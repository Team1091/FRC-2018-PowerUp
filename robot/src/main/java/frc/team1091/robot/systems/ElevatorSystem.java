package frc.team1091.robot.systems;

import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.Xbox;

public class ElevatorSystem {
    private RobotComponents robotComponents;
    //12723 = 90 inches
//141.5 counts per inch
    private final double stepDownStartAt = .5;
    private final int switchRange = 2;

    final double throttledMotorSpeed = 0.7;

    private ElevatorPositions targetPosition = ElevatorPositions.GROUND_HEIGHT;

    private double holdPosition = 0; // current desired height
    private final double maxSpeed = 1; // max change in desired speed

    public ElevatorSystem(RobotComponents robotComponents) {
        this.robotComponents = robotComponents;
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

        double actionMeasured = robotComponents.elevatorEncoder.getDistance();

        double speed = determineMotorSpeed(actionMeasured, holdPosition);

        if (isAtPosition(ElevatorPositions.GROUND_HEIGHT)) {
            robotComponents.elevatorEncoder.reset();
            robotComponents.elevatorMotor.set(0);
            return;
        }

        if (holdPosition > actionMeasured) {// go up
            robotComponents.elevatorMotor.set(speed);
        } else {//go down
            robotComponents.elevatorMotor.set(-speed);
        }

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
        double distanceRemaining = Math.abs(currentPosition - desiredPosition);

        double percentOfDistanceRenaming = (distanceRemaining / desiredPosition);
        if (percentOfDistanceRenaming > stepDownStartAt) {
            return Math.min(1, throttledMotorSpeed);
        }

        int excludedTravel = (int) Math.floor(desiredPosition * ((double) 1 - stepDownStartAt));
        double motorSpeed = distanceRemaining / (desiredPosition - excludedTravel);
        return Math.min(motorSpeed, throttledMotorSpeed);
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

