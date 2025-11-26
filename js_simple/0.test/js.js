"use strict";

function timeToBrewCoffee(name) {
    switch (name) {
        case 'Эспрессо': 
            return 1;
        case 'Американо': 
            return 2;
        case 'Капучино': 
            return 2;
        case 'Латте': 
            return 3;
        case 'Мокко': 
            return 4;            
        default:
            return 2.5;
    }
};



function pendingOrders(timeLeft, orders) {
    
    while (orders.length > 0) {
        
        if (timeLeft > 0) {
            const nextOrder = orders.shift();
            const orderTime = timeToBrewCoffee(nextOrder);
            timeLeft = timeLeft - orderTime;
        } else {
            return orders;
        }
    }

    return orders;
};

let timeLeft1 = 10;
let orders1 = ["Капучино", "Латте", "Мокко"];

console.log(pendingOrders(timeLeft1, orders1));


