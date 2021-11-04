package org.msksk.esdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.msksk.esdemo.config.BusinessException;
import org.msksk.esdemo.domain.FileInfo;
import org.msksk.esdemo.dto.FileSearchDTO;
import org.msksk.esdemo.mapper.FileInfoMapper;
import org.msksk.esdemo.service.FileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@Slf4j
public class FileInfoServiceImpl implements FileInfoService {

    @Autowired
    FileInfoMapper mapper;

    @Value("${file-info.upload-path}")
    String uploadPath;

    @Override
    public FileInfo uploadFile(MultipartFile file) throws Exception {

        //generated a random name for store
        String originalName = file.getOriginalFilename();
        int extIndex = originalName.lastIndexOf(".");
        String ext = "";
        if (extIndex >= 0) {
            ext = originalName.substring(extIndex + 1);
        }
        String generatedName = UUID.randomUUID().toString();
        if (StringUtils.hasText(ext)) {
            generatedName += "." + ext;
        }

        //store path
        File dir = new File(uploadPath);
        if (!dir.exists() || !dir.isDirectory()) {
            dir.mkdirs();
        }
        //files seperated by month
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        String folder = formatter.format(now);
        String path = genFilePath(uploadPath, folder);
        File pathFile = new File(path);
        if (!pathFile.exists() || !pathFile.isDirectory()) {
            pathFile.mkdirs();
        }

        //save
        String filePath = genFilePath(path, generatedName);
        FileOutputStream out = new FileOutputStream(filePath);
        out.write(file.getBytes());
        out.close();

        //save record
        FileInfo fi = new FileInfo();
        fi.setPhyName(generatedName);
        fi.setOriginalName(originalName);
        fi.setPath(folder);
        fi.setType(ext);
        //fi.setCreateTime(now);
        //fi.setUpdateTime(now);
        mapper.insert(fi);

        return fi;
    }

    @Override
    public String downloadFile(HttpServletRequest request, HttpServletResponse response, Integer fileId) throws IOException {
        FileInfo fi = mapper.selectById(fileId);
        if (null == fi) {
            throw new BusinessException("File not found");
        }
        String phyPath = genFilePath(uploadPath, fi.getPath(), fi.getPhyName());
        File file = new File(phyPath);
        if (!file.exists()) {
            throw new BusinessException("File not exist");
        }

        InputStream inStream = null;
        OutputStream out = null;
        try {
            // 读到流中
            inStream = new FileInputStream(phyPath);// 文件的存放路径
            // 设置输出的格式
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("multipart/form-data");
            response.addHeader("Content-Disposition",
                    "attachment; filename=\"" + URLEncoder.encode(fi.getOriginalName(), "UTF-8") + "\"");
            response.setHeader("x-decompressed-content-length", String.valueOf(file.length()));
            // 循环取出流中的数据
            byte[] b = new byte[4096];
            int len;
            out = response.getOutputStream();
            while ((len = inStream.read(b)) > 0) {
                out.write(b, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != inStream) {
                inStream.close();
            }
            if (null != out) {
                out.close();
            }
        }
        return fi.getOriginalName();
    }

    @Override
    public int deleteFile(Integer fileId) {
        FileInfo fi = mapper.selectById(fileId);
        if (null == fi) {
            throw new BusinessException("File not found");
        }

        //delete file on disk
        String phyPath = genFilePath(uploadPath, fi.getPath(), fi.getPhyName());
        File file = new File(phyPath);
        file.delete();

        //TODO: delete on es

        //delete record
        mapper.deleteById(fi.getId());

        return 0;
    }

    @Override
    public IPage<FileInfo> searchFile(FileSearchDTO searchDTO) {
        Wrapper qw = new QueryWrapper<FileInfo>().lambda()
                .like(FileInfo::getOriginalName, searchDTO.getFileName())
                .orderBy(true, true, FileInfo::getId);

        IPage<FileInfo> files = mapper.selectPage(
                new Page<>(searchDTO.getPageNumber(), searchDTO.getPageSize()),
                qw
        );

        return files;
    }

    private String genFilePath(String... names) {
        StringJoiner joiner = new StringJoiner(File.separator);
        for (String name : names) {
            joiner.add(name);
        }
        return joiner.toString();
    }
}
