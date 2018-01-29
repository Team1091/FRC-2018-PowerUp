package frc.team1091.robot.systems;

import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.Xbox;

public class GateSystem {
    private RobotComponents robotComponents;
    private GateSystemState currentState;

    public GateSystem(RobotComponents components) {
        robotComponents = components;
    }

    public void controlGate() {
        boolean shouldMoveUp = robotComponents.xboxController.getRawAxis(Xbox.rt) > 0;
        boolean shouldMoveDown = robotComponents.xboxController.getRawAxis(Xbox.lt) > 0;
        switch (currentState) {
            case Idle:
                if (shouldMoveUp) {
                    // start moving gate up
                    moveUp();
                }
                if (shouldMoveDown) {
                    // start moving gate down
                }
                break;
            case MovingDown:
                break;
            case MovingUp:
                break;
        }
    }

    public void moveUp() {
        currentState = GateSystemState.MovingUp;
        robotComponents.gateMotor.set(1);
    }
}

enum GateSystemState {
    Idle,
    MovingDown,
    MovingUp
}
