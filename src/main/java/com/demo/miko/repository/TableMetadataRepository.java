package com.demo.miko.repository;

import com.demo.miko.model.TableMetadata;

import java.util.Set;

public interface TableMetadataRepository {
    TableMetadata save(TableMetadata tableMetadata);

    public Set<TableMetadata> findAllTables();
}
