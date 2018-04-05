package frc.team1091.robot.autonomous.commands;

import frc.team1091.robot.RobotComponents;

/**
 * Set the lifter height
 */

public class LiftElevator implements Command {
    private RobotComponents components;

    private long timeStarted = 0;

    public LiftElevator(RobotComponents components) {
        this.components = components;
    }

    @Override
    public Command execute(double dt) {
        if (timeStarted == 0) {
            timeStarted = System.currentTimeMillis();
        }

        if (timeStarted + 2000 > System.currentTimeMillis() ) {
            components.elevatorMotor.set(-1);
            return this;
        } else {
            components.elevatorMotor.set(0);
            return null;
        }

    }

    @Override
    public String getMessage() {
        return "Moving the elevator for a certain amount of time so that we are able to reach the low goal";
    }
}
