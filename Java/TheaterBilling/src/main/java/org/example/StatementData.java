package org.example;

public class StatementData {
    public String customer;
    public Performance[] performances;

    public StatementData(String customer, Performance[] performances) {
        this.customer = customer;
        this.performances = performances;
    }
}