package org.msksk.esdemo.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;
import java.util.Date;

@Data
@Document(indexName = "esfile")
public class EsFile implements Serializable {
    @Id
    private Integer id;

    @Field(analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String name;

    @Field(analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String content;

    private Date updateTime;
}
