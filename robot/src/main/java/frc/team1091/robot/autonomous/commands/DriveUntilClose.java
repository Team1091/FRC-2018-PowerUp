package frc.team1091.robot.autonomous.commands;

import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.systems.DriveSystem;

/**
 * Used for final approach on the drop off point
 */
public class DriveUntilClose implements Command {

    public DriveUntilClose(RobotComponents components, DriveSystem driveSystem) {

    }

    @Override
    public Command execute() {
        // If we cannot see the goal, break out.  Maybe we are in the wrong spot, and its best not to drop

        // Drive forwards turning to keep the goal centered

        // If we are close enough, break
        return null;
    }
}
