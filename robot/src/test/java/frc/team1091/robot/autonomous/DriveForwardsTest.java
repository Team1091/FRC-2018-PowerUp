package frc.team1091.robot.autonomous;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.RobotControlSystems;
import frc.team1091.robot.drive.AutonomousDriveSystem;
import frc.team1091.robot.wrapper.EncoderWrapper;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class DriveForwardsTest {
    private int testNum;

    @Test
    public void testThatWeDriveForwards() {


        DifferentialDrive drive = mock(DifferentialDrive.class);
        EncoderWrapper encoder = mock(EncoderWrapper.class);

        RobotComponents rc = new RobotComponents(null, null, null, encoder, null);
        RobotControlSystems sy = new RobotControlSystems(drive);

        when(encoder.get()).thenReturn(10);

        AutonomousDriveSystem autonomousDriveSystem = new AutonomousDriveSystem();
        testNum = 0;
        autonomousDriveSystem.init(
                new CommandList(
                        new DriveForwards(100, rc, sy),
                        () -> {
                            testNum++;
                            System.out.println("We can use functional programming here too.");
                            return null;
                        },
                        new SpinOutOfControl(rc, sy)
                )
        );

        autonomousDriveSystem.drive();

        verify(encoder).get();
        verify(drive).arcadeDrive(1, 0);


        // Next we want to test that when the encoder is past the threshold, that it stops and goes to the next command

        when(encoder.get()).thenReturn(101);

        autonomousDriveSystem.drive();

        // We should have stopped.  We don't want to keep driving
        verify(drive).arcadeDrive(0, 0);

        assert testNum == 0;
        autonomousDriveSystem.drive();
        assert testNum == 1;

        // Lets try spinning, that's a nice trick
        autonomousDriveSystem.drive();

        verify(drive).arcadeDrive(0, 1);
    }

}
