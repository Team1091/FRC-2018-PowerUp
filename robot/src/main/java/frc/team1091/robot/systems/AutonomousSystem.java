package frc.team1091.robot.systems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1091.robot.autonomous.commands.Command;

/**
 * This should follow a set series of Commands, where each command is an object that represents
 * something we want to do.  DriveForwards, Turn, UseMechanism, etc
 */
public class AutonomousSystem {
    private Command command;

    public void init(Command command) {
        this.command = command;
    }

    public void drive() {

        if (command == null) {
            log("Completed");
            return; // Done with autonomous
        }

        log(command.getMessage());
        command = command.execute();
    }

    public void log(String message){
        SmartDashboard.putString("Autonomous", message);
    }
}
