package frc.team1091.robot.systems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.team1091.robot.RobotComponents;

import static frc.team1091.robot.Xbox.*;

public class DriveSystem {

    private RobotComponents robotComponents;
    private final DifferentialDrive differentialDrive;

    private final double forwardAccelPerSecond = .4;
    private final double turnAccelPerSecond = .5;

    private double forwardSpeed = 0;
    private double turnSpeed = 0;

    public final static double motorDeadzone = 0.3;

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

    /**
     * Reads inputs and sets commands
     *
     * @param dt
     */
    public void manualDrive(double dt) {

        boolean boostPushed = robotComponents.xboxController.getRawButton(L3);
        double desiredTurn = robotComponents.xboxController.getRawAxis(leftStickHorizontal) * (boostPushed ? 1.0 : 0.8);
        double desiredSpeed = robotComponents.xboxController.getRawAxis(leftStickVertical) * (boostPushed ? 1.0 : 0.8);

        drive(desiredSpeed, desiredTurn, dt);
    }

    public void drive(double desiredSpeed, double desiredTurn, double dt) {

        forwardSpeed = getSpeedToSet(desiredSpeed, forwardSpeed, forwardAccelPerSecond, dt);
        turnSpeed = getSpeedToSet(desiredTurn, turnSpeed, turnAccelPerSecond, dt);
        differentialDrive.arcadeDrive(-forwardSpeed, turnSpeed, false);

//        SmartDashboard.putNumber("ForwardSpeed", forwardSpeed);
//        SmartDashboard.putNumber("TurnSpeed", turnSpeed);
//        SmartDashboard.putNumber("Time to Process Frame", dt);
    }

    public double getSpeedToSet(double desiredSpeed, double currentSpeed, double accel, double dt) {

        if (desiredSpeed == currentSpeed) {
            return desiredSpeed;
        }
        if (desiredSpeed > currentSpeed) { // we are accelerating towards our target
            // if we are going towards 0, just get there
            if (currentSpeed < 0)
                return desiredSpeed;

            return Math.min(currentSpeed + Math.min(accel * dt, accel), desiredSpeed);
        }
        // we are going slower
        // if we are going towards 0, just get to dest
        if (currentSpeed > 0)
            return desiredSpeed;

        return Math.max(currentSpeed - Math.min(accel * dt, accel), desiredSpeed);
    }
}

