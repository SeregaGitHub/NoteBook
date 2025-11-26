"use strict";

const user = {
  name: 'Марина',
  age: 28,
  isOnline: true,
  contact: {
    email: 'marina@example.com',
    phone: '+12345678',
  },
  hobbies: ['рисование', 'плавание'],
  greet() {
    return `Привет, я ${this.name}`;
  },
};

user[id] = '111';  // add field

delete user.id;    // delete field
delete user[id];