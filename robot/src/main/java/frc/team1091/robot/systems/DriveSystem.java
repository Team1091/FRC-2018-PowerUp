package frc.team1091.robot.systems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.team1091.robot.RobotComponents;

import static frc.team1091.robot.Xbox.*;

public class DriveSystem {

    private RobotComponents robotComponents;
    private final DifferentialDrive differentialDrive;
    private final double accelerationPerSecond = 4.0;

    public DriveSystem(RobotComponents robotComponents) {
        this.robotComponents = robotComponents;
        differentialDrive = new DifferentialDrive(this.robotComponents.leftMotor, this.robotComponents.rightMotor);
    }

    public DriveSystem(RobotComponents robotComponents, DifferentialDrive drive) {
        this.robotComponents = robotComponents;
        differentialDrive = drive;
    }

    public void init() {
        // There is not to much that actually needs to get initialized in here,
        // but for things like resetting timers it could be useful
    }
    double forwardSpeed = 0;
    double turnSpeed = 0;
    public void drive() {
        boolean boostPushed = robotComponents.xboxController.getRawButton(L3);
        double desiredTurn = robotComponents.xboxController.getRawAxis(leftStickHorizontal);
        double desiredSpeed = robotComponents.xboxController.getRawAxis(leftStickVertical);
        forwardSpeed = getSpeedToSet(desiredSpeed, forwardSpeed) * (boostPushed ? 1.0 : 0.8);
        turnSpeed = getSpeedToSet(desiredTurn, turnSpeed) * (boostPushed ? 1.0 : 0.8);
        differentialDrive.arcadeDrive(-forwardSpeed, turnSpeed);
    }

    public void drive(double forwardSpeed, double turnSpeed) {
        differentialDrive.arcadeDrive(forwardSpeed, turnSpeed);
    }

    private long speedLastRequested = System.currentTimeMillis();
    public double getSpeedToSet (double desiredSpeed, double currentSpeed) {
        long currentTime =  System.currentTimeMillis();
        double dt = (currentTime - speedLastRequested)/1000.0;
        if (desiredSpeed == currentSpeed) {
            return desiredSpeed;
        }
        if (desiredSpeed > currentSpeed){
            return Math.min(currentSpeed + (accelerationPerSecond * dt), desiredSpeed);
        }
        return Math.max(currentSpeed - (accelerationPerSecond * dt), desiredSpeed);
    }
}

