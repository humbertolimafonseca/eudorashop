/**
 * 
 */

// Settings object that controls default parameters for library methods:
accounting.settings = {
	currency: {
		symbol : "R$",   // default currency symbol is '$'
		format: "%s%v", // controls output: %s = symbol, %v = value/number (can be object: see below)
		decimal : ",",  // decimal point separator
		thousand: ".",  // thousands separator
		precision : 2   // decimal places
	},
	number: {
		precision : 0,  // default precision on numbers is 0
		thousand: ".",
		decimal : ","
	}
}


angular.module('filtrosEudora', []).filter('moeda', function() {
  return function(input) {
    return accounting.formatMoney(input);
  };
});