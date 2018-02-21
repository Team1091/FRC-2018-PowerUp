package frc.team1091.robot.autonomous.commands;

import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.systems.SuckerSystem;

public class SuckBox implements Command {
    private RobotComponents components;

    private long timePushedMs = 0;

    public SuckBox(RobotComponents components, SuckerSystem suckerSystem) {
        this.components = components;
    }

    @Override
    public Command execute(double dt) {
        if (timePushedMs == 0) {
            timePushedMs = System.currentTimeMillis();
        }

        if (timePushedMs < System.currentTimeMillis() + 1000) {
            components.suckerMotor.set(1);
            return this;
        } else {
            components.suckerMotor.set(0);
            return null;
        }

    }

    @Override
    public String getMessage() {
        return "Sucking Box";
    }
}
