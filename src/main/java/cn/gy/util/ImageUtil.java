package cn.gy.util;

import cn.gy.core.service.ServiceException;
import com.google.common.io.Files;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.process.Pipe;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@Slf4j
public class ImageUtil {
    public static final String GRAPHICS_MAGICK_PATH = "D:\\Program Files\\GraphicsMagick-1.3.33-Q8";

    public static byte[] compressImage(byte[] bytes, String contentType,
                                       String width, String height, Double quality, String cmdPath) {
        try (ByteArrayInputStream is = new ByteArrayInputStream(bytes); ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            IMOperation op = new IMOperation();
            op.addImage("-");
            String wh = width + (StringUtils.isEmpty(width) ? "" : "x") + height + "!";
            op.addRawArgs("-thumbnail", wh);
            op.quality(quality);
            op.addImage(contentType + ":-");
            Pipe pipeIn = new Pipe(is, null);
            Pipe pipeOut = new Pipe(null, os);
            ConvertCmd cmd = new ConvertCmd(true);
            if (StringUtils.isNotBlank(cmdPath)) {
                cmd.setSearchPath(cmdPath);
            }
            cmd.setInputProvider(pipeIn);
            cmd.setOutputConsumer(pipeOut);
            cmd.run(op);
            return os.toByteArray();
        } catch (IOException | InterruptedException | IM4JavaException e) {
            log.warn("压缩图片失败!", e);
            throw new ServiceException("压缩图片失败");
        }
    }

    public static byte[] compressImageForSize(byte[] bytes, String contentType,
                                       Double quality, String cmdPath) {
        try (ByteArrayInputStream is = new ByteArrayInputStream(bytes); ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            IMOperation op = new IMOperation();
            op.addImage("-");
            op.quality(quality);
            op.sample(null, 420);//<resize<thumbnail
            op.addImage(contentType + ":-");
            Pipe pipeIn = new Pipe(is, null);
            Pipe pipeOut = new Pipe(null, os);
            ConvertCmd cmd = new ConvertCmd(false);
            if (StringUtils.isNotBlank(cmdPath)) {
                cmd.setSearchPath(cmdPath);
            }
            cmd.setInputProvider(pipeIn);
            cmd.setOutputConsumer(pipeOut);
            cmd.run(op);
            return os.toByteArray();
        } catch (Exception e) {
            log.warn("压缩图片失败!", e);
            throw new ServiceException("压缩图片失败");
        }
    }

    public static void main(String[] args) throws Exception {
        byte[] bytes = Files.toByteArray(new File("C:\\Users\\WangZhiHua\\Documents\\Tencent Files\\583903546\\FileRecv\\38.png"));

        long start = System.currentTimeMillis();
        byte[] bs = compressImageForSize(bytes, "png", 75.0, "C:\\Program Files\\GraphicsMagick-1.3.33-Q8");

        long end = System.currentTimeMillis();
        Files.write(bs, new File("C:\\Users\\WangZhiHua\\Desktop\\output16.jpg"));
        System.out.println(end - start);
    }
}
