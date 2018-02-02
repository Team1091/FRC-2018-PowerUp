package frc.team1091.robot.autonomous.commands;

import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.systems.DriveSystem;

// Keep most of the top part
public class SpinOutOfControl implements Command {

    private final RobotComponents robotComponents;
    private final DriveSystem controlSystems;

    public SpinOutOfControl(RobotComponents robotComponents, DriveSystem controlSystems) {
        this.robotComponents = robotComponents;
        this.controlSystems = controlSystems;
    }

    //to like here ish more or less
    @Override
    public Command execute() {
        controlSystems.drive(0, 1); // full spin
        return this; //return this makes me loooooooooop forever
    }
}
