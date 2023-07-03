package com.demo.miko.service;

import com.demo.miko.model.Queryinfo;
import com.demo.miko.repository.QueryInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueryInfoServiceImpl implements QueryInfoService {

    @Autowired
    private QueryInfoDao queryDao;

    @Override
    public boolean saveUser(Queryinfo queryinfo) {
        return queryDao.saveUser(queryinfo);
    }

    @Override
    public List<Queryinfo> listUser() {
        return queryDao.listUser();
    }

    public Queryinfo getUserById(Long userId) {
        return queryDao.getUserById(userId);
    }
}
