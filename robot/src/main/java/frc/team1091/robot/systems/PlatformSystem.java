package frc.team1091.robot.systems;

import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.Xbox;

public class PlatformSystem {
    private RobotComponents robotComponents;

    private PlatformPosition targetState = PlatformPosition.UP;

    public final double gateTravelSpeed = .25;
    public final double xboxTriggerPressedTolerance = .2;
    public final double ticksTolerance = 10;

    public PlatformSystem(RobotComponents components) {
        robotComponents = components;
    }

    public void controlGate() {
        updatePositionFromControllerInput();

        double power = 0;
        if (targetState.rotation > robotComponents.platformEncoder.get() + ticksTolerance) {
            power = -gateTravelSpeed;
        } else if (targetState.rotation < robotComponents.platformEncoder.get() - ticksTolerance) {
            power = gateTravelSpeed;
        }
        robotComponents.platformMotor.set(power);
    }

    public void setGatePosition(PlatformPosition moveTo) {
        targetState = moveTo;
    }

    public void updatePositionFromControllerInput() {
        boolean goToClosed = robotComponents.xboxController.getRawAxis(Xbox.rt) > xboxTriggerPressedTolerance;
        boolean goToPickup = robotComponents.xboxController.getRawAxis(Xbox.lt) > xboxTriggerPressedTolerance;
        boolean goToDrop = goToClosed && goToPickup;

        if (goToDrop) {
            setGatePosition(PlatformPosition.DOWN);
            return;
        }

        if (goToClosed) {
            setGatePosition(PlatformPosition.UP);
            return;
        }

        if (goToPickup) {
            setGatePosition(PlatformPosition.CENTER);
            return;
        }
    }

    public boolean isPlatformAtState(PlatformPosition position) {
        double x = robotComponents.platformEncoder.get();
        double upperBound = position.rotation + ticksTolerance;
        double lowerBound = position.rotation - ticksTolerance;
        return x >= lowerBound && x <= upperBound;
    }

}

