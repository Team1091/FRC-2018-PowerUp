package frc.team1091.robot.autonomous;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.autonomous.commands.CommandList;
import frc.team1091.robot.autonomous.commands.DriveForwards;
import frc.team1091.robot.autonomous.commands.SpinOutOfControl;
import frc.team1091.robot.systems.AutonomousSystem;
import frc.team1091.robot.systems.DriveSystem;
import frc.team1091.robot.wrapper.EncoderWrapper;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class DriveForwardsTest {
    private int testNum;

    @Test
    public void testThatWeDriveForwards() {
        DriveSystem drive = mock(DriveSystem.class);
        EncoderWrapper encoder = mock(EncoderWrapper.class);

        RobotComponents rc = new RobotComponents(null, null, null, null, null, null, null, null, encoder, null, null, null, null, null, null, null);

        when(encoder.get()).thenReturn(10);

        AutonomousSystem autonomousSystem = new AutonomousSystem();
        testNum = 0;
        autonomousSystem.init(
                new CommandList(
                        new DriveForwards(100, rc, drive),
                        () -> {
                            testNum++;
                            System.out.println("We can use functional programming here too.");
                            return null;
                        },
                        new SpinOutOfControl(rc, drive)
                )
        );

        autonomousSystem.drive();

        verify(encoder).get();
        verify(drive).drive(1, 0);


        // Next we want to test that when the encoder is past the threshold, that it stops and goes to the next command

        when(encoder.get()).thenReturn(101);

        autonomousSystem.drive();

        // We should have stopped.  We don't want to keep driving
        verify(drive).drive(0, 0);

        assert testNum == 0;
        autonomousSystem.drive();
        assert testNum == 1;

        // Lets try spinning, that's a nice trick
        autonomousSystem.drive();

        verify(drive).drive(0, 1);
    }

}
