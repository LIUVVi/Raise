package com.raise.service.vvi.dao;

import com.raise.service.vvi.entity.User;

public interface UserDao {
    int insert(User user);

    int query(String username, String password, String level);


}
