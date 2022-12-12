interface ClientRequest {
    customer: string;
    performances: Performance[];
}

interface Performance {
    "playId": string;
    "audience": number;
}

interface Play {
    "name": string;
    "type": string;
}

interface Plays {
    [Key: string]: Play
}

export function statement(clientRequest: ClientRequest, plays: Plays) {
    let totalAmount = 0;
    let result = `Statement for ${clientRequest.customer}\n`

    for (let perf of clientRequest.performances) {
        const play = plays[perf.playId];
        // print line for this order
        result += ` ${play.name}: ${usd(amountFor(perf, play) / 100)} (${perf.audience} seats)\n`
    }

    for (let perf of clientRequest.performances) {
        const play = plays[perf.playId];
        totalAmount += amountFor(perf, play)
    }

    result += `Amount owed is ${usd(totalAmount / 100)}\n`
    result += `You earned ${totalVolumeCredits(clientRequest, plays)} credits\n`
    return result
}

function amountFor(aPerformance: Performance, play: Play) {
    let result = 0

    switch (play.type) {
        case "tragedy":
            result = 40000;
            if (aPerformance.audience > 30) {
                result += 1000 * (aPerformance.audience - 30);
            }
            break;
        case "comedy":
            result = 30000;
            if (aPerformance.audience > 20) {
                result += 10000 + 500 * (aPerformance.audience - 20)
            }
            result += 300 * aPerformance.audience;
            break;
        default:
            throw new Error(`unkown type: ${play.type}`)
    }

    return result
}

function volumeCreditsFor(aPerformance: Performance, play: Play) {
    let result = 0
    // add volume credits
    result += Math.max(aPerformance.audience - 30, 0);
    //  add extra credit for every ten comedy attendees
    if ("comedy" === play.type) result += Math.floor(aPerformance.audience / 5)

    return result
}

function usd(aNumber: number) {
    return new Intl.NumberFormat("en-US", {
        style: "currency",
        currency: "USD",
        minimumFractionDigits: 2
    }).format(aNumber)
}

function totalVolumeCredits(clientRequest: ClientRequest, plays: Plays){
    let result = 0
    for (let perf of clientRequest.performances) {
        const play = plays[perf.playId];
        // add volume credits
        result += volumeCreditsFor(perf, play)
    }
    return result
}