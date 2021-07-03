package com.raise.service.vvi.dao;

import com.raise.service.vvi.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author MeCity
 * @version V1.0.0
 * @description
 * @date 2020/12/18
 * @since 1.0
 */
@Repository
public class UserDaoImpl implements UserDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public int insert(User user) {
        String sql = "insert into userinfo (username, password, level) values (?,?,?)";
        return jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getLevel());
    }

    @Override
    public int query(String username, String password, String level) {
        String sql = "select count(*) from userinfo where username=?&&password=?&&level=?";

        return jdbcTemplate.queryForObject(sql, Integer.class, username, password,level);
    }
}
