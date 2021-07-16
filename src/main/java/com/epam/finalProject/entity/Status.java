package com.epam.finalProject.entity;

public enum Status {
    REGISTERED, CANCELED, PAID;

    public static Status getStatus(Order order) {
        int orderStatus = order.getOrderStatus();
        return Status.values()[orderStatus];
    }

    public String getName() {
        return name().toLowerCase();
    }
}
