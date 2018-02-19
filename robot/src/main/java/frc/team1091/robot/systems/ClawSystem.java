package frc.team1091.robot.systems;

import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.Xbox;

public class ClawSystem {
    private RobotComponents robotComponents;

    private ClawPosition targetState = ClawPosition.OPEN;
    private long timePushedMs = 0;

    public final double xboxTriggerPressedTolerance = .2;

    public ClawSystem(RobotComponents components) {
        robotComponents = components;
    }

    public void controlGate(double dt) {
        updatePositionFromControllerInput();

        switch (targetState) {
            case OPEN:
                boolean opening = System.currentTimeMillis() - timePushedMs < 1000;
                robotComponents.clawMotor.set(opening ? -1 : 0);

                break;
            case CRUSH:
                robotComponents.clawMotor.set(1);
                break;
        }
    }

    public void setGatePosition(ClawPosition moveTo) {
        targetState = moveTo;
    }

    public ClawPosition getGatePosition() {
        return targetState;
    }

    public void updatePositionFromControllerInput() {

        boolean open = robotComponents.xboxController.getRawAxis(Xbox.rt) > xboxTriggerPressedTolerance;
        boolean close = robotComponents.xboxController.getRawAxis(Xbox.lt) > xboxTriggerPressedTolerance;

        if (open && close)
            return; // invalid key combo

        if (open) {
            setGatePosition(ClawPosition.OPEN);
            return;
        }

        if (close) {
            setGatePosition(ClawPosition.CRUSH);
            return;
        }
    }

}
