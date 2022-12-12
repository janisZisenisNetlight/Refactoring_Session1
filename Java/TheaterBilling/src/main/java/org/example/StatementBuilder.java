package org.example;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;
public class StatementBuilder {
    public static String statement(ClientRequest clientRequest, Map<String, Play> plays) {
        var statementData = new StatementData(clientRequest.customer, clientRequest.performances);
        return renderPlainText(statementData, plays);
    }

    private static String renderPlainText(StatementData data, Map<String, Play> plays) {
        var result = "Statement for " + data.customer + "\n";
        result += statementsForPerformances(data, plays);
        result += "Amount owed is " + usd(totalAmount(data, plays)) + "\n";
        result += "You earned " + totalVolumeCredits(data, plays) + " credits\n";
        return result;
    }

    private static String statementsForPerformances(StatementData data, Map<String, Play> plays) {
        String result = "";
        for (var perf: data.performances) {
            var play = plays.get(perf.playId);
            result += "  " + play.name + ": " + usd(amountFor(perf, play)) + " (" + perf.audience + ") seats\n";
        }

        return result;
    }

    private static int totalAmount(StatementData data, Map<String, Play> plays) {
        var result = 0;
        for (var perf: data.performances) {
            var play = plays.get(perf.playId);
            result += amountFor(perf, play);
        }
        return result;
    }

    public static String usd(int amount) {
        return NumberFormat.getCurrencyInstance(Locale.US).format(amount / 100);
    }

    public static int totalVolumeCredits(StatementData data, Map<String, Play> plays) {
        var result = 0;
        for (var perf: data.performances) {
            var play = plays.get(perf.playId);
            result += volumeCreditsFor(perf, play);
        }
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

    public static String format(int number) {
        var currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        return currencyFormatter.format(number / 100);
    }
}
