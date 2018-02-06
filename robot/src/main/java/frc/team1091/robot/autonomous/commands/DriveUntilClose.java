package frc.team1091.robot.autonomous.commands;

import edu.wpi.first.wpilibj.DriverStation;
import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.systems.DriveSystem;
import frc.team1091.robot.systems.VisionSystem;

/**
 * Used for final approach on the drop off point
 */
public class DriveUntilClose implements Command {

    private DriverStation.Alliance alliance;
    private RobotComponents components;
    private DriveSystem driveSystem;
    private VisionSystem visionSystem;

    public DriveUntilClose(DriverStation.Alliance alliance, RobotComponents components, DriveSystem driveSystem, VisionSystem visionSystem) {
        this.alliance = alliance;
        this.components = components;
        this.driveSystem = driveSystem;
        this.visionSystem = visionSystem;

    }

    @Override
    public Command execute() {
        // If we cannot see the goal, break out.  Maybe we are in the wrong spot, and its best not to drop

        // If we see the color goal we are looking for, drive towards it.

        // Drive forwards turning to keep the goal centered

        // If we are close enough, break
        return null;
    }
}
