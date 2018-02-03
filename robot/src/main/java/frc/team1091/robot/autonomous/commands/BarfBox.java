package frc.team1091.robot.autonomous.commands;

import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.systems.DriveSystem;

public class BarfBox implements Command {
private RobotComponents components;
    public BarfBox(RobotComponents components, DriveSystem driveSystem) {
        this.components = components;
    }

    @Override
    public Command execute() {
        //Check to make sure box is present... Maybe?
        //Check to make sure platform is in close posistion
        //Angle the platform down to release the box
        components.elevatorMotor.set(-0.25);
                //stop after set amount of revolutions
        //Angle the box back up
        components.elevatorMotor.set(0.25);
                //stop at limit switch
        return null;
    }
}
