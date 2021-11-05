package org.msksk.esdemo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.msksk.esdemo.domain.FileInfo;
import org.msksk.esdemo.dto.FileSearchDTO;
import org.msksk.esdemo.dto.Result;
import org.msksk.esdemo.service.FileInfoService;
import org.msksk.esdemo.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    public void downloadFile(HttpServletRequest request, HttpServletResponse response,@PathVariable Integer fileId) throws IOException {
        fiService.downloadFile(request, response, fileId);
        //return R.success(fn);
    }

    @DeleteMapping("/{fileId}")
    public Result deleteFile(@PathVariable Integer fileId) {
        int result = fiService.deleteFile(fileId);
        return R.success(result);
    }

    @PostMapping("/search")
    public Result<IPage<FileInfo>> searchFile(@RequestBody FileSearchDTO searchDTO) {
        IPage<FileInfo> fis = fiService.searchFile(searchDTO);
        return R.success(fis);
    }
}
