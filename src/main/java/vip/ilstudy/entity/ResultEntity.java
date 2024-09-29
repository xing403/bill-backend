package vip.ilstudy.entity;

import lombok.Data;

@Data
public class ResultEntity<T> {
    private Integer code;
    private String message;
    private T data;
    private String error;

    public ResultEntity() {
    }

    public ResultEntity(Integer code, String error) {
        this.code = code;
        this.error = error;
    }
}
