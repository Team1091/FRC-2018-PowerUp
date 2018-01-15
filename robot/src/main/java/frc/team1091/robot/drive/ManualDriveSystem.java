package frc.team1091.robot.drive;

import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.RobotControlSystems;

public class ManualDriveSystem {

    private RobotComponents robotComponents;
    private RobotControlSystems controlSystems;

    public ManualDriveSystem(RobotComponents robotComponents, RobotControlSystems controlSystems) {
        this.robotComponents = robotComponents;
        this.controlSystems = controlSystems;
    }

    public void init() {
        // There is not to much that actually needs to get initialized in here,
        // but for things like resetting timers it could be useful
    }

    public void drive() {
        double x = robotComponents.xboxController.getRawAxis(0);
        double y = robotComponents.xboxController.getRawAxis(1);
        controlSystems.differentialDrive.arcadeDrive(y, x);
    }
}
