package frc.team1091.robot.autonomous.commands;

import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.systems.ElevatorPositions;
import frc.team1091.robot.systems.ElevatorSystem;
import frc.team1091.robot.systems.PlatformPosition;
import frc.team1091.robot.systems.PlatformSystem;

public class BarfBox implements Command {
    private RobotComponents components;
    private PlatformSystem platformSystem;
    private ElevatorSystem elevatorSystem;

    public BarfBox(RobotComponents components, PlatformSystem platformSystem, ElevatorSystem elevatorSystem) {
        this.components = components;
        this.platformSystem = platformSystem;
        this.elevatorSystem = elevatorSystem;
    }

    @Override
    public Command execute(double dt) {
        //If elevator at ground height we cannot drop
        if (elevatorSystem.getTargetPosition() == ElevatorPositions.GROUND_HEIGHT) {
            return null;
        }
        //Wait for elevator to be at drop position if still moving
        if (!elevatorSystem.isAtDropPosition()) {
            return this;
        }
        //We are at the top, Angle the platform down to release the box
        platformSystem.setGatePosition(PlatformPosition.DOWN);
        if (!platformSystem.isPlatformAtState(PlatformPosition.DOWN)) {
            return this;
        }
        //TODO: check that the box actually dropped.
        return null;
    }

    @Override
    public String getMessage() {
        return "Barfing Box";
    }
}
