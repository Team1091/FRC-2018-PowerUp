package frc.team1091.robot;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {

    private Team1091Robot teamRobot;

    @Override
    public void robotInit() {
        teamRobot = Team1091Robot.getDefaultInstance();
        teamRobot.robotInit();
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