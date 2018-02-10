package frc.team1091.robot.systems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1091.robot.RobotComponents;

import static frc.team1091.robot.Xbox.*;

public class DriveSystem {

    private RobotComponents robotComponents;
    private final DifferentialDrive differentialDrive;
    private final double accelerationPerSecond = .4;
    private final double turnPerSecond = .5;

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

    private long speedLastRequested = System.nanoTime();

    public void drive() {
        long currentTime =  System.nanoTime();
        double dt = ((double)currentTime - (double)speedLastRequested)/1000000000.0;
        speedLastRequested = currentTime;

        boolean boostPushed = robotComponents.xboxController.getRawButton(L3);
        double desiredTurn = robotComponents.xboxController.getRawAxis(leftStickHorizontal);
        double desiredSpeed = robotComponents.xboxController.getRawAxis(leftStickVertical);
        forwardSpeed = getSpeedToSet(desiredSpeed, forwardSpeed,accelerationPerSecond,dt); //* (boostPushed ? 1.0 : 0.8);
        turnSpeed = getSpeedToSet(desiredTurn, turnSpeed,turnPerSecond, dt); //* (boostPushed ? 1.0 : 0.8);
        differentialDrive.arcadeDrive(-forwardSpeed, turnSpeed);
        SmartDashboard.putNumber("ForwardSpeed", forwardSpeed);
        SmartDashboard.putNumber("TurnSpeed", turnSpeed);
    }

    public void drive(double forwardSpeed, double turnSpeed) {
        differentialDrive.arcadeDrive(forwardSpeed, turnSpeed);
    }


    public double getSpeedToSet (double desiredSpeed, double currentSpeed, double accel, double dt) {

        SmartDashboard.putNumber("Time to Process Frame", dt);
        if (desiredSpeed == currentSpeed) {
            return desiredSpeed;
        }
        if (desiredSpeed > currentSpeed){
            return Math.min(currentSpeed + Math.min(accel * dt, accel), desiredSpeed);
        }
        return Math.max(currentSpeed - Math.min(accel * dt, accel), desiredSpeed);
    }
}

