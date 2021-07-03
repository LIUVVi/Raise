package com.raise.entity;

import com.raise.activity.AdminIndexActivity;

public class Goods {

    private Integer id;
    private String goodsName;
    private String location;
    private Integer demand;
    private Integer raised;
    private String img_url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getDemand() {
        return demand;
    }

    public void setDemand(Integer demand) {
        this.demand = demand;
    }

    public Integer getRaised() {
        return raised;
    }

    public void setRaised(Integer raised) {
        this.raised = raised;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", goodsName='" + goodsName + '\'' +
                ", location='" + location + '\'' +
                ", demand=" + demand +
                ", raised=" + raised +
                ", img_url='" + img_url + '\'' +
                '}';
    }
}
