package com.epam.shopping.gateway.model;

public class OrderLine {
    private Long id;
    private Long productId;
    private Float amount;
    private Float price;
    private Order order;

    public OrderLine() {
    }

    public OrderLine(Long id, Long productId, Float amount, Float price, Order order) {
        this.id = id;
        this.productId = productId;
        this.amount = amount;
        this.price = price;
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
