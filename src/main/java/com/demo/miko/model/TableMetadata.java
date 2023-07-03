package com.demo.miko.model;

import lombok.Data;

import java.util.HashSet;

@Data
public class TableMetadata {
    private String tableName;
    private HashSet<Column> columns;
}
