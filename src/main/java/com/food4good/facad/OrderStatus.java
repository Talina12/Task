package com.food4good.facad;

public enum OrderStatus {
    NEW("new"),
    DELIVERED("delivered"),
    CANCELED("canceled");

    private String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
