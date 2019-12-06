package com.kitsrc.watt.util;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import com.vandream.hamster.core.util.os.SystemUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

/**
 * Created with IntelliJ IDEA </p>
 *
 * @author : LiJie  </p>
 * @date : 2019/05/23  </p>
 * @time : 15:04  </p>
 * Description:  </p>
 */
@Slf4j
public class JavaLibraryUtil {
    /**
     * 文件资源路径 项目resources 实时路径
     */
    private static String FILE_RESOURCE_LOADER_PATH = Class.class.getClass()
                                                                 .getResource("/")
                                                                 .getPath();
    /**
     * windows下项目的 JavaLibraryPath
     */
    private static final String WINDOWS_JAVA_LIBRARY_PATH = "C:\\Users\\Public\\vandream\\bin\\";
    /**
     * linux下项目的 JavaLibraryPath
     */
    private static final String LINUX_JAVA_LIBRARY_PATH = "/usr/local/vandream/bin/";
    /**
     * Mac OS 下项目的 JavaLibraryPath
     */
    private static final String MACOS_JAVA_LIBRARY_PATH = "/Users/Shared/vandream/bin/";

    public static void main(String[] args) {
        initJavaLibrary();
    }

    public static void initJavaLibrary() {
        try {
            if (SystemUtil.isWindows()) {
                File file = new File(WINDOWS_JAVA_LIBRARY_PATH);
                if (!file.exists()) {
                    file.mkdirs();
                }
            }
            if (SystemUtil.isLinux()) {
                File file = new File(LINUX_JAVA_LIBRARY_PATH);
                if (!file.exists()) {
                    file.mkdirs();
                }
            }
            if (SystemUtil.isLinux()) {
                File file = new File(MACOS_JAVA_LIBRARY_PATH);
                if (!file.exists()) {
                    file.mkdirs();
                }
            }
            initSigar();
        } catch (Exception e) {
            //只打印堆栈 不做任何操作
            e.printStackTrace();
        }

    }

    private static void initSigar() throws IOException {

        // 0.判断jar包下是否存在相应资源文件 不存在则跳过
        String sigarLibResourcesPath = FILE_RESOURCE_LOADER_PATH + File.separator + "library" + File.separator + "sigar";

        File sigarLibResourcesPathFile = new File(sigarLibResourcesPath);
        if (!sigarLibResourcesPathFile.exists()) {
            return;
        }
        if (SystemUtil.isWindows()) {
            initSigarIntoWindows(sigarLibResourcesPathFile);
        } else if (SystemUtil.isLinux()) {
            initSigarIntoLinux(sigarLibResourcesPathFile);
        } else if (SystemUtil.isMacOS() || SystemUtil.isMacOSX()) {
            initSigarIntoMacos(sigarLibResourcesPathFile);
        }
        log.info("自定义 java.library Sigar 添加成功~");

    }

    /**
     * 加入 Windows 系统的 Sigar 库依赖
     *
     * @param sigarLibraryResources
     * @throws IOException
     */
    private static void initSigarIntoWindows(File sigarLibraryResources) throws IOException {
        if (!SystemUtil.isWindows()) {
            return;
        }
        String sigarLibPath = WINDOWS_JAVA_LIBRARY_PATH + "sigar" + File.separator;
        // 1.判断Sigar库路径是否存在 不存在则创建

        File sigarLibPathFile = new File(sigarLibPath);
        if (!sigarLibPathFile.exists()) {
            sigarLibPathFile.mkdirs();
        }
        // 3.从jar包中读取依赖库文件 并 复制到 项目自定义库路径
        Collection<File> sigarLibraryFileList = FileUtils.listFiles(sigarLibraryResources, null, false);
        if (CollectionUtil.isNotEmpty(sigarLibraryFileList)) {
            for (File file : sigarLibraryFileList) {
                if (file != null && file.exists() && file.isFile()) {
                    String fileName = file.getName();
                    // 判断windows 的 sigar库
                    if (fileName.contains("winnt")) {
                        copyFile(sigarLibPath, sigarLibPathFile, file);
                    }
                }
            }
        }
        //将项目自定义库路径加入到 操作系统 java.library.path 路径中
        addJavaLibraryPath(sigarLibPathFile);

    }

    /**
     * 加入 Linux 系统的 Sigar 库依赖
     *
     * @param sigarLibraryResources
     * @throws IOException
     */
    private static void initSigarIntoLinux(File sigarLibraryResources) throws IOException {
        if (!SystemUtil.isLinux()) {
            return;
        }
        String sigarLibPath = LINUX_JAVA_LIBRARY_PATH + "sigar" + File.separator;
        // 1.判断Sigar库路径是否存在 不存在则创建

        File sigarLibPathFile = new File(sigarLibPath);
        if (!sigarLibPathFile.exists()) {
            sigarLibPathFile.mkdirs();
        }
        // 3.从jar包中读取依赖库文件 并 复制到 项目自定义库路径
        Collection<File> sigarLibraryFileList = FileUtils.listFiles(sigarLibraryResources, null, null);
        if (CollectionUtil.isNotEmpty(sigarLibraryFileList)) {
            for (File file : sigarLibraryFileList) {
                if (file != null && file.exists() && file.isFile()) {
                    String fileName = file.getName();
                    // 判断windows 的 sigar库
                    if (fileName.contains("linux")) {
                        copyFile(sigarLibPath, sigarLibPathFile, file);
                    }
                }
            }
        }
        //将项目自定义库路径加入到 操作系统 java.library.path 路径中
        addJavaLibraryPath(sigarLibPathFile);

    }

    private static void copyFile(String sigarLibPath, File sigarLibPathFile, File file) throws IOException {
        String srcFileName = file.getName();
        String destFilePath = sigarLibPath + srcFileName;
        File destFile = new File(destFilePath);
        if (destFile.isFile() && destFile.exists()) {
            log.info("Sigar Lib File " + srcFileName + " is exists");
        } else {
            FileUtils.copyFileToDirectory(file, sigarLibPathFile);
        }
    }


    /**
     * 加入 Windows 系统的 Sigar 库依赖
     *
     * @param sigarLibraryResources
     * @throws IOException
     */
    private static void initSigarIntoMacos(File sigarLibraryResources) throws IOException {
        if (SystemUtil.isMacOS() || SystemUtil.isMacOSX()) {
            return;
        }
        String sigarLibPath = MACOS_JAVA_LIBRARY_PATH + "sigar" + File.separator;
        // 1.判断Sigar库路径是否存在 不存在则创建

        File sigarLibPathFile = new File(sigarLibPath);
        if (!sigarLibPathFile.exists()) {
            sigarLibPathFile.mkdirs();
        }
        // 3.从jar包中读取依赖库文件 并 复制到 项目自定义库路径
        Collection<File> sigarLibraryFileList = FileUtils.listFiles(sigarLibraryResources, null, null);
        if (CollectionUtil.isNotEmpty(sigarLibraryFileList)) {
            for (File file : sigarLibraryFileList) {
                if (file != null && file.exists() && file.isFile()) {
                    String fileName = file.getName();
                    // 判断windows 的 sigar库
                    if (fileName.contains("macos")) {
                        copyFile(sigarLibPath, sigarLibPathFile, file);

                    }
                }
            }
        }
        //将项目自定义库路径加入到 操作系统 java.library.path 路径中
        addJavaLibraryPath(sigarLibPathFile);

    }

    /**
     * 将项目自定义库路径加入到 操作系统 java.library.path 路径中
     *
     * @param libraryPath
     * @throws IOException
     */
    private static void addJavaLibraryPath(File libraryPath) throws IOException {
        String systemJavaLibraryPath = System.getProperty("java.library.path");
        String sigarLibraryPath = libraryPath.getCanonicalPath();
        //为防止java.sigar.library.path重复加，此处判断了一下
        if (!systemJavaLibraryPath.contains(sigarLibraryPath)) {
            // 获取当前操作系统 path 分隔符 在Windows系统下得到;（分号）,在Linux下得到:(冒号) 环境变量中用来分隔路径
            String pathSeparator = System.getProperty("path.separator");
            systemJavaLibraryPath += sigarLibraryPath + pathSeparator + sigarLibraryPath;
            System.out.println(systemJavaLibraryPath);
            System.setProperty("java.library.path", systemJavaLibraryPath);
            String property = System.getProperty("java.library.path");
            if (property.equalsIgnoreCase(systemJavaLibraryPath)) {
                log.info("自定义 java.library.path 添加成功~");
            } else {
                log.info("自定义 java.library.path 添加失败:" + property);
            }
        }
    }

}
