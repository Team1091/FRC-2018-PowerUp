package frc.team1091.robot.autonomous;

import com.team1091.planning.StartingPos;
import edu.wpi.first.wpilibj.DriverStation;
import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.autonomous.commands.Command;
import frc.team1091.robot.systems.ClawSystem;
import frc.team1091.robot.systems.DriveSystem;
import frc.team1091.robot.systems.ElevatorSystem;
import frc.team1091.robot.systems.VisionSystem;
import frc.team1091.robot.wrapper.EncoderWrapper;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class PlanningTest {

    @Test
    public void testPlanning() {
        DriveSystem drive = mock(DriveSystem.class);
        EncoderWrapper encoder = mock(EncoderWrapper.class);
        VisionSystem vs = mock(VisionSystem.class);
        ClawSystem ps = mock(ClawSystem.class);
        ElevatorSystem es = mock(ElevatorSystem.class);

        RobotComponents rc = new RobotComponents(null, null, null, null, null, null, null, null, encoder, null, null);

        Command result = Planner.plan(StartingPos.CENTER, DriverStation.Alliance.Blue, "RRR", rc, drive, vs, ps, es);

        assert result != null;

//        result.commands
    }

}
