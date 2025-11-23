"use strict";

function getGenrePrefix(text) {
    //throw new Error('Удалите эту строку и реализуйте функцию');
    let str = text.trimStart().slice(0, 2)//.toLowerCase();    
    
    return str == '' ? '' : str.toUpperCase();
}

console.log(getGenrePrefix(''));