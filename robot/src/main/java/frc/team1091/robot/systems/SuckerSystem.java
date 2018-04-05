package frc.team1091.robot.systems;

import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.Xbox;

public class SuckerSystem {
    private RobotComponents robotComponents;
    public final double xboxTriggerPressedTolerance = .2;

    public SuckerSystem(RobotComponents components) {
        robotComponents = components;
    }

    public void manualControl(double dt) {
        if(robotComponents.xboxController.getRawAxis(Xbox.rt) > xboxTriggerPressedTolerance){
            robotComponents.suckerMotor.set(0.4);
        }else if(robotComponents.xboxController.getRawAxis(Xbox.lt) > xboxTriggerPressedTolerance){
            robotComponents.suckerMotor.set(-1);
        }else{
            robotComponents.suckerMotor.set(0);
        }
    }

}
