package frc.team1091.robot.drive;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;
import frc.team1091.robot.Robot;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.wpi.first.wpilibj.networktables.*;

public class DriveTest {

    @Test
    public void testDriveForwards() {

        Joystick fakeJoystick = mock(Joystick.class);
        Victor leftMotor = mock(Victor.class);
        Victor rightMotor = mock(Victor.class);

        when(fakeJoystick.getRawAxis(1)).thenReturn(0.5);


        Robot robot = mock(Robot.class);

                new Robot(
                fakeJoystick,
                leftMotor,
                rightMotor
        );

        // Drive
        robot.teleopPeriodic();

        verify(fakeJoystick).getRawAxis(1);
        verify(leftMotor).set(0.5);
        verify(leftMotor).set(-0.5);

        System.out.print("lol");

    }
}
