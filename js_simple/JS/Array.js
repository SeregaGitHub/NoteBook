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

//   Не изменяйте массив внутри forEach — это может привести к странным результатам: 
// добавленные элементы игнорируются, удалённые могут "перепрыгнуть" и т.д. !!!

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


const fruits_2 = [
    'яблоко', 
    'банан', 
    'груша',
    'лимон',
    'ананас',
    'киви',
    'персик'];

const [fruitOne, , fruitThree='someFruit', , , , , , someFruit='someFruit'] = fruits_2;

console.log(fruitOne);    // яблоко
console.log(fruitThree);  // груша
console.log(someFruit);   // someFruit (index out of born)

//==============================================================================================================

Array.map(some_function);            // проходит по каждому элементу массива и возвращает новый массив, преобразованный с помощью переданной функции
                                         // numbers.map((number) => number * 2);
Array.filter(some_function);         // создаёт новый массив, оставляя только те элементы, для которых функция возвращает true
                                         // numbers.filter((number) => number % 2 === 0);
Array.reduce(some_array);            // сводит массив к одному значению
                                         // reduce((sum, price) => sum + price, 0);
Array.revers()                       // переворачивает массив (изменяет исходный)
                                         // numbers.reverse();
Array.slice(firstIndex, lastIndex);  // извлекает часть массива
                                         // numbers.slice(1, 3) - извлечёт с 1 индекса по 2-ой вкл
Array.splice(firstIndex, lastIndex); // удаляет, заменяет или вставляет элементы (изменяет массив)
                                         //
                                            const colors2 = ['red', 'green', 'blue'];

                                            // Вставим элемент на позицию 1
                                            colors2.splice(1, 0, 'yellow');
                                            console.log(colors2); // ['red', 'yellow', 'green', 'blue']
                                            
                                            // Удалим 2 элемента и заменим их
                                            const removed = colors2.splice(2, 2, 'purple');
                                            console.log(removed); // ['green', 'blue']
                                            console.log(colors2);  // ['red', 'yellow', 'purple']
                                         //
Array.sort(some_array);               // сортирует массив (изменяет массив)
                                         // numbers.sort((a, b) => a - b);  - Чтобы правильно отсортировать числа, передайте функцию сравнения

Array.some();  // Проверяет, есть ли хотя бы один элемент, для которого функция-проверка возвращает true
const temperatures = [15, 18, 22, 27, 31];
const isHot = (t) => t > 30;
const hasHotDays = temperatures.some(isHot);    // true — потому что 31 > 30

Array.every();  // Проверяет, соответствуют ли все элементы условию
const scores = [78, 82, 90, 100];
const isAbovePass = (score) => score >= 60;
const allPassed = scores.every(isAbovePass);   // true — все оценки выше 60

Array.includes();  // Проверяет наличие значения
const colors = ['red', 'green', 'blue'];
const hasYellow = colors.includes('yellow');   // false
const hasBlue = colors.includes('blue');       // true

