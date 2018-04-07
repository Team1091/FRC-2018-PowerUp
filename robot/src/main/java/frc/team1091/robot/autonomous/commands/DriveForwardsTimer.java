package frc.team1091.robot.autonomous.commands;

import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.systems.DriveSystem;

import static frc.team1091.robot.Utils.clamp;

public class DriveForwardsTimer implements Command {

    private final double timeInMillis;
    private final RobotComponents robotComponents;
    private final DriveSystem controlSystems;

    private boolean firstRun = true;
    private double startTime = 0;
    public DriveForwardsTimer(double timeInMillis, RobotComponents robotComponents, DriveSystem controlSystems) {
        this.timeInMillis = timeInMillis;
        this.robotComponents = robotComponents;
        this.controlSystems = controlSystems;
    }

    @Override
    public Command execute(double dt) {
        if (firstRun) {
            // Reset driving encoders so we can drive a set distance
            firstRun = false;
            startTime = System.currentTimeMillis();
        }

        if (System.currentTimeMillis() - startTime >= timeInMillis) {
            controlSystems.drive(0, 0, dt);
            return null;  // if I return nothing, I run the next command if im in a command list
        }

        controlSystems.drive(-0.6, -0, dt);
//        controlSystems.drive(-0.3, 0, dt);
        return this;

    }

    @Override
    public String getMessage() {
        return "Driving Forwards " + timeInMillis + " inches";
    }

}
