package com.example.cs300_dailyapple.Models;

public class WaterOverall {
    // Attributes
    private Double waterTarget;
    private Double waterAbsorbed;

    // Methods
    public WaterOverall() {
        this.waterTarget = 0.0;
        this.waterAbsorbed = 0.0;
    }

    public WaterOverall(Double waterTarget, Double waterAbsorbed) {
        this.waterTarget = waterTarget;
        this.waterAbsorbed = waterAbsorbed;
    }

    public Double getWaterAbsorbed() {
        return waterAbsorbed;
    }

    public Double getWaterTarget() {
        return waterTarget;
    }

    public void setWaterAbsorbed(Double waterAbsorbed) {
        this.waterAbsorbed = waterAbsorbed;
    }

    public void setWaterTarget(Double waterTarget) {
        this.waterTarget = waterTarget;
    }
}
