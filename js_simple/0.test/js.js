"use strict";
///////////////////////////////////////
const book = {
  title: '1984',
};

book.author = 'Джордж Оруэлл';
book['year'] = 1949;

const newKey = 'pages';
book[newKey] = 328;
///////////////////////////////////////

function applyWeeklyRestock(bookCatalog) {

    for (let key in bookCatalog) {
        const book = bookCatalog[key];
        bookCatalog[key] = book + 2;
    }
    return bookCatalog;
}

console.log(applyWeeklyRestock({ "Герой нашего времени": 2, "Отцы и дети": 4, "Обломов": 1 }));



