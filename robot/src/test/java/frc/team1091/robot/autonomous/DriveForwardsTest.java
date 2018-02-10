package frc.team1091.robot.autonomous;

import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.autonomous.commands.CommandList;
import frc.team1091.robot.autonomous.commands.DriveForwards;
import frc.team1091.robot.autonomous.commands.Turn;
import frc.team1091.robot.systems.AutonomousSystem;
import frc.team1091.robot.systems.DriveSystem;
import frc.team1091.robot.wrapper.EncoderWrapper;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class DriveForwardsTest {
    private int testNum;

    @Test
    public void testThatWeDriveForwards() {
        DriveSystem drive = mock(DriveSystem.class);

        EncoderWrapper rEncoder = mock(EncoderWrapper.class);
        EncoderWrapper lEncoder = mock(EncoderWrapper.class);

        RobotComponents rc = new RobotComponents(null, null, null, null, null, null, null, null, lEncoder, rEncoder, null, null, null, null);

        when(rEncoder.getDistance()).thenReturn(10.0);
        when(lEncoder.getDistance()).thenReturn(10.0);

        AutonomousSystem autonomousSystem = Mockito.spy(new AutonomousSystem());
        Mockito.doNothing().when(autonomousSystem).log((String) notNull()); // Skip smartdashboard

        autonomousSystem.init(
                new CommandList(
                        new DriveForwards(100, rc, drive),
                        new Turn(90, rc, drive)
                )
        );

        autonomousSystem.drive();

        verify(lEncoder).getDistance();
        verify(rEncoder).getDistance();
        verify(drive).drive(1, 0);

        // Next we want to test that when the encoder is past the threshold, that it stops and goes to the next command
        when(lEncoder.getDistance()).thenReturn(101.0 * 360.0 / 4.0);
        when(rEncoder.getDistance()).thenReturn(101.0 * 360.0 / 4.0);

        autonomousSystem.drive();

        // We should have stopped.  We don't want to keep driving
        verify(drive).drive(0, 0);

        // Got rid of this
//        assert testNum == 0;
//        autonomousSystem.drive();
//        assert testNum == 1;

        // Lets try spinning, that's a nice trick
        autonomousSystem.drive();

        verify(drive).drive(0, 1);
    }

}