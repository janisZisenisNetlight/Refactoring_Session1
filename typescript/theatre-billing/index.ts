import {statement} from "./statementBuilder";
import availablePlays from "./availablePlays.json"
import clientRequest from "./clientRequest.json"

const state = statement(clientRequest, availablePlays)
console.log(state)