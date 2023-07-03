package com.demo.miko.repository;

import com.demo.miko.model.Queryinfo;

import java.util.List;

public interface QueryInfoDao {
    boolean saveUser(Queryinfo queryinfo);

    List<Queryinfo> listUser();

    Queryinfo getUserById(Long userId);
}
