package com.demo.miko.repository;

import com.demo.miko.model.Table;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

@Repository
public class FileTableRepository implements TableRepository {

    @Override
    public Table save(Table table) {

        try{
            File metadataTableFile = new File("meta"+table.getTableName() + ".json");
            if (!metadataTableFile.exists()) {
                throw new RuntimeException("Table doesn't exist");
            }
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
            return null;
        }

        String TABLE_FILE_PATH = table.getTableName() + ".json";
        Set<Table> allTables = findAllTables(TABLE_FILE_PATH);
        allTables.removeIf(t -> t.getTableName().equals(table.getTableName()));
        allTables.add(table);
        saveAllTables(allTables, TABLE_FILE_PATH);
        return table;
    }

    public Set<Table> findAllTables(String TABLE_FILE_PATH) {
        try (BufferedReader reader = new BufferedReader(new FileReader(TABLE_FILE_PATH))) {
            Gson gson = new Gson();
            Type setType = new TypeToken<HashSet<Table>>() {}.getType();
            Set<Table> tables = gson.fromJson(reader, setType);
            return tables != null ? tables : new HashSet<>();
        } catch (IOException e) {
            return new HashSet<>();
        }
    }

    private void saveAllTables(Set<Table> tables, String TABLE_FILE_PATH) {
        try {
            File tableFile = new File(TABLE_FILE_PATH);
            if (!tableFile.exists()) {
                tableFile.createNewFile();
            }

            try (Writer writer = new FileWriter(tableFile)) {
                Gson gson = new Gson();
                gson.toJson(tables, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
