package com.hand.miaosha.vo;

import java.util.Date;

/**
 * @Class: GoodsData
 * @description:
 * @Author: hongzhi.zhao
 * @Date: 2018-11-15 10:39
 */
public class GoodsData {


    private Long id;
    private String goods_name;
    private String goods_title;
    private String goods_img;
    private String goods_detail;
    private Double goods_price;
    private Integer goods_stock;
    private Integer stock_count;
    private Date start_date;
    private Date end_date;
    private Double miaosha_price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_title() {
        return goods_title;
    }

    public void setGoods_title(String goods_title) {
        this.goods_title = goods_title;
    }

    public String getGoods_img() {
        return goods_img;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }

    public String getGoods_detail() {
        return goods_detail;
    }

    public void setGoods_detail(String goods_detail) {
        this.goods_detail = goods_detail;
    }

    public Double getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(Double goods_price) {
        this.goods_price = goods_price;
    }

    public Integer getGoods_stock() {
        return goods_stock;
    }

    public void setGoods_stock(Integer goods_stock) {
        this.goods_stock = goods_stock;
    }

    public Integer getStock_count() {
        return stock_count;
    }

    public void setStock_count(Integer stock_count) {
        this.stock_count = stock_count;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public Double getMiaosha_price() {
        return miaosha_price;
    }

    public void setMiaosha_price(Double miaosha_price) {
        this.miaosha_price = miaosha_price;
    }
}
