package frc.team1091.robot.systems;

import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.Xbox;

public class GateSystem {
    private RobotComponents robotComponents;
    private GateSystemState nextState;

    private final double gateTravelSpeed = .25;
    private final double xboxTriggerPressedTolerance = .2;

    public GateSystem(RobotComponents components) {
        robotComponents = components;
    }

    public void controlGate() {
        UpdatePositionFromConrollerInput();
        switch (nextState) {
            case GateUp:
                MoveToGateUp();
                break;
            case DropPosition:
                MoveToDrop();
                break;
            case PickupPosition:
                MoveGateToPickup();
                break;
        }
    }

    public void SetGatePosition(GateSystemState moveTo){
        nextState = moveTo;
    }

    public void UpdatePositionFromConrollerInput(){
        boolean goToClosed = robotComponents.xboxController.getRawAxis(Xbox.rt) > xboxTriggerPressedTolerance;
        boolean goToPickup = robotComponents.xboxController.getRawAxis(Xbox.lt) > xboxTriggerPressedTolerance;
        boolean goToDrop = goToClosed && goToPickup;

        if(goToDrop) {
            SetGatePosition(GateSystemState.DropPosition);
            return;
        }

        if(goToClosed) {
            SetGatePosition(GateSystemState.GateUp);
            return;
        }

        if(goToPickup) {
            SetGatePosition(GateSystemState.PickupPosition);
        }
    }

    public void MoveToGateUp(){
        if(robotComponents.gateClosePositionDigitalInput.get()) {
            robotComponents.gateMotor.set(0);
            return;
        }

        robotComponents.gateMotor.set(gateTravelSpeed);
    }

    public void MoveGateToPickup(){
        if(robotComponents.pickUpPositionDigitalInput.get()) {
            robotComponents.gateMotor.set(0);
            return;
        }

        if(robotComponents.gateClosePositionDigitalInput.get()){
            robotComponents.gateMotor.set(-1 * gateTravelSpeed);
        }

        robotComponents.gateMotor.set(gateTravelSpeed);
    }

    public void MoveToDrop(){
        if(robotComponents.dropBoxPositionDigitalInput.get()) {
            robotComponents.gateMotor.set(0);
            return;
        }

        robotComponents.gateMotor.set(-1 * gateTravelSpeed);
    }
}

enum GateSystemState {
    GateUp,
    DropPosition,
    PickupPosition
}
