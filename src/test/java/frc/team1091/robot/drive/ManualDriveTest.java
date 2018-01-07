package frc.team1091.robot.drive;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class ManualDriveTest {

    @Test
    public void testDriveForwards() {

        Joystick joystick = mock(Joystick.class);
        DifferentialDrive drive = mock(DifferentialDrive.class);

        when(joystick.getRawAxis(1)).thenReturn(0.5);

        ManualDriveSystem manualDriveSystem = new ManualDriveSystem(
                joystick,
                drive);

        manualDriveSystem.drive();

        verify(joystick).getRawAxis(1);
        verify(drive).arcadeDrive(0.5, 0);

    }
}
