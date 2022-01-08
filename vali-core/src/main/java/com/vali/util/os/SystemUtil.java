package com.vali.util.os;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import com.vandream.hamster.core.constant.Constants;
import com.vandream.hamster.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Operating System Util
 *
 * @author : LiJie
 * @date : 2019/2/25
 * @time : 17:22
 * Description:
 */
@Slf4j
public class SystemUtil {
    private static String OS = System.getProperty("os.name")
            .toLowerCase();


    public static boolean isLinux() {
        return OS.indexOf("linux") >= 0;
    }

    public static boolean isMacOS() {
        return OS.indexOf("mac") >= 0 && OS.indexOf("os") > 0 && OS.indexOf("x") < 0;
    }

    public static boolean isMacOSX() {
        return OS.indexOf("mac") >= 0 && OS.indexOf("os") > 0 && OS.indexOf("x") > 0;
    }

    public static boolean isWindows() {
        return OS.indexOf("windows") >= 0;
    }

    public static boolean isOS2() {
        return OS.indexOf("os/2") >= 0;
    }

    public static boolean isSolaris() {
        return OS.indexOf("solaris") >= 0;
    }

    public static boolean isSunOS() {
        return OS.indexOf("sunos") >= 0;
    }

    public static boolean isMPEiX() {
        return OS.indexOf("mpe/ix") >= 0;
    }

    public static boolean isHPUX() {
        return OS.indexOf("hp-ux") >= 0;
    }

    public static boolean isAix() {
        return OS.indexOf("aix") >= 0;
    }

    public static boolean isOS390() {
        return OS.indexOf("os/390") >= 0;
    }

    public static boolean isFreeBSD() {
        return OS.indexOf("freebsd") >= 0;
    }

    public static boolean isIrix() {
        return OS.indexOf("irix") >= 0;
    }

    public static boolean isDigitalUnix() {
        return OS.indexOf("digital") >= 0 && OS.indexOf("unix") > 0;
    }

    public static boolean isNetWare() {
        return OS.indexOf("netware") >= 0;
    }

    public static boolean isOSF1() {
        return OS.indexOf("osf1") >= 0;
    }

    public static boolean isOpenVMS() {
        return OS.indexOf("openvms") >= 0;
    }

    public static String getOSName() {
        return OS;
    }

    /**
     * 根据 InetAddress 对象获取 主机名
     *
     * @return
     * @throws UnknownHostException
     */
    public static String getHostNameForInet() {
        try {
            return (InetAddress.getLocalHost()).getHostName();
        }
        catch (UnknownHostException e) {
            // host = "hostname: hostname"
            String host = e.getMessage();
            if (host != null) {
                int colon = host.indexOf(':');
                if (colon > 0) {
                    return host.substring(0, colon);
                }
            }
            return "";
        }
    }

    /**
     * 获取 主机名 HostName
     *
     * @return
     * @throws UnknownHostException
     */
    public static String getHostName() throws UnknownHostException {
        String computerName = System.getenv("COMPUTERNAME");
        if (StringUtil.isNotBlank(computerName)) {
            return computerName;
        }
        else {
            return getHostNameForInet();
        }
    }

    /**
     * 获取本地主机 IP 非 127.0.0.1
     *
     * @return
     */
    public static String getServerIP() throws SocketException {
        String serverIP = null;

        Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();

        InetAddress inetAddress = null;

        while (netInterfaces.hasMoreElements()) {
            boolean flag = false;
            NetworkInterface networkInterface = (NetworkInterface) netInterfaces.nextElement();
            //判断 接口名字中不包含：的
            log.debug(networkInterface.getName() + "-----");
            if (!networkInterface.getName()
                    .contains(":")) {
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    inetAddress = inetAddresses.nextElement();
                    //!ip.isSiteLocalAddress()  判断信息是否源自本机的ip
                    if (!inetAddress.isLoopbackAddress() && inetAddress.getHostAddress()
                            .indexOf(":") == -1) {
                        serverIP = inetAddress.getHostAddress();
                        flag = true;
                        break;
                    }
                    else {
                        inetAddress = null;
                    }
                }
            }
            if (flag) {
                break;
            }
        }

        return serverIP;
    }

    /**
     * 从系统环境变量获取 服务器标识 flag
     *
     * @return empty or 1
     */
    public static String getServerFlag() {
        return System.getenv(Constants.KEY_VCLOUD_SERVER_FLAG);
    }

    /**
     * 判断当前运行环境是否为 物理机
     *
     * @return
     */
    //public static String isPhysical() {
    //
    //
    //}

    /**
     * 判断当前运行环境是否为 虚拟机
     *
     * @return
     */
    //public static String isVirtual() {
    //
    //}

    /**
     * 判断当前运行环境是否为 容器
     * @return
     */
    //public static String isContainer() {
    //
    //}

    /**
     * 是否存在服务器环境标识
     *
     * @return true 是 否则 false
     */
    public static boolean isCloudServer() {
        final String getenv = getServerFlag();
        if (StringUtil.isBlank(getenv)) {
            return false;
        }
        else {
            return true;
        }
    }
}

