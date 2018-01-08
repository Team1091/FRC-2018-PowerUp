package frc.team1091.robot.autonomous;

import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.RobotControlSystems;

public class SpinOutOfControl implements Command {

    private final RobotComponents robotComponents;
    private final RobotControlSystems controlSystems;

    public SpinOutOfControl(RobotComponents robotComponents, RobotControlSystems controlSystems) {
        this.robotComponents = robotComponents;
        this.controlSystems = controlSystems;
    }

    @Override
    public Command execute() {
        controlSystems.differentialDrive.arcadeDrive(0, 1);
        return this;
    }
}
