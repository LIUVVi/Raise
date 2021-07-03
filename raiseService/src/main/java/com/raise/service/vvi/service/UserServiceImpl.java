package com.raise.service.vvi.service;

import com.raise.service.vvi.dao.UserDao;
import com.raise.service.vvi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author MeCity
 * @version V1.0.0
 * @description
 * @date 2020/12/18
 * @since 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public int insert(User user) {
        return userDao.insert(user);
    }

    @Override
    public int query(String username, String password, String level) {
        return userDao.query(username, password,level);
    }

}
