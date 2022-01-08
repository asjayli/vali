package com.vali.util.os;

import java.io.File;

/**
 * Created with IntelliJ IDEA
 *
 * @author : LiJie
 * @date : 2019/2/25
 * @time : 17:49
 * Description:
 */
public class JvmUtil {
    /**
     * java 版本号
     */
    public static final String JAVA_VERSION;
    /**
     * 操作系统架构 取决于cpu 例如 amd64
     */
    public static final String OS_ARCH;
    /**
     * jvm 位宽 jre或jdk安装的版本 例如 64
     */
    public static final String JVM_ARCH;


    public static final boolean IS_JAVA_5;
    public static final boolean IS_JAVA_6;
    public static final boolean IS_JAVA_7;
    public static final boolean IS_JAVA_8;
    public static final boolean IS_JAVA_9;
    /**
     * 操作系统位宽与 jvm 安装版本 位宽 匹配
     */
    public static final boolean OS_JVM_ARCH_MATCHED;

    static {
        JVM_ARCH = System.getProperty("sun.arch.data.model");
        OS_ARCH = System.getProperty("os.arch");
        JAVA_VERSION = System.getProperty("java.version") + " " + JVM_ARCH;
        OS_JVM_ARCH_MATCHED = (OS_ARCH.indexOf(JVM_ARCH) > 0);

        IS_JAVA_5 = JAVA_VERSION.startsWith("1.5.");
        IS_JAVA_6 = JAVA_VERSION.startsWith("1.6.");
        IS_JAVA_7 = JAVA_VERSION.startsWith("1.7.");
        IS_JAVA_8 = JAVA_VERSION.startsWith("1.8.");
        IS_JAVA_9 = JAVA_VERSION.startsWith("1.9.");

    }

    /**
     * 运行时是否以 jar 包方式启动
     *
     * @return
     */
    public static boolean isStartupFromJar() {
        File file = new File(JvmUtil.class.getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .getPath());
        return file.isFile();
    }
}
