package frc.team1091.robot.systems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.team1091.robot.RobotComponents;

import static frc.team1091.robot.Xbox.*;

public class DriveSystem {

    private RobotComponents robotComponents;
    private final DifferentialDrive differentialDrive;

    public DriveSystem(RobotComponents robotComponents) {
        this.robotComponents = robotComponents;
        differentialDrive = new DifferentialDrive(this.robotComponents.leftMotor, this.robotComponents.rightMotor);
    }

    public DriveSystem(RobotComponents robotComponents, DifferentialDrive drive){
        this.robotComponents = robotComponents;
        differentialDrive = drive;
    }

    public void init() {
        // There is not to much that actually needs to get initialized in here,
        // but for things like resetting timers it could be useful
    }

    public void drive() {
        boolean boostPushed = robotComponents.xboxController.getRawButton(L3);
        double desiredTurn = robotComponents.xboxController.getRawAxis(leftStickHorizontal);
        double desiredSpeed = robotComponents.xboxController.getRawAxis(leftStickVertical);
        double forwardSpeed = desiredSpeed * (boostPushed ? 1.0 : 0.6);
        double turnSpeed = desiredTurn * (boostPushed ? 1.0 : 0.6);
        differentialDrive.arcadeDrive(forwardSpeed, turnSpeed);

    }

    public void drive(double forwardSpeed, double turnSpeed) {
        differentialDrive.arcadeDrive(forwardSpeed, turnSpeed);
    }
}
