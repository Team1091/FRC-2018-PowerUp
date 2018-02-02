package frc.team1091.robot.wrapper;

import edu.wpi.first.wpilibj.DigitalInput;

public class DigitalInputWrapper {
    private DigitalInput input;

    public DigitalInputWrapper(DigitalInput input) {
        this.input = input;
    }

    public boolean get() {
        return input.get();
    }
}
