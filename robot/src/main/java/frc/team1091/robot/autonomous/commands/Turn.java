package frc.team1091.robot.autonomous.commands;

import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.systems.DriveSystem;

/**
 * This turns the robot by x degrees.  Positive is to the right
 */
public class Turn implements Command {
    private final RobotComponents components;
    private final DriveSystem driveSystem;
    private final double turnRightInDegrees;

    private final static double ticksPerDegree = 1.0;

    private boolean firstRun = true;

    public Turn(double turnRightInDegrees, RobotComponents components, DriveSystem driveSystem) {
        this.components = components;
        this.driveSystem = driveSystem;
        this.turnRightInDegrees = turnRightInDegrees;
    }

    @Override
    public Command execute() {
        if (firstRun) {
            // Reset driving encoders so we can measure turn
            firstRun = false;
            components.leftEncoder.reset();
            components.rightEncoder.reset();
        }


        int ltix = components.leftEncoder.get();
        int rtix = components.rightEncoder.get();

        double difference = Math.abs(rtix - ltix) * ticksPerDegree; // ticks per degree

        if (difference > Math.abs(turnRightInDegrees)) {
            // We have turned far enough, we are done
            driveSystem.drive(0, 0);
            return null;

        } else {
            driveSystem.drive(0, (turnRightInDegrees > 0) ? 1 : -1);
            return this;
        }
    }

    @Override
    public String getMessage() {
        return "Turning " + turnRightInDegrees + " degrees";
    }
}
