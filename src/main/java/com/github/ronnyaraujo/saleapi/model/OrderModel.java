package com.github.ronnyaraujo.saleapi.model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.github.ronnyaraujo.saleapi.enumeration.OrderStatus;

@Entity
@Table(name = "tb_order")
public class OrderModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant instant;

    private Integer orderStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel userModel;

    @OneToMany(mappedBy = "orderItemId.orderModel")
	private List<OrderItemModel> items = new ArrayList<>();

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private PaymentModel payment;

    public OrderModel() {
    }

    public OrderModel(Long id, Instant instant, OrderStatus orderStatus, UserModel userModel) {
        this.id = id;
        this.instant = instant;
        if (orderStatus != null) {
            this.orderStatus = orderStatus.getCode();
        }        
        this.userModel = userModel;
    }

    public List<OrderItemModel> getItems() {
        return items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getInstant() {
        return instant;
    }

    public void setInstant(Instant instant) {
        this.instant = instant;
    }

    public OrderStatus getOrderStatus() {
        return OrderStatus.toEnum(orderStatus);
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus.getCode();
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public PaymentModel getPayment() {
        return payment;
    }

    public void setPayment(PaymentModel payment) {
        this.payment = payment;
    }

    public BigDecimal getTotalOrder() {
        BigDecimal totalOrder = new BigDecimal("0.0");
        for (OrderItemModel orderItemModel : items) {
            totalOrder = totalOrder.add(orderItemModel.getSubTotal());
        }
        return totalOrder;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        OrderModel other = (OrderModel) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        DecimalFormat decFormat = new DecimalFormat("'R$ ' #,###,##0.00");
        
        StringBuilder sb = new StringBuilder();
		sb.append("Pedido número: ");
		sb.append(getId());
		sb.append("\nCliente: ");
		sb.append(getUserModel().getName());		
		sb.append("\nDetalhes:\n");
		for (OrderItemModel ip : getItems()) {
			sb.append(ip.toString());
		}
		sb.append("Valor total: ");
		sb.append(decFormat.format(getTotalOrder()));
        sb.append("\nPagamento:\n");
		sb.append(getPayment());
		return sb.toString();

    }    
}
