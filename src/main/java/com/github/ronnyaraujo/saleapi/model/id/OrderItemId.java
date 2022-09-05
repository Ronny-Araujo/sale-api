package com.github.ronnyaraujo.saleapi.model.id;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.github.ronnyaraujo.saleapi.model.OrderModel;
import com.github.ronnyaraujo.saleapi.model.ProductModel;

@Embeddable
public class OrderItemId implements Serializable {
    
	private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderModel orderModel;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductModel productModel;

    public OrderModel getOrderModel() {
        return orderModel;
    }

    public void setOrderModel(OrderModel orderModel) {
        this.orderModel = orderModel;
    }

    public ProductModel getProductModel() {
        return productModel;
    }

    public void setProductModel(ProductModel productModel) {
        this.productModel = productModel;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((orderModel == null) ? 0 : orderModel.hashCode());
        result = prime * result + ((productModel == null) ? 0 : productModel.hashCode());
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
        OrderItemId other = (OrderItemId) obj;
        if (orderModel == null) {
            if (other.orderModel != null)
                return false;
        } else if (!orderModel.equals(other.orderModel))
            return false;
        if (productModel == null) {
            if (other.productModel != null)
                return false;
        } else if (!productModel.equals(other.productModel))
            return false;
        return true;
    }    
}
