"use strict";

function getLastLetterLowercase(text) {
    //throw new Error('Удалите эту строку и реализуйте функцию');
    let str = text.trimEnd().at(-1)//.toLowerCase();    
    
    return str == undefined ? '' : str.toLowerCase();

}

console.log(getLastLetterLowercase(''));