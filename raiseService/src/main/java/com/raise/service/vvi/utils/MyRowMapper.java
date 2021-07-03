package com.raise.service.vvi.utils;

import com.raise.service.vvi.entity.Goods;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author MeCity
 * @version V1.0.0
 * @description
 * @date 2020/12/22
 * @since 1.0
 */
public class MyRowMapper implements RowMapper<Goods> {
    @Override
    public Goods mapRow(ResultSet resultSet, int i) throws SQLException {
        Integer id = resultSet.getInt("id");
        String goodsName = resultSet.getString("goods_name");
        String location = resultSet.getString("location");
        Integer demand = resultSet.getInt("demand");
        Integer raised = resultSet.getInt("raised");
        String img_url = resultSet.getString("img_url");

        Goods goods = new Goods();
        goods.setId(id);
        goods.setGoodsName(goodsName);
        goods.setLocation(location);
        goods.setDemand(demand);
        goods.setRaised(raised);
        goods.setImg_url(img_url);

        return goods;

    }
}
