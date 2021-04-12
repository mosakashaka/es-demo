package org.msksk.esdemo.repository;

import org.msksk.esdemo.domain.FileInfo;
import org.msksk.esdemo.dto.FileSearchDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileInfoRepository extends JpaRepository<FileInfo, Integer> {

    @Query(value = "select f from FileInfo f where f.originalName like %:#{#searchDTO.fileName}% order by f.id")
    List<FileInfo> search(@Param("searchDTO") FileSearchDTO searchDTO);
}
