"use strict";

function createGuest(name, age, bookingId) {
    let obj = {
      name: name,
      age: age,
      bookingId: bookingId
    };

    return obj;
}

console.log(createGuest('Иван Иванов', 25, 'BR123ABC'));



