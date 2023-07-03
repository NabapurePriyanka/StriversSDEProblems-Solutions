package com.demo.miko.repository;

import com.demo.miko.model.Queryinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QueryInfoDaoImpl implements QueryInfoDao {
    private static final String KEY = "USER";
    private static final String NEXT_ID_KEY = "NEXT_USER_ID";
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean saveUser(Queryinfo queryinfo) {
        try {
            Long userId = getNextUserId();
            queryinfo.setId(userId);
            redisTemplate.opsForHash().put(KEY, queryinfo.getId(), queryinfo);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Queryinfo> listUser() {
         List<Queryinfo> queryinfos;
         queryinfos = redisTemplate.opsForHash().values(KEY);
         return queryinfos;
    }

    @Override
    public Queryinfo getUserById(Long userId) {
        return (Queryinfo) redisTemplate.opsForHash().get(KEY, userId);
    }

    private Long getNextUserId() {
        return redisTemplate.opsForValue().increment(NEXT_ID_KEY, 1);
    }
}
