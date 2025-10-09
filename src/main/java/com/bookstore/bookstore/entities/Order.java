package com.bookstore.bookstore.entities;

import java.time.Instant;
import java.util.Objects;
import java.util.Set;

public class Order {

    private Long id;
    private Instant moment;
    private StatusOrder status;

    private User client;

    private Set<OrderItem> items;

    private Payment payment;

    public Order() {
    }

    public Order(Long id, Instant moment, StatusOrder status) {
        this.id = id;
        this.moment = moment;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getMoment() {
        return moment;
    }

    public void setMoment(Instant moment) {
        this.moment = moment;
    }

    public StatusOrder getStatus() {
        return status;
    }

    public void setStatus(StatusOrder status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
