"use strict";

                                            spread - распаковывает
                                           // (всегда справа от = )

const arr = [1, 2, 3, 4, 5, 6];
console.log(...arr);  // 1 2 3 4 5 6

const str = 'Hello';
console.log(...str);  // H e l l o

const twoArr = [...str, 'world', ...arr];  // [H, e, l, l, o, world, 1, 2, 3, 4, 5, 6]

// ======================================================================================

                                             rest - запаковывает
                                          // (всегда слева от = )

const [a, b , ...others] = [1, 2, 3, 4, 5];   // rest - всегда должен идти последним !!!
console.log(a);       // 1
console.log(b);       // 2 
console.log(others);  // [3, 4, 5]

function calc(...numbers) {  // 1, 2, 3, 4, 5
    console.log(numbers);    // [1, 2, 3, 4, 5]  - всё что будет передано - соберётся в массив
    
}