"use strict";
exports.__esModule = true;
var statementBuilder_1 = require("./statementBuilder");
var plays_json_1 = require("./plays.json");
var invoices_json_1 = require("./invoices.json");
var state = (0, statementBuilder_1.statement)(invoices_json_1["default"], plays_json_1["default"]);
console.log(state);
