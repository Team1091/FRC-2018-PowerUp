package frc.team1091.robot.systems;

import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.Utils;
import frc.team1091.robot.Xbox;

import static frc.team1091.robot.Utils.determineMotorSpeed;

public class ElevatorSystem {
    private RobotComponents robotComponents;
    //12723 = 90 inches
    //141.5 counts per inch

    private final double rampWidth = 2.0;

    final double throttledMotorSpeed = 1.0;

    private ElevatorPosition targetPosition = ElevatorPosition.GROUND_HEIGHT;

    private double holdPosition = 0.0; // current desired height
    private final double maxSpeed = 10.0; // max change in desired speed

    public ElevatorSystem(RobotComponents robotComponents) {
        this.robotComponents = robotComponents;
    }

    public void setHoldPosition(double pos) {
        holdPosition = pos;
    }

    public double getHoldPosition() {
        return holdPosition;
    }

    public void controlLift(double dt) {
        if (!robotComponents.xboxController.getRawButton(Xbox.b)) {
            manualControl();
            return;
        }

        //Determine which way we need to go
        setStateFromController();

        // Update target
        if (targetPosition.inches > holdPosition) {
            holdPosition += maxSpeed * dt;
        } else if (targetPosition.inches < holdPosition) {
            holdPosition -= maxSpeed * dt;
        }
        if (Math.abs(targetPosition.inches - holdPosition) < 0.5) {
            holdPosition = targetPosition.inches;
        }
        if (holdPosition < 0) {
            holdPosition = 0;
        }

        double actualMeasured = robotComponents.elevatorEncoder.getDistance();
        if (isAtPosition(ElevatorPosition.GROUND_HEIGHT) && targetPosition == ElevatorPosition.GROUND_HEIGHT) {
            robotComponents.elevatorEncoder.reset();
            robotComponents.elevatorMotor.set(0);
            holdPosition = 0;
            return;
        }

        double power = determineMotorSpeed(actualMeasured, holdPosition, rampWidth);
        power = Utils.clamp(power, -throttledMotorSpeed, throttledMotorSpeed);

        robotComponents.elevatorMotor.set(-power);
    }

    public ElevatorPosition getTargetPosition() {
        return targetPosition;
    }

    public void setElevatorPosition(ElevatorPosition desiredPosition) {
        targetPosition = desiredPosition;
    }

    public boolean isAtPosition(ElevatorPosition position) {
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
        return isAtPosition(targetPosition) && targetPosition != ElevatorPosition.GROUND_HEIGHT;
    }

    private void setStateFromController() {
        Boolean goToSwitch = robotComponents.xboxController.getRawButton(Xbox.rb);
        Boolean goToScale = robotComponents.xboxController.getRawButton(Xbox.lb);
        Boolean goToGround = robotComponents.xboxController.getRawButton(Xbox.x);
        if (goToSwitch) {
            setElevatorPosition(ElevatorPosition.SWITCH_HEIGHT);
            return;
        }
        if (goToScale) {
            setElevatorPosition(ElevatorPosition.SCALE_HEIGHT);
            return;
        }
        if (goToGround) {
            setElevatorPosition(ElevatorPosition.GROUND_HEIGHT);
            return;
        }
    }

    private void manualControl() {
        Boolean goUp = robotComponents.xboxController.getRawButton(Xbox.rb);
        Boolean goDown = robotComponents.xboxController.getRawButton(Xbox.lb);
        Boolean override = robotComponents.xboxController.getRawButton(Xbox.r3);
        if (goUp) {
            robotComponents.elevatorMotor.set(-1);
            return;
        }
        if ((goDown && (!robotComponents.elevatorLimitSwitch.get())) || override) {

            robotComponents.elevatorMotor.set(0.5);
            return;
        }
        robotComponents.elevatorMotor.set(0);
    }
}

