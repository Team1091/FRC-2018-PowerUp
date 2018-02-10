package frc.team1091.robot.autonomous.commands;

import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.systems.DriveSystem;

public class DriveForwards implements Command {

    private final double distanceInInches;
    private final RobotComponents robotComponents;
    private final DriveSystem controlSystems;

    private boolean firstRun = true;

    public DriveForwards(double distanceInInches, RobotComponents robotComponents, DriveSystem controlSystems) {
        this.distanceInInches = distanceInInches;
        this.robotComponents = robotComponents;
        this.controlSystems = controlSystems;
    }

    @Override
    public Command execute(double dt) {
        if (firstRun) {
            // Reset driving encoders so we can drive a set distance
            firstRun = false;
            robotComponents.leftEncoder.reset();
            robotComponents.rightEncoder.reset();
        }

        double l = robotComponents.leftEncoder.getDistance();
        double r = robotComponents.rightEncoder.getDistance();

        double distanceTraveled = Math.min(l, r);

        if (distanceTraveled > distanceInInches) {
            controlSystems.drive(0, 0, dt);
            return null;  // if I return nothing, I run the next command if im in a command list
        }

        double rightBias = (r - l) / 10.0;
        controlSystems.drive(1, limit(rightBias), dt);
        return this;

    }

    @Override
    public String getMessage() {
        return "Driving Forwards " + distanceInInches + " inches";
    }

    private double limit(double qty) {
        return Math.min(Math.max(-0.5, qty), 0.5);
    }
}
