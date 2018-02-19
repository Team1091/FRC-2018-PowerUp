package frc.team1091.robot.autonomous.commands;

import frc.team1091.robot.systems.ElevatorPosition;
import frc.team1091.robot.systems.ElevatorSystem;

/**
 * Set the lifter height
 */

public class SetElevatorPosition implements Command {
    private final ElevatorSystem elevatorSystem;
    private ElevatorPosition elevatorPositions;

    public SetElevatorPosition(ElevatorPosition elevatorPositions, ElevatorSystem elevatorSystem) {
        this.elevatorPositions = elevatorPositions;
        this.elevatorSystem = elevatorSystem;
    }

    @Override
    public Command execute(double dt) {
        elevatorSystem.setElevatorPosition(elevatorPositions);
        return null;
    }

    @Override
    public String getMessage() {
        return "Setting the target elevator position";
    }
}
