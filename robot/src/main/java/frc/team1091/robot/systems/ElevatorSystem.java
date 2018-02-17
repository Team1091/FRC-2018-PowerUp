package frc.team1091.robot.systems;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.Xbox;

public class ElevatorSystem {
    private RobotComponents robotComponents;
//12723 = 90 inches
//141.5 counts per inch
    private final double stepDownStartAt = .5;
    private final int scaleHeight = 720;
    private final int switchHeight = 360;
    private final int switchRange = 12;

    private final double decentSpeed = .3;
    final double throttledMotorSpeed = 0.7;

    private double lastControlTime;
    private double currentControlTime;
    private ElevatorPositions targetPosition = ElevatorPositions.GROUND_HEIGHT;

    public ElevatorSystem(RobotComponents robotComponents) {
        this.robotComponents = robotComponents;
    }

    public void controlLift(double dt) {
        currentControlTime = dt;
        if(lastControlTime != 0) {
            //Determine which way we need to go
            setStateFromController();
            switch (targetPosition) {
                case GROUND_HEIGHT:
                    goToGround();
                    break;
                case SWITCH_HEIGHT:
                    goToSwitch();
                    break;
                case SCALE_HEIGHT:
                    goToScale();
                    break;
            }
        }
        lastControlTime = currentControlTime;
    }

    public ElevatorPositions getTargetPosition() {
        return targetPosition;
    }

    public void setElevatorPosition(ElevatorPositions desiredPosition) {
        targetPosition = desiredPosition;
    }

    private void goToGround() {
        if (isAtPosition(ElevatorPositions.GROUND_HEIGHT)) {
            robotComponents.elevatorEncoder.reset();
            robotComponents.elevatorMotor.set(0);
            return;
        }
        //Slam it to the bottom
        robotComponents.elevatorMotor.set(decentSpeed);
    }

    public boolean isAtPosition(ElevatorPositions position) {
        double currentHeight = robotComponents.elevatorEncoder.getDistance();
        switch (position){
            case GROUND_HEIGHT:
                return robotComponents.elevatorLimitSwitch.get();
            case SWITCH_HEIGHT:
                double upperSwitchRange = switchHeight + switchRange;
                double lowerSwitchRange = switchHeight - switchRange;
                return !(currentHeight <= upperSwitchRange && currentHeight >= lowerSwitchRange);
            case SCALE_HEIGHT:
                double upperScaleRange = scaleHeight + switchRange;
                double lowerScaleRange = scaleHeight - switchRange;
                return !(currentHeight <= upperScaleRange && currentHeight >= lowerScaleRange);
        }
        return false;
    }

    public boolean isAtDropPosition() {
        if (targetPosition == ElevatorPositions.SCALE_HEIGHT && isAtPosition(ElevatorPositions.SCALE_HEIGHT)) {
            return true;
        }
        if (targetPosition == ElevatorPositions.SWITCH_HEIGHT && isAtPosition(ElevatorPositions.SWITCH_HEIGHT)) {
            return true;
        }
        return false;
    }

    public double determineMotorSpeed(double currentPosition, double desiredPosition) {
        double distanceRemaining = Math.abs(currentPosition - desiredPosition);

        double percentOfDistanceRenaming = (distanceRemaining / desiredPosition);
        if (percentOfDistanceRenaming > stepDownStartAt) {
            return Math.min(1,throttledMotorSpeed);
        }

        int excludedTravel = (int) Math.floor(desiredPosition * ((double) 1 - stepDownStartAt));
        double motorSpeed =  distanceRemaining / (desiredPosition - excludedTravel);
        return Math.min(motorSpeed,throttledMotorSpeed);
    }

    private void goToScale() {
        double currentHeight = robotComponents.elevatorEncoder.getDistance();
        if (currentHeight < scaleHeight) {
            double motorSpeed = determineMotorSpeed(currentHeight, scaleHeight);
            robotComponents.elevatorMotor.set(motorSpeed);
            return;
        }
        robotComponents.elevatorMotor.set(0);
    }

    private void goToSwitch() {
        double currentHeight = robotComponents.elevatorEncoder.getDistance();
        if (currentHeight > switchHeight + switchRange) {
            robotComponents.elevatorMotor.set(decentSpeed);
            return;
        }
        if (currentHeight < switchHeight - switchRange) {
            double motorSpeed = determineMotorSpeed(currentHeight, switchHeight);
            robotComponents.elevatorMotor.set(motorSpeed);
            return;
        }

        robotComponents.elevatorMotor.set(0);
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

