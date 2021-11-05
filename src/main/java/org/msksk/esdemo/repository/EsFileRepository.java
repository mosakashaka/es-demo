package org.msksk.esdemo.repository;

import org.msksk.esdemo.domain.EsFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsFileRepository extends ElasticsearchRepository<EsFile, Integer> {

    Page<EsFile> findByNameOrContent(Pageable page, String name, String content);
}
