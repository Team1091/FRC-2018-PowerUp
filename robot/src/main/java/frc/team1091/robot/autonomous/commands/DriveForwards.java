package frc.team1091.robot.autonomous.commands;

import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.systems.DriveSystem;

public class DriveForwards implements Command {

    private final double distanceInInches;
    private final RobotComponents robotComponents;
    private final DriveSystem controlSystems;
    private final static double ticksPerInch = 360.0 / 4.0; //Fix this or I will find you (add in the correct tic per inch ratio

    private boolean firstRun = true;

    public DriveForwards(double distanceInInches, RobotComponents robotComponents, DriveSystem controlSystems) {
        this.distanceInInches = distanceInInches;
        this.robotComponents = robotComponents;
        this.controlSystems = controlSystems;
    }

    @Override
    public Command execute() {
        if (firstRun) {
            // Reset driving encoders so we can drive a set distance
            firstRun = false;
            robotComponents.leftEncoder.reset();
            robotComponents.rightEncoder.reset();
        }

        double l = robotComponents.leftEncoder.get();
        double r = robotComponents.rightEncoder.get();

        double distanceTraveled = Math.min(l, r) / ticksPerInch;

        if (distanceTraveled > distanceInInches) {
            controlSystems.drive(0, 0);
            return null;  // if I return nothing, I run the next command if im in a command list
        }

        double rightBias = (r - l) / 10.0;
        controlSystems.drive(1, limit(rightBias));
        return this;

    }

    private double limit(double qty) {
        return Math.min(Math.max(-0.5, qty), 0.5);
    }
}
