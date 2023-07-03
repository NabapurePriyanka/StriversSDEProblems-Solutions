package com.demo.miko.model;

import lombok.Data;

import java.util.HashSet;

@Data
public class Table {
    private String tableName;
    private HashSet<Row> rows;
}
