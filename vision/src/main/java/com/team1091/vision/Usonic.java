package com.team1091.vision;

import edu.wpi.first.wpilibj.AnalogInput;

public class Usonic {

    public double getValue() {
        AnalogInput analogOne = new AnalogInput(0);
        double mV = analogOne.getValue();
        double mm = mV / 0.9777;
        double in = mm / 25.4;
        return in;
    }
}
