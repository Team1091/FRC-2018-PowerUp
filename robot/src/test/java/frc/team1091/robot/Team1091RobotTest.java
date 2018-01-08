package frc.team1091.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.team1091.robot.drive.AutonomousDriveSystem;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class Team1091RobotTest {
    @Test
    public void testDriveForwards() {

        Joystick joystick = mock(Joystick.class);
        DifferentialDrive drive = mock(DifferentialDrive.class);

        RobotComponents rc = new RobotComponents(joystick, null, null, null, null);
        RobotControlSystems sy = new RobotControlSystems(drive);

        when(joystick.getRawAxis(1)).thenReturn(0.5);

        Team1091Robot robot = new Team1091Robot(rc, sy, new AutonomousDriveSystem());

        robot.teleopPeriodic();

        verify(joystick).getRawAxis(1);
        verify(drive).arcadeDrive(0.5, 0);
    }
}
