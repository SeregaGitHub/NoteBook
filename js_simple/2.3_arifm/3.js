"use strict";

// Для долгосрочных клиентов арендодатель предлагает скидку. 
// Если аренда длится 7 полных дней, то применяется скидка. 
// Остальные дни (менее 7) считаются по обычной суточной ставке. 
// В одном календарном месяце арендодатель считает неделю фиксированной: 
// 7 дней = 1 неделя. Помогите оценить общую стоимость аренды, 
// зная почасовую ставку, общее число дней аренды и процент скидки для каждой полной недели. 
// Скидка передаётся в виде десятичного числа, где 20% записывается как 0.2. 
// Результат должен быть округлён вверх до целого числа.

// 20 10 0.1  -  1860
// 15 15 0.2  -  1830

function dailyRateFromHourly(hourlyRate) {
    return hourlyRate * 10;
};

function rentalPriceWithWeeklyDiscount(hourlyRate, numDays, discount) {
    // throw new Error('Удалите эту строку и реализуйте функцию');
    const HR = dailyRateFromHourly(hourlyRate);
    const daysWithDiscount = Math.floor(numDays / 7) * 7;
    const daysWithNoDiscount = numDays % 7;

    const sumDaysWithDiscount = HR * daysWithDiscount;
    console.log(sumDaysWithDiscount);
    const totalDiscount = sumDaysWithDiscount * discount;
    console.log(totalDiscount);
    const priceWithDiscount = sumDaysWithDiscount - totalDiscount;
    console.log(priceWithDiscount);
    const priceWithNoDiscount = HR * daysWithNoDiscount;
    console.log(priceWithNoDiscount);
    
    return sumDaysWithDiscount - totalDiscount + priceWithNoDiscount;
    
};

console.log(rentalPriceWithWeeklyDiscount(20, 10, 0.1));




// console.log(((150 * 14) - 420) + 150);
