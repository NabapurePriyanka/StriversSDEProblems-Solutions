package com.demo.miko.controller;

import com.demo.miko.model.Queryinfo;
import com.demo.miko.model.Table;
import com.demo.miko.model.TableMetadata;
import com.demo.miko.repository.TableMetadataRepository;
import com.demo.miko.repository.TableRepository;
import com.demo.miko.service.QueryInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class QueryController {
    @Autowired
    private final TableMetadataRepository tableMetadataRepository;
    private final TableRepository tableRepository;

    @Autowired
    private QueryInfoService queryInfoService;

    public QueryController(TableMetadataRepository tableMetadataRepository, TableRepository tableRepository) {
        this.tableMetadataRepository = tableMetadataRepository;
        this.tableRepository = tableRepository;
    }

    @GetMapping("/queries")
    public ResponseEntity<List<Queryinfo>> listUser() {
        List<Queryinfo> queryinfos;
        queryinfos = queryInfoService.listUser();
        return ResponseEntity.ok(queryinfos);
    }

    @GetMapping("/query/{id}")
    public ResponseEntity<Queryinfo> getUserById(@PathVariable("id") Long id) {
        Queryinfo queryinfo = queryInfoService.getUserById(id);
        if (queryinfo != null) {
            return ResponseEntity.ok(queryinfo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/tables")
    public ResponseEntity<?> createTable(@RequestBody TableMetadata request) {
        TableMetadata result = tableMetadataRepository.save(request);

        // Save the status in redis
        Queryinfo queryinfo = new Queryinfo();
        queryinfo.setQuery("CREATE TABLE: "+request.getTableName()+". Data: "+request.getColumns());
        if (result == null) {
            // Table already exists, return a bad request response with an error message
            String errorMessage = "Table already exists";
            queryinfo.setStatus(false);
            queryInfoService.saveUser(queryinfo);
            return ResponseEntity.badRequest().body(errorMessage);
        }
        // Table created successfully, return a success response with the created table metadata
        queryinfo.setStatus(true);
        queryInfoService.saveUser(queryinfo);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/tables")
    public ResponseEntity<Set<TableMetadata>> getAllTables() {
        Set<TableMetadata> tables = tableMetadataRepository.findAllTables();
        if (tables.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tables);
    }

    @PostMapping("/insert")
    public ResponseEntity<?> insertIntoTable(@RequestBody Table request){
        Table result = tableRepository.save(request);

        // Save the status in redis
        Queryinfo queryinfo = new Queryinfo();
        queryinfo.setQuery("INSERT INTO: "+request.getTableName()+". Data: "+request.getRows());
        if(result == null){
            String errorMessage = "Table doesn't exist.Please create a table";
            queryinfo.setStatus(false);
            queryInfoService.saveUser(queryinfo);
            return ResponseEntity.badRequest().body(errorMessage);
        }
        queryinfo.setStatus(true);
        queryInfoService.saveUser(queryinfo);
        return ResponseEntity.ok(result);
    }
}
