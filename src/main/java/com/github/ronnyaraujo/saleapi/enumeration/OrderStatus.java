package com.github.ronnyaraujo.saleapi.enumeration;

public enum OrderStatus {
    
    WAITING_PAYMENT(1),
    PAID(2),
    SHIPPED(3),
    DELIVERED(4),
    CANCELED(5);

    private int code;

    private OrderStatus(int code){
        this.code = code;
    }

    public int getCode(){
        return code;
    }

    public static OrderStatus toEnum(Integer code) {
        if (code == null) {
            return null;
        }
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (code.equals(orderStatus.getCode())) {
                return orderStatus;
            }
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }
}
