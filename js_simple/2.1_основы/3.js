"use strict";

const COST_PER_MINUTE = 10;
const COST_PER_KILOMETER = 20;

function calculateTimeCost(minutes) {
    return minutes * COST_PER_MINUTE;
}

function calculateDistanceCost(kilometers) {
    return kilometers * COST_PER_KILOMETER;
}

function calculateTotalCost(minutes, kilometers) {
    const timeCost = calculateTimeCost(minutes);
    const distanceCost = calculateDistanceCost(kilometers);
    return timeCost + distanceCost;
}

console.log(calculateTotalCost(15, 10));  // 350
