package org.msksk.esdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.msksk.esdemo.domain.FileInfo;

@Mapper
public interface FileInfoMapper extends BaseMapper<FileInfo> {
}
