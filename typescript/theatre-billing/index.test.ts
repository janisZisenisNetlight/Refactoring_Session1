import {statement} from "./statementBuilder";
import availablePlays from "./availablePlays.json"
import clientRequest from "./clientRequest.json"

describe('testing statement function', () => {

    it('calculates correctly', () => {
        const result = statement(clientRequest, availablePlays)

        let expected =
            "Statement for BigCo\n" +
            " Hamlet: $650.00 (55 seats)\n" +
            " As You Like It: $580.00 (35 seats)\n" +
            " Othello: $500.00 (40 seats)\n" +
            "Amount owed is $1,730.00\n" +
            "You earned 47 credits\n"
        expect(expected).toBe(result)
    })
})