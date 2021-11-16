package cn.gy.util;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.MXRecord;
import org.xbill.DNS.Record;
import org.xbill.DNS.Type;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;

public class EmailUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailUtil.class);

    public static boolean isValidEmail(String email, String domain) {
        if (StringUtils.isBlank(email) || !email.matches("[\\w.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+")) {
            LOGGER.error("邮箱（" + email + "）校验未通过，格式不对!");
            return false;
        }
        String hostName = email.split("@")[1];
        try (Socket socket = new Socket()) {
            // 查找DNS缓存服务器上为MX类型的缓存域名信息
            Lookup lookup = new Lookup(hostName, Type.MX);
            Record[] records = lookup.run();
            if (lookup.getResult() != Lookup.SUCCESSFUL || ArrayUtils.isEmpty(records)) {
                LOGGER.error("邮箱（" + email + "）校验未通过，未找到对应的MX记录!");
                return false;
            } else {
                Arrays.sort(records, (o1, o2) -> new CompareToBuilder().append(((MXRecord) o1).getPriority(), ((MXRecord) o2).getPriority()).toComparison());
                // 超时时间(毫秒)
                long timeout = 5000;
                // 间隔检查时间(毫秒)
                int sleepSect = 50;
                MXRecord mxRecord = (MXRecord) records[0];
                String mxHost = mxRecord.getTarget().toString();
                socket.connect(new InetSocketAddress(mxHost, 25));
                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new BufferedInputStream(socket.getInputStream())));
                     BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
                    LOGGER.info("连接");
                    if (getResponseCode(timeout, sleepSect, bufferedReader) != 220) {
                        return false;
                    }
                    LOGGER.info("握手");
                    bufferedWriter.write("HELO " + domain + "\r\n");
                    bufferedWriter.flush();
                    if (getResponseCode(timeout, sleepSect, bufferedReader) != 250) {
                        return false;
                    }
                    LOGGER.info("身份");
                    bufferedWriter.write("MAIL FROM: <check@" + domain + ">\r\n");
                    bufferedWriter.flush();
                    if (getResponseCode(timeout, sleepSect, bufferedReader) != 250) {
                        return false;
                    }
                    LOGGER.info("验证");
                    bufferedWriter.write("RCPT TO: <" + email + ">\r\n");
                    bufferedWriter.flush();
                    if (getResponseCode(timeout, sleepSect, bufferedReader) != 250) {
                        return false;
                    }
                    // 断开
                    bufferedWriter.write("QUIT\r\n");
                    bufferedWriter.flush();
                    return true;
                }
            }
        } catch (Exception e) {
            LOGGER.error("验证EMAIL地址有效性过程中出现异常", e);
        }
        return false;
    }

    private static int getResponseCode(long timeout, int sleepSect, BufferedReader bufferedReader) throws InterruptedException, NumberFormatException, IOException {
        int code = 0;
        for (long i = sleepSect; i < timeout; i += sleepSect) {
            if (bufferedReader.ready()) {
                String outline = bufferedReader.readLine();
                if (outline != null && outline.length() >= 3) {
                    code = NumberUtils.toInt(outline.substring(0, 3));
                    break;
                }
            }
            Thread.sleep(sleepSect);
        }
        return code;
    }
}
