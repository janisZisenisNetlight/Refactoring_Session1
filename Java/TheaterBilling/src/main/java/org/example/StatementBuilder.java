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
            var thisAmount = 0;

            switch(play.type) {
                case "tragedy":
                    thisAmount = 40000;
                    if(perf.audience > 30) {
                        thisAmount += 1000 * (perf.audience -30);
                    }
                    break;
                case "comedy":
                    thisAmount = 30000;
                    if(perf.audience > 20) {
                        thisAmount += 10000 + 500 * (perf.audience - 20);
                    }
                    thisAmount += 300 * perf.audience;
                    break;
                default:
                    throw new RuntimeException("unknown type: " + play.type);
            }

            // add volume credits
            volumeCredits += Math.max(perf.audience - 30, 0);
            // add extra credit for every ten comedy attendees
            if("comedy" == play.type) volumeCredits += Math.floor(perf.audience / 5);

            // print line for this order
            result += "  " + play.name + ": " + currencyFormatter.format(thisAmount/100) + " (" + perf.audience + ") seats\n";
            totalAmount += thisAmount;
        }

        result += "Amount owed is " + currencyFormatter.format(totalAmount/100) + "\n";
        result += "You earned " + volumeCredits + " credits\n";

        return result;
    }
}
