package org.example;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.Test;

public class StatementBuilderTest {
    @Test
    public void test_bigCo() {
        Map<String, Play> availablePlays = Map.of(
                "hamlet", new Play("Hamlet", "tragedy"),
                "as-like", new Play("As You Like It", "comedy"),
                "othello", new Play("Othello", "tragedy")
        );
        ClientRequest clientRequest = new ClientRequest("BigCo", new Performance[] {
                new Performance("hamlet", 55),
                new Performance("as-like", 35),
                new Performance("othello", 40)}
        );

        var actual = StatementBuilder.statement(clientRequest, availablePlays);

        var expected =
                "Statement for BigCo\n"
                + "  Hamlet: $650.00 (55) seats\n"
                + "  As You Like It: $580.00 (35) seats\n"
                + "  Othello: $500.00 (40) seats\n"
                + "Amount owed is $1,730.00\n"
                + "You earned 47 credits\n";
        assertEquals(expected, actual);
    }

}