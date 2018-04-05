package frc.team1091.robot.autonomous.commands;

import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.systems.ElevatorSystem;
import frc.team1091.robot.systems.SuckerSystem;

public class ReleaseBox implements Command {
    private RobotComponents components;
    private SuckerSystem suckerSystem;
    private ElevatorSystem elevatorSystem;

    private long timePushedMs = 0;

    public ReleaseBox(RobotComponents components, SuckerSystem suckerSystem, ElevatorSystem elevatorSystem) {
        this.components = components;
        this.suckerSystem = suckerSystem;
        this.elevatorSystem = elevatorSystem;
    }

    @Override
    public Command execute(double dt) {
     //   if (!elevatorSystem.isAtDropPosition()) {
       //     return this;
   //     }

        if (timePushedMs == 0) {
            timePushedMs = System.currentTimeMillis();
        }

        if (timePushedMs + 1000 > System.currentTimeMillis()) {
            components.suckerMotor.set(-1);
            return this;
        } else {
            components.suckerMotor.set(0);
            return null;
        }
    }

    @Override
    public String getMessage() {
        return "Spewing Forth";
    }
}
