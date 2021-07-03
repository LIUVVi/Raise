package com.raise.service.vvi.dao;

import com.raise.service.vvi.entity.Goods;
import com.raise.service.vvi.utils.MyRowMapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author MeCity
 * @version V1.0.0
 * @description
 * @date 2020/12/22
 * @since 1.0
 */
@Repository
public class GoodsDaoImpl implements GoodsDao{

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Goods> queryAll() {
        String sql = "select * from goods";
        return jdbcTemplate.query(sql, new MyRowMapper());
    }

    @Override
    public int insert(Goods goods) {
        String sql = "insert into goods (goods_name, location, demand, img_url) values (?,?,?,?)";
        return jdbcTemplate.update(sql, goods.getGoodsName(), goods.getLocation(), goods.getDemand(), goods.getImg_url());
    }

    @Override
    public int update(int id, int raised) {
        String sql = "update goods set raised=raised+? where id=?";
        return jdbcTemplate.update(sql,raised,id);
    }
}
