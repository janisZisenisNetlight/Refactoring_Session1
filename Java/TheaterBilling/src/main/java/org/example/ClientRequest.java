package org.example;

public class ClientRequest {
    public String customer;
    public Performance[] performances;

    public ClientRequest(String customer, Performance[] performances) {
        this.customer = customer;
        this.performances = performances;
    }
}