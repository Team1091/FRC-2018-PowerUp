package frc.team1091.robot.autonomous;

import com.team1091.math.Rectangle;
import com.team1091.math.Vec2;
import com.team1091.planning.EndingPos;
import com.team1091.planning.StartingPos;
import edu.wpi.first.wpilibj.DriverStation;
import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.RobotControlSystems;
import frc.team1091.robot.autonomous.commands.Command;
import frc.team1091.robot.autonomous.commands.CommandList;
import frc.team1091.robot.autonomous.commands.DriveForwards;
import frc.team1091.robot.autonomous.commands.SpinOutOfControl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.team1091.planning.PathMakerKt.makePath;

public class Planner {

    public Command plan(RobotComponents components, RobotControlSystems controlSystem) {

        // initialize the autonomous with a list of things to do.
        // This depends on our starting position and goal we want to go for

        DriverStation driverStation = DriverStation.getInstance();
        DriverStation.Alliance alliance = driverStation.getAlliance();

        // I think this is where we are starting this year, could be only the driver station tho
//        int driverStationLocation = driverStation.getLocation();

        // http://wpilib.screenstepslive.com/s/currentCS/m/getting_started/l/826278-2018-game-data-details
        // this will be a 3 character string with your goals side marked, index 0 being the closest.
        // L = left side
        // R = right side
        //
        // Ex: "LRL", "RRR", "LRR"
        String gameGoalData = DriverStation.getInstance().getGameSpecificMessage();

        // Translate that into a goal we want to go to

        // starting path - set fom dropdown?
        StartingPos start = StartingPos.LEFT;
        EndingPos end = EndingPos.RIGHT_SCALE;

        List<Vec2> path = makePath(start, end, Arrays.asList(
                // another robot's plan takes this zone.  It would be nice to
                new Rectangle(Vec2.Companion.get(15, 0), Vec2.Companion.get(15, 10))
        ));

        // Convert list of points into driving instructions - need to parse out turns.

        List<Command> commandList = new ArrayList<>();


        return new CommandList(
                new DriveForwards(123.0, components, controlSystem),
                new SpinOutOfControl(components, controlSystem)
        );
//        return new CommandList();
    }

}
