package frc.team1091.robot.systems;

import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.Xbox;

public class PlatformSystem {
    private RobotComponents robotComponents;
    private PlatformSystemState nextState;

    public final double gateTravelSpeed = .25;
    public final double xboxTriggerPressedTolerance = .2;

    public PlatformSystem(RobotComponents components) {
        robotComponents = components;
    }

    public void controlGate() {
        UpdatePositionFromConrollerInput();
        switch (nextState) {
            case GateUp:
                break;
            case DropPosition:
                break;
            case PickupPosition:
                break;
        }
    }

    public void SetGatePosition(PlatformSystemState moveTo) {
        nextState = moveTo;
    }

    public void UpdatePositionFromConrollerInput() {
        boolean goToClosed = robotComponents.xboxController.getRawAxis(Xbox.rt) > xboxTriggerPressedTolerance;
        boolean goToPickup = robotComponents.xboxController.getRawAxis(Xbox.lt) > xboxTriggerPressedTolerance;
        boolean goToDrop = goToClosed && goToPickup;

        if (goToDrop) {
            SetGatePosition(PlatformSystemState.DropPosition);
            return;
        }

        if (goToClosed) {
            SetGatePosition(PlatformSystemState.GateUp);
            return;
        }

        if (goToPickup) {
            SetGatePosition(PlatformSystemState.PickupPosition);
        }
    }

}

enum PlatformSystemState {
    GateUp,
    DropPosition,
    PickupPosition
}
