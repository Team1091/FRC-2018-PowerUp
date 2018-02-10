package frc.team1091.robot.autonomous.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.systems.DriveSystem;

/**
 * This turns the robot by x degrees.  Positive is to the right
 */
public class Turn implements Command {
    private final RobotComponents components;
    private final DriveSystem driveSystem;
    private final double turnDegrees;

    private double requiredTurnDistance;
    private boolean isTurnRight;
    private boolean firstRun = true;

    public Turn(double turnDegrees, RobotComponents components, DriveSystem driveSystem) {
        this.components = components;
        this.driveSystem = driveSystem;
        this.turnDegrees = turnDegrees;
        this.requiredTurnDistance = (Math.abs(turnDegrees/360))* (29*Math.PI);
//        SmartDashboard.putNumber("requiredTurn",requiredTurnDistance);
        isTurnRight = turnDegrees > 0;
    }

    @Override
    public Command execute() {
        if (firstRun) {
            // Reset driving encoders so we can measure turn
            firstRun = false;
            components.leftEncoder.reset();
            components.rightEncoder.reset();
        }


        double ltix = components.leftEncoder.getDistance();
        double rtix = components.rightEncoder.getDistance();

        double difference = Math.abs(rtix - ltix)/2.0; // ticks per degree

        if (difference > requiredTurnDistance) {
            // We have turned far enough, we are done
            driveSystem.drive(0, 0);
            return null;

        } else {
            driveSystem.drive(0, (isTurnRight) ? 1 : -1);
            return this;
        }
    }

    @Override
    public String getMessage() {
        return "Turning " + turnDegrees + " degrees";
    }
}
