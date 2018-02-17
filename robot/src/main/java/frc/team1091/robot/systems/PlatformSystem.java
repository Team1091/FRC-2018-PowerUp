package frc.team1091.robot.systems;

import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.Xbox;

public class PlatformSystem {
    private RobotComponents robotComponents;

    private PlatformPosition targetState = PlatformPosition.UP;

    public final double gateTravelSpeed = 0.8;
    public final double xboxTriggerPressedTolerance = .2;
    public final double ticksTolerance = 3;

    public PlatformSystem(RobotComponents components) {
        robotComponents = components;
    }

    public void controlGate(double dt) {
        updatePositionFromControllerInput();

        double power = 0;
        if (targetState.rotation > robotComponents.platformEncoder.getDistance() + ticksTolerance) {
            power = -gateTravelSpeed;
        } else if (targetState.rotation < robotComponents.platformEncoder.getDistance() - ticksTolerance) {
            power = gateTravelSpeed;
        }
        robotComponents.platformMotor.set(power);
    }

    public void setGatePosition(PlatformPosition moveTo) {
        targetState = moveTo;
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
            if (targetState == PlatformPosition.DOWN)
                setGatePosition(PlatformPosition.CENTER);
            if (targetState == PlatformPosition.CENTER)
                setGatePosition(PlatformPosition.UP);
            return;
        }

        if (goDown) {
            if (targetState == PlatformPosition.UP)
                setGatePosition(PlatformPosition.CENTER);
            if (targetState == PlatformPosition.CENTER)
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

