"use strict";

                                                Functions

// 1. Объявление функций (function declaration)
// 2. Функциональные выражения (function expression)

// 1 (function declaration) :
function greet(name) {
  console.log('Привет, ' + name + '!');
};
// Функцию можно использовать в любом месте, даже до неё в файле


// 2 (function expression) :
let result = function (a, b) {
    return a + b;
};
// Можно записать в виде стрелочной функции:
let result_2 = (a, b) => {
    return a + b;
};

let result_3 = (a, b) => a + b

let result_4 = () => {
    'Some actions'
};

// Результат функции можно использовать только после неё в файле

//////////////////////////////////////////////////////////////////////////////////////////////////////////////

                                    //  CallBack Functions

// Это функции используемые в качестве аргументов для параметров других функций

function ask(question, yes, no) {
    if (confirm(question)) {
        yes()
    } else {
        no()
    }
};

// function yes() {
//     alert("Thanks")
// };

// function no() {
//     alert("OK, next time")
// };

ask("Are you agreed ?", 
    () => alert("Thanks"), 
    () => alert("OK, next time")
);