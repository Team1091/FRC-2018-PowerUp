package frc.team1091.robot.systems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.team1091.robot.RobotComponents;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class ManualDriveTest {

    @Test
    public void testDriveForwards() {

        Joystick joystick = mock(Joystick.class);
        DifferentialDrive drive = mock(DifferentialDrive.class);

        RobotComponents rc = new RobotComponents(joystick, null, null, null, null, null, null, null, null, null, null);
        when(joystick.getRawAxis(0)).thenReturn(0.0);
        when(joystick.getRawAxis(1)).thenReturn(0.5);

        DriveSystem driveSystem = new DriveSystem(rc, drive);

        driveSystem.manualDrive(1);

        verify(joystick).getRawAxis(0);
        verify(joystick).getRawAxis(1);
        verify(drive).arcadeDrive(-0.3, 0, false);
    }

    @Test
    public void testDriveBackwards() {

        Joystick joystick = mock(Joystick.class);
        DifferentialDrive drive = mock(DifferentialDrive.class);

        RobotComponents rc = new RobotComponents(joystick, null, null, null, null, null, null, null, null, null, null);

        when(joystick.getRawAxis(0)).thenReturn(0.0);
        when(joystick.getRawAxis(1)).thenReturn(-0.5);

        DriveSystem driveSystem = new DriveSystem(rc, drive);

        driveSystem.manualDrive(1);

        verify(joystick).getRawAxis(0);
        verify(joystick).getRawAxis(1);
        verify(drive).arcadeDrive(0.3, 0, false);
    }

    @Test
    public void testDriveTurnLeft() {

        Joystick joystick = mock(Joystick.class);
        DifferentialDrive drive = mock(DifferentialDrive.class);

        RobotComponents rc = new RobotComponents(joystick, null, null, null, null, null, null, null, null, null, null);

        when(joystick.getRawAxis(0)).thenReturn(-1.0);
        when(joystick.getRawAxis(1)).thenReturn(0.5);

        DriveSystem driveSystem = new DriveSystem(rc, drive);

        driveSystem.manualDrive(1);

        verify(joystick).getRawAxis(0);
        verify(joystick).getRawAxis(1);
        verify(drive).arcadeDrive(-0.3, -0.6, false);
    }

    @Test
    public void testDriveTurnRight() {

        Joystick joystick = mock(Joystick.class);
        DifferentialDrive drive = mock(DifferentialDrive.class);

        RobotComponents rc = new RobotComponents(joystick, null, null, null, null, null, null, null, null, null, null);

        when(joystick.getRawAxis(0)).thenReturn(1.0);
        when(joystick.getRawAxis(1)).thenReturn(0.5);

        DriveSystem driveSystem = new DriveSystem(rc, drive);

        driveSystem.manualDrive(1);

        verify(joystick).getRawAxis(0);
        verify(joystick).getRawAxis(1);
        verify(drive).arcadeDrive(-0.3, 0.6, false);
    }
}
