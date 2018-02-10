package frc.team1091.robot.autonomous.commands;

import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.systems.DriveSystem;

public class DriveForwards implements Command {

    private final double distanceInInches;
    private final RobotComponents robotComponents;
    private final DriveSystem controlSystems;
    private final static double ticksPerInch = 360; //Fix this or I will find you (add in the correct tic per inch ratio

    public DriveForwards(double distanceInInches, RobotComponents robotComponents, DriveSystem controlSystems) {
        this.distanceInInches = distanceInInches;
        this.robotComponents = robotComponents;
        this.controlSystems = controlSystems;
    }


    @Override
    public Command execute() {

        float distanceTraveled = Math.min(robotComponents.leftEncoder.get(), robotComponents.rightEncoder.get());

        if (distanceTraveled > distanceInInches) {
            controlSystems.drive(0, 0);
            return null;  // if I return nothing, i run the next command if im in a command list
        }

        double rightBias = (robotComponents.rightEncoder.get() - robotComponents.leftEncoder.get()) / 10.0;
        controlSystems.drive(1, limit(rightBias));
        return this;

    }

    private double limit(double qty) {
        return Math.min(Math.max(0, qty), 1);
    }
}
