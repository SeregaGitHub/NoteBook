"use strict";

function calculateCarePrice(baseFeedPrice, age) {
    let discount;
    if (age < 1) {
        discount = 0.2;
    } else if (age > 7) {
        discount = 0.5;
    } else {
        discount = 0;
    }

    return baseFeedPrice + (baseFeedPrice * discount);
}

