package frc.team1091.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;

public class Robot extends IterativeRobot {

    Joystick joystick;
    Victor leftMotor;
    Victor rightMotor;

    public Robot() {
        this(
                new Joystick(1),
                new Victor(0),
                new Victor(1)
        );
    }

    public Robot(Joystick joystick, Victor leftMotor, Victor rightMotor) {
        this.joystick = joystick;
        this.leftMotor = leftMotor;
        this.rightMotor = rightMotor;
    }

//    IRobotDriver iRobotDriver;

    @Override
    public void robotInit() {
    }

    @Override
    public void disabledInit() {
    }

    @Override
    public void autonomousInit() {
    }

    @Override
    public void teleopInit() {
        //.drive(this);
        double x = joystick.getRawAxis(1);
        leftMotor.set(x);
        rightMotor.set(-x);
    }

    @Override
    public void testInit() {
    }


    @Override
    public void disabledPeriodic() {
    }

    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void teleopPeriodic() {
    }

    @Override
    public void testPeriodic() {
    }
}