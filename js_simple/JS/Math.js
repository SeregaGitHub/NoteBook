"use strict";

Math.floor(4.7); // 4 — округление вниз
Math.ceil(4.1);  // 5 — округление вверх
Math.round(4.7); // 5 — математическое округление
Math.trunc(4.7); // 4 — отбрасывание дробной части


//Number() может преобразовать почти любое значение в число:
let timestamp = Number(new Date("2023-01-01"));
// Вернёт количество миллисекунд с 1970 года


//Метод toFixed() округляет число до указанного количества знаков после запятой и возвращает строку:
let price = 15.6789;

price.toFixed(2);  // "15.68"
price.toFixed(0);  // "16"
price.toFixed(4);  // "15.6789"

let whole = 10;
whole.toFixed(2);  // "10.00"


// Преобразование в число с toPrecision()
// Определяет общее количество значащих цифр:

let num = 123.456;

num.toPrecision(4);  // "123.5"
num.toPrecision(2);  // "1.2e+2"
num.toPrecision(6);  // "123.456"


Number.MAX_VALUE;        // Самое большое представимое число
Number.MIN_VALUE;        // Самое маленькое (положительное)
Number.MAX_SAFE_INTEGER; // Максимально "безопасное" целое
Number.MIN_SAFE_INTEGER; // Минимально "безопасное" целое