package frc.team1091.robot.autonomous.commands;

import edu.wpi.first.wpilibj.SpeedController;
import frc.team1091.robot.Robot;
import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.systems.DriveSystem;
import frc.team1091.robot.wrapper.EncoderWrapper;

/**
 * Set the lifter height
 */

public class Elevator implements Command {
    double ticksToMove;
    RobotComponents robotComponents;
    DriveSystem controlSystems;
    final double ticksToTop = 1000;

    public Elevator(double ticksToMove, RobotComponents robotComponents, DriveSystem controlSystems) {
        this.ticksToMove = ticksToMove;
        this.robotComponents = robotComponents;
        this.controlSystems = controlSystems;
    }

    @Override
    //Lifts the platform
    public Command execute() {
        if (ticksToMove > ticksToTop - robotComponents.elevatorEncoder.get()
                || ticksToMove < -robotComponents.elevatorEncoder.get())
            return null;
        else {
            if (ticksToMove < 0) {
                robotComponents.elevatorMotor.set(-0.5);
            }
            else if (ticksToMove > 0){
                robotComponents.elevatorMotor.set(0.5);
            }
            else {
                return null;
            }
            return this;
        }
    }
}
