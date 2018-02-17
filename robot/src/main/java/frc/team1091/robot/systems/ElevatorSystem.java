package frc.team1091.robot.systems;

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
    final double throttledMotorSpeed = 0.7;
    private ElevatorPositions targetPosition = ElevatorPositions.GROUND_HEIGHT;

    public ElevatorSystem(RobotComponents robotComponents) {
        this.robotComponents = robotComponents;
    }

    public void controlLift(double dt) {
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

    public ElevatorPositions getTargetPosition() {
        return targetPosition;
    }

    public boolean isAtBottom() {
        return robotComponents.elevatorLimitSwitch.get();
    }

    public void setElevatorPosition(ElevatorPositions desiredPosition) {
        targetPosition = desiredPosition;
    }

    private void goToGround() {
        if (isAtBottom()) {
            robotComponents.elevatorEncoder.reset();
            robotComponents.elevatorMotor.set(0);
            return;
        }
        //Slam it to the bottom
        robotComponents.elevatorMotor.set(-1);
    }

    private boolean isAtScale() {
        double currentHeight = robotComponents.elevatorEncoder.getDistance();
        return currentHeight >= scaleHeight;
    }

    private boolean isAtSwitch() {
        double currentHeight = robotComponents.elevatorEncoder.getDistance();
        double upperSwitchRange = switchHeight + switchRange;
        double lowerSwitchRange = switchHeight - switchRange;
        return !(currentHeight <= upperSwitchRange && currentHeight >= lowerSwitchRange);
    }

    public boolean isAtDropPosition() {
        if (targetPosition == ElevatorPositions.SCALE_HEIGHT && isAtScale()) {
            return true;
        }
        if (targetPosition == ElevatorPositions.SWITCH_HEIGHT && isAtSwitch()) {
            return true;
        }
        return false;
    }

    public void goToScale() {
        double currentHeight = robotComponents.elevatorEncoder.getDistance();
        if (currentHeight < scaleHeight) {
            double motorSpeed = determineMotorSpeed(currentHeight, scaleHeight);
            robotComponents.elevatorMotor.set(motorSpeed);
        } else {
            robotComponents.elevatorMotor.set(0);
        }
    }

    public void goToSwitch() {
        double currentHeight = robotComponents.elevatorEncoder.getDistance();
        if (currentHeight > switchHeight + switchRange) {
            double motorSpeed = determineMotorSpeed(currentHeight, switchHeight);
            robotComponents.elevatorMotor.set(-1 * motorSpeed);
            return;
        }
        if (currentHeight < switchHeight - switchRange) {
            double motorSpeed = determineMotorSpeed(currentHeight, switchHeight);
            robotComponents.elevatorMotor.set(motorSpeed);
            return;
        }

        robotComponents.elevatorMotor.set(0);
    }

    public double determineMotorSpeed(double currentPosition, double desiredPosition) {
        double distanceRemaining = Math.abs(currentPosition - desiredPosition);

        double percentOfDistanceRenaming = (distanceRemaining / desiredPosition);
        if (percentOfDistanceRenaming > stepDownStartAt) {
            return Math.min(1,throttledMotorSpeed);
        }

        int excludedTravel = (int) Math.floor(desiredPosition * ((double) 1 - stepDownStartAt));
        double motorSpeed =  distanceRemaining / (desiredPosition - excludedTravel);
        return  Math.min(motorSpeed,throttledMotorSpeed);
    }

    public void setStateFromController() {
        SmartDashboard.putString("Getting There", "HERE");
        Boolean goToSwitch = robotComponents.xboxController.getRawButton(Xbox.rb);
        Boolean goToScale = robotComponents.xboxController.getRawButton(Xbox.lb);
        Boolean goToGround = robotComponents.xboxController.getRawButton(Xbox.x);
        SmartDashboard.putBoolean("Pushing RB", goToSwitch);
        SmartDashboard.putBoolean("Pushing LB", goToScale);
        SmartDashboard.putBoolean("Pushing X", goToGround);
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

