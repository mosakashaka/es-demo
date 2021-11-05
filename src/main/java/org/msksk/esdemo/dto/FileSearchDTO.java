package org.msksk.esdemo.dto;

import lombok.Data;

@Data
public class FileSearchDTO {
    private String keyword;

    private Integer pageSize;

    private Integer pageNumber;
}
