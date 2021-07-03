package com.raise.service.vvi.dao;

import com.raise.service.vvi.entity.Goods;

import java.util.List;

public interface GoodsDao {
    List<Goods> queryAll();

    int insert(Goods goods);

    int update(int id, int raised);
}
