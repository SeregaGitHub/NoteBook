"use strict";

const fruits = ['яблоко', 'банан', 'груша'];
console.log(fruits[0]);  // => "яблоко"

fruits.forEach((fruit) => console.log(fruit));
// => "яблоко"
// => "банан"
// => "груша"
delete fruits[1];
// => ['яблоко', <empty>, 'груша']
console.log(fruits.length); // => 3

fruits.splice(0, 1);
console.log(fruits);
// => ['банан', 'груша']

push       // Добавляет элемент(ы) в конец массива
pop        // Удаляет последний элемент и возвращает его
shift      // Удаляет первый элемент
unshift    // Добавляет элемент(ы) в начало массива
splice     // Мощный метод для удаления, замены или вставки элементов

fruits.splice(1, 1, 'груша');
console.log(fruits);  // => ['яблоко', 'груша', 'киви']
