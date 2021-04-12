package org.msksk.esdemo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T>  extends ResponseData implements Serializable {

    //数据
    private T data;

}
