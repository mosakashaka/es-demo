package org.msksk.esdemo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.msksk.esdemo.domain.FileInfo;
import org.msksk.esdemo.dto.FileSearchDTO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface FileInfoService {

    FileInfo uploadFile(MultipartFile file) throws Exception;

    String downloadFile(HttpServletRequest request, HttpServletResponse response, Integer fileId) throws IOException;

    int deleteFile(Integer fileId);

    IPage<FileInfo> searchFile(FileSearchDTO searchDTO);
}
