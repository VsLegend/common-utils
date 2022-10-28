package com.utils.file;

import com.utils.exception.BusinessException;
import com.utils.exception.Status;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @Author Wang Junwei
 * @Date 2022/8/12 13:57
 * @Description 文件下载
 */
public class FileDownloadUtils {

    /**
     * 模板下载
     *
     * @param templatePath
     * @param response
     */
    public static void downloadInResourceFile(String templatePath, HttpServletResponse response) {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(templatePath);
        download(inputStream, templatePath, response);
    }

    /**
     * 文件下载(不删除文件)
     *
     * @param path
     * @param response
     */
    public static void download(String path, HttpServletResponse response) {
        download(path, response, false);
    }

    /**
     * 文件下载
     * @param path
     * @param response
     * @param isDeleteFile
     */
    public static void download(String path, HttpServletResponse response, boolean isDeleteFile) {
        File file = new File(path);
        if (!file.exists()) {
            throw new BusinessException(Status.FILE_NOT_FOUND);
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStream inputStream = new BufferedInputStream(fileInputStream);
            download(inputStream, file.getName(), response);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(Status.FILE_DOWNLOAD_ERROR);
        } finally {
            if (isDeleteFile) {
                boolean delete = file.delete();
            }
        }
    }

    /**
     * 下载文件
     * @param inputStream
     * @param fileName
     * @param response
     */
    public static void download(InputStream inputStream, String fileName, HttpServletResponse response) {
        if (inputStream == null) {
            throw new BusinessException(Status.FILE_STREAM_IS_EMPTY);
        }
        try {
            //设置响应类型
            response.setContentType("application/x-msdownload");
            response.addHeader("Content-Disposition",
                    " attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new BusinessException(Status.FILE_NAME_ENCODE_ERROR);
        }
        try (ServletOutputStream servletOutputStream = response.getOutputStream()) {
            //把资源文件的二进制流数据copy到response的输出流中
            IOUtils.copy(inputStream, servletOutputStream);
            //清除flush所有的缓冲区中已设置的响应信息至客户端
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(Status.FILE_DOWNLOAD_ERROR);
        }
        IOUtils.closeQuietly(inputStream);
    }


}
