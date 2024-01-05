package com.bill.backend.utils;

import com.bill.backend.modules.constant.ErrorCode;
import com.bill.backend.modules.exception.BusinessException;
import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class FileUtils {

    public static String upload(HttpServletRequest request, MultipartFile file) {
        String fileName = UUIDUtil.get32UUID();
        String dirPath = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "static/file/";

        System.out.println(file.getOriginalFilename());

        String originalFileName = file.getOriginalFilename();
        String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));
        File filePath = new File(dirPath);
        // 判断路径是否存在
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        if (file.isEmpty()) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "文件不能为空");
        }
        try {
            file.transferTo(new File(dirPath + fileName + suffix));
        } catch (IOException e) {
            System.out.println(e.toString());
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "上传失败, 系统错误");
        }
        return fileCompletePath(request, "/static/file/", fileName + suffix);
    }

    public static String fileCompletePath(HttpServletRequest request, String path, String fileName) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + fileName;
    }

    /**
     * 获取除域名和端口之外的路径部分
     *
     * @param urlStr
     * @return
     */
    public static String getPathAfterDomainOrPort(String urlStr) {
        try {
            URL url = new URL(urlStr);
            String protocol = url.getProtocol();
            String host = url.getHost();
            int port = url.getPort();
            // 如果端口是默认端口（例如HTTP的80或HTTPS的443），则不需要包含在路径中
            if (port == -1 || (protocol.equals("http") && port == 80) || (protocol.equals("https") && port == 443)) {
                return url.getPath();
            } else {
                return urlStr.substring(protocol.length() + 3 +host.length() + 1 + Integer.toString(port).length());
            }
        } catch (Exception e) {
            throw new RuntimeException("无法解析URL: " + urlStr, e);
        }
    }

}
