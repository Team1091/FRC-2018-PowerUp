package frc.team1091.robot.systems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.Xbox;

public class ElevatorSystem {
    private RobotComponents robotComponents;
    //12723 = 90 inches
//141.5 counts per inch
    private final double stepDownStartAt = .5;
    private final int switchRange = 2;

    final double throttledMotorSpeed = 0.5;

    private ElevatorPositions targetPosition = ElevatorPositions.GROUND_HEIGHT;

    private double holdPosition = 0.0; // current desired height
    private final double maxSpeed = 0.25; // max change in desired speed

    public ElevatorSystem(RobotComponents robotComponents) {
        this.robotComponents = robotComponents;
    }

    public void setHoldPosition(double pos){
        holdPosition = pos;
    }

    public void controlLift(double dt) {
        //Determine which way we need to go
        setStateFromController();

        // Update target
        if (targetPosition.inches > holdPosition) {
            holdPosition += maxSpeed * dt;
        } else {
            holdPosition -= maxSpeed * dt;
        }

        //SmartDashboard.putNumber("Elevator Hold Positions", holdPosition);
        //SmartDashboard.putNumber("Target Position", targetPosition.inches);
        double actionMeasured = robotComponents.elevatorEncoder.getDistance();

        if (isAtPosition(ElevatorPositions.GROUND_HEIGHT) && targetPosition == ElevatorPositions.GROUND_HEIGHT) {
            robotComponents.elevatorEncoder.reset();
            robotComponents.elevatorMotor.set(0);
            return;
        }

        double power = determineMotorSpeed(actionMeasured, targetPosition.inches) * throttledMotorSpeed;
//        SmartDashboard.putNumber("Elevator Motor Power", power);
        robotComponents.elevatorMotor.set(power);

//        if (holdPosition > actionMeasured) {// go up
//            robotComponents.elevatorMotor.set(-speed);
//        } else {//go down
//            robotComponents.elevatorMotor.set(speed);
//        }

    }

    public ElevatorPositions getTargetPosition() {
        return targetPosition;
    }

    public void setElevatorPosition(ElevatorPositions desiredPosition) {
        targetPosition = desiredPosition;
    }

    public boolean isAtPosition(ElevatorPositions position) {
        double currentHeight = robotComponents.elevatorEncoder.getDistance();
        switch (position) {
            case GROUND_HEIGHT:
                return currentHeight <= 0;
            case SWITCH_HEIGHT:
            case SCALE_HEIGHT:
                double upperSwitchRange = position.inches + switchRange;
                double lowerSwitchRange = position.inches - switchRange;
                return !(currentHeight <= upperSwitchRange && currentHeight >= lowerSwitchRange);
        }
        return false;
    }

    public boolean isAtDropPosition() {
        return isAtPosition(targetPosition) && targetPosition != ElevatorPositions.GROUND_HEIGHT;
    }

    public double determineMotorSpeed(double currentPosition, double desiredPosition) {
        double d = desiredPosition - currentPosition;

        if (d < -switchRange) {
            return -1;
        }
        if (d > switchRange)
            return 1;

        return d / switchRange;
    }

    private void setStateFromController() {
        Boolean goToSwitch = robotComponents.xboxController.getRawButton(Xbox.rb);
        Boolean goToScale = robotComponents.xboxController.getRawButton(Xbox.lb);
        Boolean goToGround = robotComponents.xboxController.getRawButton(Xbox.x);
        if (goToSwitch) {
            setElevatorPosition(ElevatorPositions.SWITCH_HEIGHT);
            return;
        }
        if (goToScale) {
            setElevatorPosition(ElevatorPositions.SCALE_HEIGHT);
            return;
        }
        if (goToGround) {
            setElevatorPosition(ElevatorPositions.GROUND_HEIGHT);
            return;
        }
    }
}

