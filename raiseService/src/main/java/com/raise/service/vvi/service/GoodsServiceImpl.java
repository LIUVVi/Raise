package com.raise.service.vvi.service;

import com.raise.service.vvi.dao.GoodsDao;
import com.raise.service.vvi.entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author MeCity
 * @version V1.0.0
 * @description
 * @date 2020/12/22
 * @since 1.0
 */
@Service
public class GoodsServiceImpl implements GoodsService{

    @Autowired
    private GoodsDao goodsDao;

    @Override
    public List<Goods> queryAll() {
        return goodsDao.queryAll();
    }

    @Override
    public int insert(Goods goods) {
        return goodsDao.insert(goods);
    }

    @Override
    public int update(int id, int raised) {
        return goodsDao.update(id,raised);
    }
}
