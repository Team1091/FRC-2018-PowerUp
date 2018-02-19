package frc.team1091.robot.autonomous.commands;

import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.systems.ClawPosition;
import frc.team1091.robot.systems.ClawSystem;

public class CrushBox implements Command {
    private RobotComponents components;
    private ClawSystem clawSystem;

    public CrushBox(RobotComponents components, ClawSystem clawSystem) {
        this.components = components;
        this.clawSystem = clawSystem;
    }

    @Override
    public Command execute(double dt) {
        clawSystem.setGatePosition(ClawPosition.CRUSH);
        return null;
    }

    @Override
    public String getMessage() {
        return "Crushing Box";
    }
}
