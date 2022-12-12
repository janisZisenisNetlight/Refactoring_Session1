package org.example;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;
public class StatementBuilder {
    public static String statement(ClientRequest clientRequest, Map<String, Play> plays) {
        var totalAmount = 0;
        var volumeCredits = 0;
        var result = "Statement for " + clientRequest.customer + "\n";
        var currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);

        for (var perf: clientRequest.performances) {
            var play = plays.get(perf.playId);
            var thisAmount = amountFor(perf, play);

            // add volume credits
            volumeCredits += volumeCreditsFor(perf, play);

            // print line for this order
            result += "  " + play.name + ": " + currencyFormatter.format(thisAmount / 100) + " (" + perf.audience + ") seats\n";
            totalAmount += thisAmount;
        }

        result += "Amount owed is " + currencyFormatter.format(totalAmount / 100) + "\n";
        result += "You earned " + volumeCredits + " credits\n";
        return result;
    }

    public static int amountFor(Performance performance, Play play) {
        var result = 0;

        switch(play.type) {
            case "tragedy":
                result = 40000;
                if(performance.audience > 30) {
                    result += 1000 * (performance.audience -30);
                }
                break;
            case "comedy":
                result = 30000;
                if(performance.audience > 20) {
                    result += 10000 + 500 * (performance.audience - 20);
                }
                result += 300 * performance.audience;
                break;
            default:
                throw new RuntimeException("unknown type: " + play.type);
        }

        return result;
    }

    public static int volumeCreditsFor(Performance performance, Play play) {
        var result = 0;
        // add volume credits
        result += Math.max(performance.audience - 30, 0);
        // add extra credit for every ten comedy attendees
        if("comedy" == play.type) result += Math.floor(performance.audience / 5);
        return result;
    }
}
