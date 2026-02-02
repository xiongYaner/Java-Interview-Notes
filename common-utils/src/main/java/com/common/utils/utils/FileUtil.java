package com.common.utils.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 文件工具类
 * 提供各种文件操作方法
 */
public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 读取文件内容
     */
    public static String readFile(String filePath) {
        return readFile(filePath, "UTF-8");
    }

    /**
     * 读取文件内容
     */
    public static String readFile(String filePath, String charsetName) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), charsetName))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (Exception e) {
            logger.error("Failed to read file: {}", filePath, e);
            return null;
        }
    }

    /**
     * 写入文件内容
     */
    public static boolean writeFile(String filePath, String content) {
        return writeFile(filePath, content, "UTF-8", false);
    }

    /**
     * 写入文件内容
     */
    public static boolean writeFile(String filePath, String content, String charsetName) {
        return writeFile(filePath, content, charsetName, false);
    }

    /**
     * 写入文件内容
     */
    public static boolean writeFile(String filePath, String content, String charsetName, boolean append) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, append), charsetName))) {
            writer.write(content);
            return true;
        } catch (Exception e) {
            logger.error("Failed to write file: {}", filePath, e);
            return false;
        }
    }

    /**
     * 读取文件内容到列表
     */
    public static List<String> readFileToList(String filePath) {
        return readFileToList(filePath, "UTF-8");
    }

    /**
     * 读取文件内容到列表
     */
    public static List<String> readFileToList(String filePath, String charsetName) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), charsetName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (Exception e) {
            logger.error("Failed to read file to list: {}", filePath, e);
        }
        return lines;
    }

    /**
     * 写入列表内容到文件
     */
    public static boolean writeListToFile(String filePath, List<String> lines) {
        return writeListToFile(filePath, lines, "UTF-8", false);
    }

    /**
     * 写入列表内容到文件
     */
    public static boolean writeListToFile(String filePath, List<String> lines, String charsetName) {
        return writeListToFile(filePath, lines, charsetName, false);
    }

    /**
     * 写入列表内容到文件
     */
    public static boolean writeListToFile(String filePath, List<String> lines, String charsetName, boolean append) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, append), charsetName))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            return true;
        } catch (Exception e) {
            logger.error("Failed to write list to file: {}", filePath, e);
            return false;
        }
    }

    /**
     * 复制文件
     */
    public static boolean copyFile(String sourcePath, String destPath) {
        try (InputStream is = new FileInputStream(sourcePath);
             OutputStream os = new FileOutputStream(destPath)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            return true;
        } catch (Exception e) {
            logger.error("Failed to copy file: {} -> {}", sourcePath, destPath, e);
            return false;
        }
    }

    /**
     * 移动文件
     */
    public static boolean moveFile(String sourcePath, String destPath) {
        File sourceFile = new File(sourcePath);
        File destFile = new File(destPath);
        try {
            return sourceFile.renameTo(destFile);
        } catch (Exception e) {
            logger.error("Failed to move file: {} -> {}", sourcePath, destPath, e);
            return false;
        }
    }

    /**
     * 删除文件
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        try {
            return file.delete();
        } catch (Exception e) {
            logger.error("Failed to delete file: {}", filePath, e);
            return false;
        }
    }

    /**
     * 创建目录
     */
    public static boolean createDirectory(String dirPath) {
        File dir = new File(dirPath);
        try {
            return dir.mkdirs();
        } catch (Exception e) {
            logger.error("Failed to create directory: {}", dirPath, e);
            return false;
        }
    }

    /**
     * 删除目录（递归删除）
     */
    public static boolean deleteDirectory(String dirPath) {
        File dir = new File(dirPath);
        if (!dir.exists()) {
            return true;
        }
        if (dir.isFile()) {
            return dir.delete();
        }
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                deleteDirectory(file.getAbsolutePath());
            }
        }
        return dir.delete();
    }

    /**
     * 列出目录下的所有文件
     */
    public static List<File> listFiles(String dirPath) {
        List<File> fileList = new ArrayList<>();
        File dir = new File(dirPath);
        if (!dir.exists() || !dir.isDirectory()) {
            return fileList;
        }
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                fileList.add(file);
            }
        }
        return fileList;
    }

    /**
     * 列出目录下的所有文件（递归）
     */
    public static List<File> listFilesRecursive(String dirPath) {
        List<File> fileList = new ArrayList<>();
        File dir = new File(dirPath);
        if (!dir.exists() || !dir.isDirectory()) {
            return fileList;
        }
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    fileList.addAll(listFilesRecursive(file.getAbsolutePath()));
                } else {
                    fileList.add(file);
                }
            }
        }
        return fileList;
    }

    /**
     * 获取文件大小
     */
    public static long getFileSize(String filePath) {
        File file = new File(filePath);
        if (!file.exists() || file.isDirectory()) {
            return 0;
        }
        return file.length();
    }

    /**
     * 获取文件名（不含扩展名）
     */
    public static String getFileNameWithoutExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex != -1) {
            return fileName.substring(0, dotIndex);
        }
        return fileName;
    }

    /**
     * 获取文件扩展名
     */
    public static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex != -1 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1);
        }
        return "";
    }

    /**
     * 判断文件是否存在
     */
    public static boolean fileExists(String filePath) {
        return new File(filePath).exists();
    }

    /**
     * 判断是否是目录
     */
    public static boolean isDirectory(String path) {
        File file = new File(path);
        return file.exists() && file.isDirectory();
    }

    /**
     * 判断是否是文件
     */
    public static boolean isFile(String path) {
        File file = new File(path);
        return file.exists() && file.isFile();
    }

    /**
     * 获取文件创建时间
     */
    public static Date getFileCreationTime(String filePath) {
        try {
            Path path = Paths.get(filePath);
            BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
            return new Date(attributes.creationTime().toMillis());
        } catch (Exception e) {
            logger.error("Failed to get file creation time: {}", filePath, e);
            return null;
        }
    }

    /**
     * 获取文件修改时间
     */
    public static Date getFileModificationTime(String filePath) {
        try {
            Path path = Paths.get(filePath);
            BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
            return new Date(attributes.lastModifiedTime().toMillis());
        } catch (Exception e) {
            logger.error("Failed to get file modification time: {}", filePath, e);
            return null;
        }
    }

    /**
     * 格式化文件大小
     */
    public static String formatFileSize(long size) {
        return CommonUtils.formatFileSize(size);
    }

    /**
     * 格式化文件日期
     */
    public static String formatFileDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
}
