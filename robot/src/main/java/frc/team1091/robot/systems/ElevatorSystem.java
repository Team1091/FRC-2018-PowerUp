package frc.team1091.robot.systems;

import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.Xbox;
import org.opencv.core.Mat;

public class ElevatorSystem {
    private RobotComponents robotComponents;
    //12723 = 90 inches
    //141.5 counts per inch

    private final double rampWidth = 2.0;

    final double throttledMotorSpeed = 1.0;

    private ElevatorPositions targetPosition = ElevatorPositions.GROUND_HEIGHT;

    private double holdPosition = 0.0; // current desired height
    private final double maxSpeed = 10.0; // max change in desired speed

    public ElevatorSystem(RobotComponents robotComponents) {
        this.robotComponents = robotComponents;
    }

    public void setHoldPosition(double pos) {
        holdPosition = pos;
    }

    public void controlLift(double dt) {
        //Determine which way we need to go
        setStateFromController();

        // Update target
        if (targetPosition.inches > holdPosition) {
            holdPosition += maxSpeed * dt;
        }
        if (targetPosition.inches < holdPosition) {
            holdPosition -= maxSpeed * dt;
        }
        if (Math.abs(targetPosition.inches - holdPosition) < 0.5) {
            holdPosition = targetPosition.inches;
        }
        if (holdPosition < 0) {
            holdPosition = 0;
        }

        double actualMeasured = robotComponents.elevatorEncoder.getDistance();
        if (isAtPosition(ElevatorPositions.GROUND_HEIGHT) && targetPosition == ElevatorPositions.GROUND_HEIGHT) {
            robotComponents.elevatorEncoder.reset();
            robotComponents.elevatorMotor.set(0);
            holdPosition = 0;
            return;
        }

        double power = determineMotorSpeed(actualMeasured, holdPosition);
        power = power > 0 ? Math.min(power, throttledMotorSpeed) : Math.max(power, throttledMotorSpeed);

        robotComponents.elevatorMotor.set(-power);
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
                return robotComponents.elevatorLimitSwitch.get() ||
                       currentHeight <= 0;
            case SWITCH_HEIGHT:
            case SCALE_HEIGHT:
                double upperSwitchRange = position.inches + rampWidth;
                double lowerSwitchRange = position.inches - rampWidth;
                return !(currentHeight <= upperSwitchRange && currentHeight >= lowerSwitchRange);
        }
        return false;
    }

    public boolean isAtDropPosition() {
        return isAtPosition(targetPosition) && targetPosition != ElevatorPositions.GROUND_HEIGHT;
    }

    public double determineMotorSpeed(double currentPosition, double desiredPosition) {
        double d = desiredPosition - currentPosition;

        if (d < -rampWidth) {
            return -1.0 ;
        }
        if (d > rampWidth) {
            return 1.0;
        }
        double power = d / rampWidth;
        return power;
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

