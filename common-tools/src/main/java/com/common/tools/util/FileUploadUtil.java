package com.common.tools.util;

import com.common.tools.config.CommonToolsProperties;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * 文件上传工具类
 */
@Component
public class FileUploadUtil {

    private final CommonToolsProperties.FileUploadProperties fileUploadProperties;

    @Autowired
    public FileUploadUtil(CommonToolsProperties commonToolsProperties) {
        this.fileUploadProperties = commonToolsProperties.getFileUpload();
    }

    /**
     * 上传文件
     * @param file 上传的文件
     * @return 文件路径
     */
    public String uploadFile(MultipartFile file) {
        try {
            // 验证文件类型
            String fileName = file.getOriginalFilename();
            if (fileName == null) {
                throw new IllegalArgumentException("文件名不能为空");
            }

            String extension = FilenameUtils.getExtension(fileName).toLowerCase();
            String allowedTypes = fileUploadProperties.getAllowedTypes();

            if (!allowedTypes.contains(extension)) {
                throw new IllegalArgumentException("不支持的文件类型：" + extension);
            }

            // 验证文件大小
            long fileSize = file.getSize();
            long maxSize = fileUploadProperties.getMaxFileSize() * 1024 * 1024;

            if (fileSize > maxSize) {
                throw new IllegalArgumentException("文件大小超出限制：" + fileUploadProperties.getMaxFileSize() + "MB");
            }

            // 生成唯一文件名
            String uniqueFileName = generateUniqueFileName(fileName);

            // 创建上传目录
            String uploadPath = fileUploadProperties.getUploadPath();
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // 保存文件
            Path destinationPath = Paths.get(uploadPath, uniqueFileName);
            Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

            // 返回文件路径
            return destinationPath.toString();
        } catch (IOException e) {
            throw new RuntimeException("文件上传失败：" + e.getMessage(), e);
        }
    }

    /**
     * 生成唯一文件名
     * @param originalFileName 原始文件名
     * @return 唯一文件名
     */
    private String generateUniqueFileName(String originalFileName) {
        String extension = FilenameUtils.getExtension(originalFileName);
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String uuid = UUID.randomUUID().toString().replace("-", "");

        return timestamp + "_" + uuid + "." + extension;
    }

    /**
     * 删除文件
     * @param filePath 文件路径
     */
    public void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 获取文件信息
     * @param filePath 文件路径
     * @return 文件信息
     */
    public FileInfo getFileInfo(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IllegalArgumentException("文件不存在：" + filePath);
        }

        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileName(file.getName());
        fileInfo.setFilePath(filePath);
        fileInfo.setFileSize(file.length());
        fileInfo.setFileExtension(FilenameUtils.getExtension(file.getName()));

        try {
            fileInfo.setContentType(Files.probeContentType(file.toPath()));
        } catch (IOException e) {
            fileInfo.setContentType("application/octet-stream");
        }

        return fileInfo;
    }

    /**
     * 文件信息类
     */
    public static class FileInfo {
        private String fileName;
        private String filePath;
        private long fileSize;
        private String fileExtension;
        private String contentType;

        // 构造方法和getter/setter
        public FileInfo() {
        }

        public FileInfo(String fileName, String filePath, long fileSize, String fileExtension, String contentType) {
            this.fileName = fileName;
            this.filePath = filePath;
            this.fileSize = fileSize;
            this.fileExtension = fileExtension;
            this.contentType = contentType;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public long getFileSize() {
            return fileSize;
        }

        public void setFileSize(long fileSize) {
            this.fileSize = fileSize;
        }

        public String getFileExtension() {
            return fileExtension;
        }

        public void setFileExtension(String fileExtension) {
            this.fileExtension = fileExtension;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }
    }
}
