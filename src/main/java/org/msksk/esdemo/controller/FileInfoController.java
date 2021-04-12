package org.msksk.esdemo.controller;

import com.github.pagehelper.PageInfo;
import org.msksk.esdemo.domain.FileInfo;
import org.msksk.esdemo.dto.FileSearchDTO;
import org.msksk.esdemo.dto.Result;
import org.msksk.esdemo.service.FileInfoService;
import org.msksk.esdemo.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileInfoController {

    @Autowired
    FileInfoService fiService;

    @PostMapping("/upload")
    public Result<FileInfo> uploadFile(@RequestParam(value = "file") MultipartFile file) throws Exception {
        FileInfo fileInfo = fiService.uploadFile(file);
        return R.success(fileInfo);
    }

    @GetMapping("/downloadFile/{fileId}")
    public Result<String> downloadFile(HttpServletRequest request, HttpServletResponse response,@PathVariable Integer fileId) throws IOException {
        String fn = fiService.downloadFile(request, response, fileId);
        return R.success(fn);
    }

    @PostMapping("/search")
    public Result<PageInfo<FileInfo>> searchFile(@RequestBody FileSearchDTO searchDTO) {
        PageInfo<FileInfo> fis = fiService.searchFile(searchDTO);
        return R.success(fis);
    }
}
