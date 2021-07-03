package com.raise.service.vvi.service;

import com.raise.service.vvi.entity.Goods;

import java.util.List;

public interface GoodsService {
    List<Goods> queryAll();

    int insert(Goods goods);

    int update(int id, int raised);
}
