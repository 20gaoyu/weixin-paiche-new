package cn.gy.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.InetAddressValidator;

import javax.servlet.http.HttpServletRequest;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

@Slf4j
public class IPUtil {
    private static final String LOCAL_IP = "127.0.0.1";
    private static final String LOCAL_HOST_NAME = "Default_Host_Name";
    private static final InetAddressValidator validator = InetAddressValidator.getInstance();

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        log.debug("获取到x-forwarded-for: {}",ip);
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            log.debug("获取到Proxy-Client-IP: {}",ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            log.debug("获取到WL-Proxy-Client-IP: {}",ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            log.debug("获取到HTTP_CLIENT_IP: {}",ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            log.debug("获取到HTTP_X_FORWARDED_FOR: {}",ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            log.debug("获取到RemoteAddr: {}",ip);
        }
        log.info("获取到请求IP: {}",ip);
        // 如果是多级代理，那么取第一个ip为客户端ip
        if (ip != null && ip.contains(",")) {
            ip = ip.substring(0, ip.indexOf(",")).trim();
        }

        return ip;
    }

    public static String getLocalIp() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface nif = interfaces.nextElement();
                if (!nif.isUp() || nif.isVirtual() || nif.isLoopback()) {
                    continue;
                }
                Enumeration<InetAddress> addresses = nif.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    if (addr instanceof Inet4Address && !addr.isLoopbackAddress()) {
                        return addr.getHostAddress();
                    }
                }
            }
        }catch (Exception e){
            log.error("can't find local ip!!!",e);
        }
        return LOCAL_IP;
    }

    public static String getLocalHostName() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface nif = interfaces.nextElement();
                if (!nif.isUp() || nif.isVirtual() || nif.isLoopback()) {
                    continue;
                }
                Enumeration<InetAddress> addresses = nif.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    if (addr instanceof Inet4Address && !addr.isLoopbackAddress()) {
                        return addr.getHostName();
                    }
                }
            }
        }catch (Exception e){
            log.error("can't find local host name!!!",e);
        }
        return LOCAL_HOST_NAME;
    }

    public static boolean isValidIpV4(String ip) {
        return validator.isValidInet4Address(ip);
    }

    public static boolean isValidIpV6(String ip) {
        return validator.isValidInet6Address(ip);
    }

    public static boolean isValidIpSegment(String ip) {
        return validator.isValid(ip);
    }

    public static void main(String[] args) {
        System.out.println(isValidIpV6("::1"));
        System.out.println(isValidIpV6("2000::1:0:0:9"));
        System.out.println(isValidIpV6("3ffe:ffff:0:c000::b"));
        System.out.println(isValidIpV4("127.0.0.1"));
        System.out.println(isValidIpV4("172.31.209.40"));

    }
}
