package org.example;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<String,Play> availablePlays = Map.of(
                "hamlet", new Play("Hamlet", "tragedy"),
                "as-like", new Play("As You Like It", "comedy"),
                "othello", new Play("Othello", "tragedy")
        );
        ClientRequest clientRequest = new ClientRequest("BigCo", new Performance[] {
                new Performance("hamlet", 55),
                new Performance("as-like", 35),
                new Performance("othello", 40)}
        );

        var statement = StatementBuilder.statement(clientRequest, availablePlays);

        System.out.println(statement);
    }
}