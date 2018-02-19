package frc.team1091.robot.systems;

import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.Xbox;

import static frc.team1091.robot.Utils.determineMotorSpeed;

public class PlatformSystem {
    private RobotComponents robotComponents;

    private PlatformPosition targetState = PlatformPosition.UP;

    public final double gateTravelSpeed = 1.0;
    public final double xboxTriggerPressedTolerance = .2;
    public final double ticksTolerance = 10;

    public PlatformSystem(RobotComponents components) {
        robotComponents = components;
    }

    public void controlGate(double dt) {
        updatePositionFromControllerInput();

        double power = determineMotorSpeed(robotComponents.platformEncoder.getDistance(), targetState.rotation, ticksTolerance);
        robotComponents.platformMotor.set(power);
    }

    public void setGatePosition(PlatformPosition moveTo) {
        targetState = moveTo;
    }

    public PlatformPosition getGatePosition() {
        return targetState;
    }

    private boolean lastPressed = false;

    public void updatePositionFromControllerInput() {
        boolean goUp = robotComponents.xboxController.getRawAxis(Xbox.rt) > xboxTriggerPressedTolerance;
        boolean goDown = robotComponents.xboxController.getRawAxis(Xbox.lt) > xboxTriggerPressedTolerance;

        if (goUp && goDown)
            return; // invalid key combo

        if (lastPressed) {
            if (!goUp && !goDown) {
                lastPressed = false; // we are no longer pressing
            }
            return;
        }

        if (goUp) {
            lastPressed = true;
            if (targetState == PlatformPosition.DOWN)
                setGatePosition(PlatformPosition.CENTER);
            else if (targetState == PlatformPosition.CENTER)
                setGatePosition(PlatformPosition.UP);
            return;
        }

        if (goDown) {
            lastPressed = true;
            if (targetState == PlatformPosition.UP)
                setGatePosition(PlatformPosition.CENTER);
            else if (targetState == PlatformPosition.CENTER)
                setGatePosition(PlatformPosition.DOWN);
            return;
        }
    }

    public boolean isPlatformAtState(PlatformPosition position) {
        double x = robotComponents.platformEncoder.getDistance();
        double upperBound = position.rotation + ticksTolerance;
        double lowerBound = position.rotation - ticksTolerance;
        return x >= lowerBound && x <= upperBound;
    }

}

