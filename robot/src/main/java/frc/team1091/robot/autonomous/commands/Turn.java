package frc.team1091.robot.autonomous.commands;

import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.systems.DriveSystem;

/**
 * This turns the robot by x degrees.  Positive is to the right
 */
public class Turn implements Command {
    private final RobotComponents components;
    private final DriveSystem driveSystem;

    public Turn(double turnRightInDegrees, RobotComponents components, DriveSystem driveSystem) {
        this.components = components;
        this.driveSystem = driveSystem;
    }

    @Override
    public Command execute() {
        //TODO: make the turn code so that the robot doesn't just run turn once
        driveSystem.drive(0, 1);

        return null;
    }
}
