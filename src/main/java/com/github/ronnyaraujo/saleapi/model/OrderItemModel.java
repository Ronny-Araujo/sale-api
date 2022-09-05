package com.github.ronnyaraujo.saleapi.model;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.ronnyaraujo.saleapi.model.id.OrderItemId;

@Entity
@Table(name = "tb_order_item")
public class OrderItemModel {

    @EmbeddedId
    private OrderItemId orderItemId = new OrderItemId();

    private Integer quantity;
    private BigDecimal price;

    public OrderItemModel() {
    }

    public OrderItemModel(OrderModel orderModel, ProductModel productModel, Integer quantity, BigDecimal price) {
        orderItemId.setOrderModel(orderModel);
        orderItemId.setProductModel(productModel);
        this.quantity = quantity;
        this.price = price;
    }

    @JsonIgnore
    public OrderModel getOrderModel() {
        return orderItemId.getOrderModel();
    }

    public void setOrderModel(OrderModel orderModel) {
        orderItemId.setOrderModel(orderModel);
    }

    public ProductModel getProductModel() {
        return orderItemId.getProductModel();
    }

    public void setProductModel(ProductModel productModel) {
        orderItemId.setProductModel(productModel);
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getSubTotal() {
        return getPrice().multiply(new BigDecimal(quantity));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((orderItemId == null) ? 0 : orderItemId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OrderItemModel other = (OrderItemModel) obj;
        if (orderItemId == null) {
            if (other.orderItemId != null)
                return false;
        } else if (!orderItemId.equals(other.orderItemId))
            return false;
        return true;
    }

    @Override
    public String toString() {

        DecimalFormat decFormat = new DecimalFormat("'R$ ' #,###,##0.00");

        StringBuilder sb = new StringBuilder();
        sb.append(getProductModel().getName());
        sb.append(", Qte: ");
        sb.append(getQuantity());
        sb.append(", Preço unitário: ");
        sb.append(decFormat.format(getPrice()));
        sb.append(", Subtotal: ");
        sb.append(decFormat.format(getSubTotal()));
        sb.append("\n");

        return sb.toString();
    }
}
