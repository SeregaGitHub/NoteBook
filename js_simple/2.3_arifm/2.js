"use strict";

function dailyRateFromHourly(hourlyRate) {
    return hourlyRate * 10;
};

function daysRentableWithinBudget(budget, hourlyRate) {
    //throw new Error('Удалите эту строку и реализуйте функцию');
    const X = dailyRateFromHourly(hourlyRate);

    return Math.trunc(budget / X);
}

console.log(daysRentableWithinBudget(1000, 15)); // 6
