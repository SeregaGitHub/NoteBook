"use strict";

const COST_PER_KILOMETER = 20;

function calculateDistanceCost(kilometers) {
    return COST_PER_KILOMETER * kilometers;    
};

console.log(calculateDistanceCost(999));  // 19980
