package com.demo.miko.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Queryinfo implements Serializable {
    private Long id;
    private String query;
    private boolean status;
}
