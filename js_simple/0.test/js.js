"use strict";

const fishPerDay = [3, 8, 2, 5, 7, 1];

function fixFishCountLog(fishPerDay) {

    for (let index = 0; index < fishPerDay.length; index++) {
        if (index % 3 === 0) {
            fishPerDay.splice(index, 1, fishPerDay[index] + 1);
        }
    }

    return fishPerDay;
}

console.log(fixFishCountLog(fishPerDay));

