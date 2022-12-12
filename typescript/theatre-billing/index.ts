import {statement} from "./statementBuilder";
import plays from "./plays.json"
import invoices from "./invoices.json"

const state = statement(invoices, plays)

console.log(state)