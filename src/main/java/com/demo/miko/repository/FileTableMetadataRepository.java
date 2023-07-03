package com.demo.miko.repository;

import com.demo.miko.model.TableMetadata;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

@Repository
public class FileTableMetadataRepository implements TableMetadataRepository {

//    public static final String METADATA_DIRECTORY_PATH = "metadata/";

    @Override
    public TableMetadata save(TableMetadata tableMetadata) {
        String METADATA_FILE_PATH = "meta"+tableMetadata.getTableName() + ".json";

        Set<TableMetadata> allMetadata = findAllMetadata(METADATA_FILE_PATH);
        allMetadata.removeIf(metadata -> metadata.getTableName().equals(tableMetadata.getTableName()));
        allMetadata.add(tableMetadata);
        try{
            saveAllMetadata(allMetadata, METADATA_FILE_PATH);
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
            return null;
        }
        return tableMetadata;
    }

    private Set<TableMetadata> findAllMetadata(String METADATA_FILE_PATH) {
        try (BufferedReader reader = new BufferedReader(new FileReader(METADATA_FILE_PATH))) {
            Gson gson = new Gson();
            Type setType = new TypeToken<HashSet<TableMetadata>>() {}.getType();
            Set<TableMetadata> metadataSet = gson.fromJson(reader, setType);
            return metadataSet != null ? metadataSet : new HashSet<>();
        } catch (IOException e) {
            return new HashSet<>();
        }
    }

    private void saveAllMetadata(Set<TableMetadata> metadataSet, String METADATA_FILE_PATH) {
        try {
            File metadataFile = new File(METADATA_FILE_PATH);
            if (!metadataFile.exists()) {
                metadataFile.createNewFile();
            } else{
                throw new RuntimeException("Table exists");
            }

            try (Writer writer = new FileWriter(metadataFile)) {
                Gson gson = new Gson();
                gson.toJson(metadataSet, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public Set<TableMetadata> findAllTables() {
        String METADATA_DIRECTORY_PATH = "metadata/";
        File metadataDirectory = new File(METADATA_DIRECTORY_PATH);
        Set<TableMetadata> allTables = new HashSet<>();

        if (metadataDirectory.exists() && metadataDirectory.isDirectory()) {
            File[] metadataFiles = metadataDirectory.listFiles();

            if (metadataFiles != null) {
                Gson gson = new Gson();
                Type setType = new TypeToken<HashSet<TableMetadata>>() {}.getType();

                for (File file : metadataFiles) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        Set<TableMetadata> metadataSet = gson.fromJson(reader, setType);
                        if (metadataSet != null) {
                            allTables.addAll(metadataSet);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return allTables;
    }

}


