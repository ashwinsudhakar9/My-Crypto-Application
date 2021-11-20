package com.ashwin_sudhakar.mycryptoapplication.model;

public class ModelCoinDetails {

    private String currency;
    private String symbol;
    private String name;
    private String imageUrl;
    private Long current_price;
    private Long market_cap;
    private Integer market_cap_rank;
    private Long total_volume;
    private Double high_24h;
    private Double low_24h;
    private Double price_change_24h;
    private Double price_change_percentage_24h;
    private Double market_cap_change_24h;
    private Double market_cap_change_percentage_24h;
    private String last_updated_Date;
    private Double ath_change_percentage;
    private Double atl_change_percentage;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getCurrent_price() {
        return current_price;
    }

    public void setCurrent_price(Long current_price) {
        this.current_price = current_price;
    }

    public Long getMarket_cap() {
        return market_cap;
    }

    public void setMarket_cap(Long market_cap) {
        this.market_cap = market_cap;
    }

    public Integer getMarket_cap_rank() {
        return market_cap_rank;
    }

    public void setMarket_cap_rank(Integer market_cap_rank) {
        this.market_cap_rank = market_cap_rank;
    }

    public Long getTotal_volume() {
        return total_volume;
    }

    public void setTotal_volume(Long total_volume) {
        this.total_volume = total_volume;
    }

    public Double getHigh_24h() {
        return high_24h;
    }

    public void setHigh_24h(Double high_24h) {
        this.high_24h = high_24h;
    }

    public Double getLow_24h() {
        return low_24h;
    }

    public void setLow_24h(Double low_24h) {
        this.low_24h = low_24h;
    }

    public Double getPrice_change_24h() {
        return price_change_24h;
    }

    public void setPrice_change_24h(Double price_change_24h) {
        this.price_change_24h = price_change_24h;
    }

    public Double getPrice_change_percentage_24h() {
        return price_change_percentage_24h;
    }

    public void setPrice_change_percentage_24h(Double price_change_percentage_24h) {
        this.price_change_percentage_24h = price_change_percentage_24h;
    }

    public Double getMarket_cap_change_24h() {
        return market_cap_change_24h;
    }

    public void setMarket_cap_change_24h(Double market_cap_change_24h) {
        this.market_cap_change_24h = market_cap_change_24h;
    }

    public Double getMarket_cap_change_percentage_24h() {
        return market_cap_change_percentage_24h;
    }

    public void setMarket_cap_change_percentage_24h(Double market_cap_change_percentage_24h) {
        this.market_cap_change_percentage_24h = market_cap_change_percentage_24h;
    }

    public String getLast_updated_Date() {
        return last_updated_Date;
    }

    public void setLast_updated_Date(String last_updated_Date) {
        this.last_updated_Date = last_updated_Date;
    }

    public Double getAth_change_percentage() {
        return ath_change_percentage;
    }

    public void setAth_change_percentage(Double ath_change_percentage) {
        this.ath_change_percentage = ath_change_percentage;
    }

    public Double getAtl_change_percentage() {
        return atl_change_percentage;
    }

    public void setAtl_change_percentage(Double atl_change_percentage) {
        this.atl_change_percentage = atl_change_percentage;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public ModelCoinDetails(String currency, String symbol, String name, String imageUrl, Long current_price, Long market_cap, Integer market_cap_rank, Long total_volume, Double high_24h, Double low_24h, Double price_change_24h, Double price_change_percentage_24h, Double market_cap_change_24h, Double market_cap_change_percentage_24h, String last_updated_date, Double ath_change_percentage, Double atl_change_percentage) {
        this.currency = currency;
        this.symbol = symbol;
        this.name = name;
        this.imageUrl = imageUrl;
        this.current_price = current_price;
        this.market_cap = market_cap;
        this.market_cap_rank = market_cap_rank;
        this.total_volume = total_volume;
        this.high_24h = high_24h;
        this.low_24h = low_24h;
        this.price_change_24h = price_change_24h;
        this.price_change_percentage_24h = price_change_percentage_24h;
        this.market_cap_change_24h = market_cap_change_24h;
        this.market_cap_change_percentage_24h = market_cap_change_percentage_24h;
        this.last_updated_Date = last_updated_date;
        this.ath_change_percentage = ath_change_percentage;
        this.atl_change_percentage = atl_change_percentage;
    }
}
