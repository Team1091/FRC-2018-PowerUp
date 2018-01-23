package frc.team1091.robot.drive;

import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.RobotControlSystems;

import static frc.team1091.robot.Xbox.*;

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
        boolean boostPushed = robotComponents.xboxController.getRawButton(l3);
        double desiredTurn = robotComponents.xboxController.getRawAxis(leftStickHorizontal);
        double desiredSpeed = robotComponents.xboxController.getRawAxis(leftStickVertical);
        double forwardSpeed = desiredSpeed * (boostPushed ? 1.0 : 0.6);
        double turnSpeed = desiredTurn * (boostPushed ? 1.0 : 0.6);
        controlSystems.differentialDrive.arcadeDrive(forwardSpeed, turnSpeed);

    }
}
