package com.demo.miko.service;

import com.demo.miko.model.Queryinfo;

import java.util.List;

public interface QueryInfoService {
    boolean saveUser(Queryinfo queryinfo);

    List<Queryinfo> listUser();

    Queryinfo getUserById(Long userId);
}
