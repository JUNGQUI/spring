package com.jk.spring.JKStream;

import lombok.Data;

// 편의상 lombok data 사용
@Data
public class JKStreamObj {
    private int id;
    private String value;

    public JKStreamObj(int id, String value) {
        this.id = id;
        this.value = value;
    }
}
