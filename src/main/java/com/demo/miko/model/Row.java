package com.demo.miko.model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Row {
    private Map<String, Object> columnValues;
}
