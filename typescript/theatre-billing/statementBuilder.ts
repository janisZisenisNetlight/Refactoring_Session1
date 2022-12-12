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

interface StatementData {
    customer: string;
    performances: Performance[]
}

export function statement(clientRequest: ClientRequest, plays: Plays) {
    let data: StatementData = {customer: clientRequest.customer, performances: clientRequest.performances}
    return renderPlainText(data, plays);
}

function renderPlainText(data: StatementData, plays: Plays) {
    let result = `Statement for ${data.customer}\n`
    result += statementsForPerformances(data, plays)
    result += `Amount owed is ${usd(totalAmount(data, plays) / 100)}\n`
    result += `You earned ${totalVolumeCredits(data, plays)} credits\n`
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

function totalVolumeCredits(data: StatementData, plays: Plays) {
    let result = 0
    for (let perf of data.performances) {
        const play = plays[perf.playId];
        // add volume credits
        result += volumeCreditsFor(perf, play)
    }
    return result
}

function totalAmount(data: StatementData, plays: Plays) {
    let result = 0;
    for (let perf of data.performances) {
        const play = plays[perf.playId];
        result += amountFor(perf, play)
    }
    return result
}

function statementsForPerformances(data: StatementData, plays: Plays) {
    let result = ""
    for (let perf of data.performances) {
        const play = plays[perf.playId];
        result += ` ${play.name}: ${usd(amountFor(perf, play) / 100)} (${perf.audience} seats)\n`
    }
    return result
}