package frc.team1091.robot.systems;

import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.Xbox;

public class ElevatorSystem {
    private RobotComponents robotComponents;

    public ElevatorSystem(RobotComponents robotComponents) {
        this.robotComponents = robotComponents;
    }

    public void controlLift() {
        //Determine which way we need to go
        Boolean upButtonPressed = robotComponents.xboxController.getRawButton(Xbox.rb);
        Boolean downButtonPressed = robotComponents.xboxController.getRawButton(Xbox.lb);
        //If both buttons are in same state, do nothing
        if (upButtonPressed == downButtonPressed) {
            robotComponents.platformMotor.set(0);
            return;
        }
        //Move motor right
        if (upButtonPressed) {
            robotComponents.platformMotor.set(0.25);
        }
        //Move motor left
        if (downButtonPressed) {
            robotComponents.platformMotor.set(-0.25);
        }
    }
}
