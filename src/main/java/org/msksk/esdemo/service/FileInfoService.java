package org.msksk.esdemo.service;

import com.github.pagehelper.PageInfo;
import org.msksk.esdemo.domain.FileInfo;
import org.msksk.esdemo.dto.FileSearchDTO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public interface FileInfoService {

    FileInfo uploadFile(MultipartFile file) throws Exception;

    String downloadFile(HttpServletRequest request, HttpServletResponse response, Integer fileId) throws IOException;

    int deleteFile(Integer fileId);

    PageInfo<FileInfo> searchFile(FileSearchDTO searchDTO);
}
