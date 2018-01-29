package frc.team1091.robot.systems;

import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.Xbox;

public class LiftSystem {
    private RobotComponents robotComponents;

    public LiftSystem(RobotComponents robotComponents) {
        this.robotComponents = robotComponents;
    }

    public void controlLift() {
        //Determine which way we need to go
        Boolean upButtonPressed = robotComponents.xboxController.getRawButton(Xbox.rb);
        Boolean downButtonPressed = robotComponents.xboxController.getRawButton(Xbox.lb);
        //If both buttons are in same state, do nothing
        if (upButtonPressed == downButtonPressed) {
            robotComponents.gateMotor.set(0);
            return;
        }
        //Move motor right
        if (upButtonPressed) {
            robotComponents.gateMotor.set(0.25);
        }
        //else stop lifting
    /*    else {
            robotComponents.lifterMotor.set(0);
        }*/
        //Move motor left
        if (downButtonPressed) {
            robotComponents.gateMotor.set(-0.25);
        }
        //else stop lifting
  /*      else {
            robotComponents.lifterMotor.set(0);
        }*/
    }
}
