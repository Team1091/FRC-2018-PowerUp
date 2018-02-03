package frc.team1091.robot.systems;

import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.Xbox;

public class ElevatorSystem {
    private RobotComponents robotComponents;

    public final double stepDownStartAt = .25;
    public final int scaleHeight = 1000;
    public final int switchHeight = 300;
    public final int switchRange = 50;

    public ElevatorSystem(RobotComponents robotComponents) {
        this.robotComponents = robotComponents;
    }

    private ElevatorPositions currentPosition;

    public void controlLift() {
        //Determine which way we need to go
        setStateFromController();

        switch (currentPosition) {
            case GroundHeight:
                goToGround();
                break;
            case SwitchHeight:
                goToSwitch();
                break;
            case ScaleHeight:
                goToScale();
                break;
        }
    }

    public boolean isAtBottom() {
        return robotComponents.elevatorLimitSwitch.get();
    }

    public void setElevatorPosition (ElevatorPositions desiredPosition) {
        currentPosition = desiredPosition;
    }

    public void goToGround (){
        boolean isPlatformOnGround = robotComponents.elevatorLimitSwitch.get();
        if (isPlatformOnGround) {
            robotComponents.elevatorEncoder.reset();
            robotComponents.elevatorMotor.set(0);
            return;
        }
        //Slam it to the bottom
        robotComponents.elevatorMotor.set(-1);
    }

    public void goToScale () {
        int currentHeight = robotComponents.elevatorEncoder.get();
        if (currentHeight<scaleHeight) {
            double motorSpeed = determineMotorSpeed(currentHeight, scaleHeight);
            robotComponents.elevatorMotor.set(motorSpeed);
        } else{
            robotComponents.elevatorMotor.set(0);
        }
    }
    public void goToSwitch () {
        int currentHeight = robotComponents.elevatorEncoder.get();
        if (currentHeight>switchHeight+switchRange){
            double motorSpeed = determineMotorSpeed(currentHeight, switchHeight);
            robotComponents.elevatorMotor.set(-1*motorSpeed);
            return;
        }
        if (currentHeight<switchHeight-switchRange){
            double motorSpeed = determineMotorSpeed(currentHeight, switchHeight);
            robotComponents.elevatorMotor.set(motorSpeed);
            return;
        }

        robotComponents.elevatorMotor.set(0);
    }

    public double determineMotorSpeed(int currentPosition, int desiredPosition){
        int distanceRemaining = Math.abs(currentPosition-desiredPosition);

        double percentOfDistanceRenaming = ((double)distanceRemaining / (double)desiredPosition);
        if(percentOfDistanceRenaming > stepDownStartAt){
            return 1;
        }

        int excludedTravel = (int) Math.floor(desiredPosition * ((double)1-stepDownStartAt));
        double motorSpeed = (((double)distanceRemaining) / (double)(desiredPosition - excludedTravel));
        return motorSpeed;
    }

    public void setStateFromController (){
        Boolean goToSwitch = robotComponents.xboxController.getRawButton(Xbox.rb);
        Boolean goToScale = robotComponents.xboxController.getRawButton(Xbox.lb);
        Boolean goToGround = robotComponents.xboxController.getRawButton(Xbox.x);
        if (goToSwitch){
            setElevatorPosition(ElevatorPositions.SwitchHeight);
            return;
        }
        if (goToScale){
            setElevatorPosition(ElevatorPositions.ScaleHeight);
            return;
        }
        if (goToGround){
            setElevatorPosition(ElevatorPositions.GroundHeight);
            return;
        }
    }
}

