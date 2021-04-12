package org.msksk.esdemo.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "file_info")
public class FileInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String relativeId;

    private String originalName;

    private String path;

    private String phyName;

    private String type;

    private Date createTime;

    private Date updateTime;
}
