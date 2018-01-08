package frc.team1091.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.team1091.robot.autonomous.Command;
import frc.team1091.robot.autonomous.CommandList;
import frc.team1091.robot.autonomous.DriveForwards;
import frc.team1091.robot.autonomous.SpinOutOfControl;
import frc.team1091.robot.drive.AutonomousDriveSystem;
import frc.team1091.robot.drive.ManualDriveSystem;
import frc.team1091.robot.wrapper.EncoderWrapper;

public class Robot extends IterativeRobot {

    Team1091Robot teamRobot;

    @Override
    public void robotInit() {
        teamRobot =  Team1091Robot.getDefaultInstance();
    }

    @Override
    public void autonomousInit() {

        teamRobot.autonomousInit();
    }

    @Override
    public void autonomousPeriodic() {
       teamRobot.autonomousPeriodic();
    }

    @Override
    public void teleopInit() {
        teamRobot.teleopInit();
    }

    @Override
    public void teleopPeriodic() {
        teamRobot.teleopPeriodic();
    }

    @Override
    public void disabledInit() {
        teamRobot.disabledInit();
    }

    @Override
    public void disabledPeriodic() {
        teamRobot.disabledPeriodic();
    }

    @Override
    public void testInit() {
        teamRobot.testInit();
    }

    @Override
    public void testPeriodic() {
        teamRobot.testPeriodic();
    }
}