package com.example.gameproject;

import android.widget.ProgressBar;

public class EnergyBar {
    public
    EnergyBar(ProgressBar bar, int maxValue){
        energyBar=bar;
        energyBar.setMax(maxValue);
        energyBar.setProgress(maxValue);
    }
    void addEnergy(int amount){
        energyBar.setProgress(energyBar.getProgress()+amount);
    }
    boolean isZero(){
        return energyBar.getProgress()<=0;
    }
    private
    int max;
    int current;
    ProgressBar energyBar;
}
