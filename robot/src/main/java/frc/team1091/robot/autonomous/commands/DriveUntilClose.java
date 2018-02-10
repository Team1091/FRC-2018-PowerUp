package frc.team1091.robot.autonomous.commands;

import edu.wpi.first.wpilibj.DriverStation;
import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.systems.DriveSystem;
import frc.team1091.robot.systems.VisionSystem;

/**
 * Used for final approach on the drop off point
 */
public class DriveUntilClose implements Command {

    private final static float visionScale = 5f;

    private DriverStation.Alliance alliance;
    private RobotComponents components;
    private DriveSystem driveSystem;
    private VisionSystem visionSystem;

    private boolean hasSeen = false;

    public DriveUntilClose(DriverStation.Alliance alliance, RobotComponents components, DriveSystem driveSystem, VisionSystem visionSystem) {
        this.alliance = alliance;
        this.components = components;
        this.driveSystem = driveSystem;
        this.visionSystem = visionSystem;
    }

    @Override
    public Command execute() {

        double center;
        double distance;
        switch (alliance) {
            case Red:
                center = visionSystem.getRedCenter();
                distance = visionSystem.getRedDistance();
                break;
            case Blue:
                center = visionSystem.getBlueCenter();
                distance = visionSystem.getBlueDistance();
                break;
            default: // for invalid
                System.out.println("Invalid Alliance");
                return null;
        }

        double turnpower = -visionScale * center;
        if (turnpower > 0.6)
            turnpower = 0.6f;
        if (turnpower < -0.6)
            turnpower = -0.6f;

        // Drive forwards turning to keep the goal centered

        if (distance > 100 && hasSeen) {
            // we saw it, but now we dont, which means we are super close.
            driveSystem.drive(0, 0);
            // If we are close enough, break and drop box
            // TODO: return drop action
            return null;
        }
        if (distance > 30) {
            // If we see the color goal we are looking for, drive towards it.
            driveSystem.drive(0.7, turnpower);
            return this;
        }

        if (distance > 20) {
            hasSeen = true;
            driveSystem.drive(0.65, turnpower);
            return this;

        }
        if (distance > 5) {
            hasSeen = true;
            driveSystem.drive(0.6, turnpower);
            return this;

        }

        // If we cannot see the goal, break out.  Maybe we are in the wrong spot, and its best not to drop
        driveSystem.drive(0, 0);
        // don't see it, give up.  Probably should not plan to drop
        return null;


    }
}
