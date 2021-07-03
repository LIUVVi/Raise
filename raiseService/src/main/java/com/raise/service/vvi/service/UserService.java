package com.raise.service.vvi.service;

import com.raise.service.vvi.entity.User;

/**
 * @author MeCity
 * @version V1.0.0
 * @description
 * @date 2020/12/18
 * @since 1.0
 */
public interface UserService {
    int insert(User user);

    int query(String username, String password, String level);
}
