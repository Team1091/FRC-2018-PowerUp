package frc.team1091.robot.drive;

import frc.team1091.robot.autonomous.commands.Command;

/**
 * This should follow a set series of Commands, where each command is an object that represents
 * something we want to do.  DriveForwards, Turn, UseMechanism, etc
 */
public class AutonomousDriveSystem {
    private Command command;

    public void init(Command command) {
        this.command = command;
    }

    public void drive() {

        if (command == null)
            return; // Done with autonomous

        command = command.execute();

    }
}
