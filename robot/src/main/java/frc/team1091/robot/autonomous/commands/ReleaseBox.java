package frc.team1091.robot.autonomous.commands;

import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.systems.ClawPosition;
import frc.team1091.robot.systems.ClawSystem;
import frc.team1091.robot.systems.ElevatorSystem;

public class ReleaseBox implements Command {
    private RobotComponents components;
    private ClawSystem clawSystem;
    private ElevatorSystem elevatorSystem;

    public ReleaseBox(RobotComponents components, ClawSystem clawSystem, ElevatorSystem elevatorSystem) {
        this.components = components;
        this.clawSystem = clawSystem;
        this.elevatorSystem = elevatorSystem;
    }

    @Override
    public Command execute(double dt) {
        if(!elevatorSystem.isAtDropPosition()){
            return this;
        }
        clawSystem.setGatePosition(ClawPosition.OPEN);
        return null;
    }

    @Override
    public String getMessage() {
        return "Releasing The Claw";
    }
}
