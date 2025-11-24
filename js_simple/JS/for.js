"use strict";

const fruits = ['яблоко', 'груша', 'банан'];

for (let index = 0; index < fruits.length; index++) {
  console.log('Товар:', fruits[index]);
}

const table = [
  ['Иван', 'Мария'],
  ['Пётр', 'Ольга'],
  ['Анна', 'Дмитрий'],
];

for (let row = 0; row < table.length; row++) {
  for (let col = 0; col < table[row].length; col++) {
    console.log(`Ячейка [${row}, ${col}]:`, table[row][col]);
 // console.log('Ячейка [' + row + ', ' +  col + ']:', table[row][col]);
  }
}

// break     - полностью прерывает выполнение цикла;
// continue  - пропускает текущую итерацию и переходит к следующей.

//  Если есть вложенные циклы, можно использовать метки — это имена, перед которыми ставится двоеточие:

outerLoop: for (let day = 1; day <= 3; day++) {
  for (let hour = 0; hour < 24; hour++) {
    if (hour > 20) {
      continue outerLoop;
    }
    console.log(`День ${day}, час ${hour}`);
  }
};

