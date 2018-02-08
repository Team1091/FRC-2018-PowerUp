package frc.team1091.robot.autonomous;

import com.team1091.math.Rectangle;
import com.team1091.math.Vec2;
import com.team1091.math.Vec3;
import com.team1091.planning.EndingPos;
import com.team1091.planning.Facing;
import com.team1091.planning.StartingPos;
import edu.wpi.first.wpilibj.DriverStation;
import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.autonomous.commands.*;
import frc.team1091.robot.systems.DriveSystem;
import frc.team1091.robot.systems.ElevatorSystem;
import frc.team1091.robot.systems.PlatformSystem;
import frc.team1091.robot.systems.VisionSystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.team1091.planning.PathMakerKt.makePath;

public class Planner {

    public static Command plan(StartingPos start,
                               DriverStation.Alliance alliance,
                               String gameGoalData,
                               RobotComponents components,
                               DriveSystem driveSystem,
                               VisionSystem visionSystem,
                               PlatformSystem platformSystem,
                               ElevatorSystem elevatorSystem) {
        // initialize the autonomous with a list of things to do.
        // This depends on our starting position and goal we want to go for

        // http://wpilib.screenstepslive.com/s/currentCS/m/getting_started/l/826278-2018-game-data-details
        // this will be a 3 character string with your goals side marked, index 0 being the closest.
        // L = left side
        // R = right side
        //
        // Ex: "LRL", "RRR", "LRR"
        // TODO: Translate gameGoalData into a goal we want to go to
        EndingPos end = EndingPos.RIGHT_SCALE;

        List<Vec3> path = makePath(start, end, Arrays.asList(
                // another robot's plan takes this zone.  It would be nice to
                new Rectangle(Vec2.Companion.get(15, 0), Vec2.Companion.get(15, 10))
        ));
        ArrayList<Command> commandList = getCommandList(components, driveSystem, path);

        // drive up to the target
        commandList.add(new DriveUntilClose(alliance, components, driveSystem, visionSystem));

        // unload box
        commandList.add(new BarfBox(components, platformSystem, elevatorSystem));

        return new CommandList(commandList);
    }

    private static ArrayList<Command> getCommandList(RobotComponents components, DriveSystem driveSystem, List<Vec3> path) {
        // Convert list of points into driving instructions - need to parse out turns.
        ArrayList<Command> commandList = new ArrayList<>();

        // At this point we have a list of positions we want to be at, we need to translate that into a list of commands to get the robot there
        // we can start at 1, since we are are already in our current position.

        double turn = 0;
        double forward = 0;

        for (int i = 0; i < path.size() - 1; i++) {
            Vec3 lastNode = path.get(i);
            Vec3 nextNode = path.get(i + 1);
//            Command command = commandList.get(commandList.size()-1);

            if (lastNode.getZ() != nextNode.getZ()) { // we turned

                if (forward != 0) {
                    // we changed from turn to go forward, save forward
                    commandList.add(new DriveForwards(forward, components, driveSystem));
                    forward = 0;
                }
                turn += 90.0 * (lastNode.getZ() - nextNode.getZ());

            } else {//we are going forward/backward

                if (turn != 0) {
                    // we changed from forward to turn, save it
                    commandList.add(new Turn(turn, components, driveSystem));
                    turn = 0;
                }
                Facing facing = Facing.values()[lastNode.getZ()];
                forward += 32.0;// TODO/ forwards/backwards

            }
        }

        // If we have a remainder, create an action
        if (turn != 0) {
            commandList.add(new Turn(turn, components, driveSystem));
        }
        if (forward != 0) {
            commandList.add(new DriveForwards(forward, components, driveSystem));
        }
        return commandList;
    }

}
