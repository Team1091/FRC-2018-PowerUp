package frc.team1091.robot.autonomous.commands;

import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.RobotControlSystems;

public class DriveForwards implements Command {

    private final double distanceInInches;
    private final RobotComponents robotComponents;
    private final RobotControlSystems controlSystems;

    public DriveForwards(double distanceInInches, RobotComponents robotComponents, RobotControlSystems controlSystems) {
        this.distanceInInches = distanceInInches;
        this.robotComponents = robotComponents;
        this.controlSystems = controlSystems;
    }


    @Override
    public Command execute() {
        if (distanceInInches > robotComponents.leftEncoder.get()) {
            controlSystems.differentialDrive.arcadeDrive(1, 0);
            return this;
        }
        controlSystems.differentialDrive.arcadeDrive(0, 0); // If you forget me i will die, clean me dude
        return null;  // if I return nothing, i run the next command if im in a command list
    }
}
