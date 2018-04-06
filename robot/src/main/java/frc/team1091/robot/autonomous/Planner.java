package frc.team1091.robot.autonomous;

import com.team1091.math.Vec3;
import com.team1091.planning.EndingPos;
import com.team1091.planning.FieldMeasurement;
import com.team1091.planning.StartingPos;
import edu.wpi.first.wpilibj.DriverStation;
import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.autonomous.commands.*;
import frc.team1091.robot.systems.DriveSystem;
import frc.team1091.robot.systems.ElevatorSystem;
import frc.team1091.robot.systems.SuckerSystem;
import frc.team1091.robot.systems.VisionSystem;

import java.util.ArrayList;
import java.util.List;

public class Planner {

    // initialize the autonomous with a list of things to do.
    public static Command plan(StartingPos start,
                               DriverStation.Alliance alliance,
                               String gameGoalData,
                               RobotComponents components,
                               DriveSystem driveSystem,
                               VisionSystem visionSystem,
                               SuckerSystem suckerSystem,
                               ElevatorSystem elevatorSystem) {

        EndingPos close = gameGoalData.charAt(0) == 'R' ? EndingPos.RIGHT_SWITCH : EndingPos.LEFT_SWITCH;
        EndingPos far = gameGoalData.charAt(1) == 'R' ? EndingPos.RIGHT_SCALE : EndingPos.LEFT_SCALE;


//        close = EndingPos.LEFT_SWITCH;
        ArrayList<Command> commandList = new ArrayList<>();
        switch (start) {
            case LEFT:
                commandList.add(new DriveForwards(8 * 12, components, driveSystem));
                commandList.add(new DriveForwards(-1, components, driveSystem));
                commandList.add(new Turn(90, components, driveSystem));
                if (close == EndingPos.LEFT_SWITCH) {
                    commandList.add(new DriveForwards(2, components, driveSystem));
                    commandList.add(new LiftElevator(components));
                    commandList.add(new ReleaseBox(components, suckerSystem, elevatorSystem));
                }
                break;
            case RIGHT:
                commandList.add(new DriveForwards(8 * 12, components, driveSystem));
                // not sure the backwords breaks, so turning to break
                commandList.add(new DriveForwards(-1, components, driveSystem));
                commandList.add(new Turn(-90, components, driveSystem));
                if (close == EndingPos.RIGHT_SWITCH) {
                    commandList.add(new DriveForwards(2, components, driveSystem));
                    commandList.add(new LiftElevator(components));
                    commandList.add(new ReleaseBox(components, suckerSystem, elevatorSystem));
                }
                break;
            default: // center
                components.suckerMotor.set(0.2);
                commandList.add(new DriveForwards(2 * 12, components, driveSystem));
                commandList.add(new DriveForwards(-1, components, driveSystem));
                if (close == EndingPos.RIGHT_SWITCH) {
                    commandList.add(new Turn(90, components, driveSystem));
                    commandList.add(new DriveForwards(2 * 12, components, driveSystem));
                    commandList.add(new DriveForwards(-1, components, driveSystem));
                    commandList.add(new Turn(-90, components, driveSystem));
                } else {
                    commandList.add(new Turn(-90, components, driveSystem));
                    commandList.add(new DriveForwards(2 * 12, components, driveSystem));
                    commandList.add(new DriveForwards(-1, components, driveSystem));
                    commandList.add(new Turn(90, components, driveSystem));
                }
                commandList.add(new DriveForwards(1 * 12, components, driveSystem));
                commandList.add(new LiftElevator(components));
                commandList.add(new ReleaseBox(components, suckerSystem, elevatorSystem));
                // TODO: select a far or close goal
//                List<Obstacle> obstacles = Arrays.asList(
//                        //FieldMeasurement.Companion.getBoxPile()
//                );//visionSystem.getObstacles();
//
//                List<Vec3> farPath = makePath(start, far, obstacles);
//                List<Vec3> closePath = makePath(start, close, obstacles);
//                List<Vec3> actualPath;
//
//                if (closePath != null) {
//                    // be less ambitious
//                    actualPath = closePath;
//                } else if (farPath != null) {
//                    // be ambitious
//                    actualPath = farPath;
//                } else {
//                    // Well, at least we can drag ourselves forward
//                    return new DriveForwards(72, components, driveSystem);
//                }
//                commandList.addAll(getCommandList(components, driveSystem, actualPath));
                break;
        }


        // Pre-loaded, dont need to load.  Elevator encoder is broke, so don't use it
//        commandList.addAll(Arrays.asList(
//                new SuckBox(components, suckerSystem),
//                new Wait(100),
//                new SetElevatorPosition(ElevatorPosition.SWITCH_HEIGHT, elevatorSystem)
//        ));


        // drive up to the target
//        commandList.add(new DriveUntilClose(alliance, components, driveSystem, visionSystem));

        // unload box
//        commandList.add(new ReleaseBox(components, suckerSystem, elevatorSystem));

        // TODO: we dont want to just drive forwards 24 inches
        //return new DriveForwards(6 * 12, components, driveSystem);
        //return new Turn(-90, components, driveSystem);
//        return new CommandList(
//                new DriveForwards(24, components, driveSystem),
//                new Turn(-90, components, driveSystem),
//                new DriveForwards(24, components, driveSystem),
//                new Turn(-90, components, driveSystem),
//                new DriveForwards(24, components, driveSystem),
//                new Turn(-90, components, driveSystem),
//                new DriveForwards(24, components, driveSystem),
//                new Turn(-90, components, driveSystem)
//        );
        return new CommandList(commandList);
    }

    // At this point we have a list of positions we want to be at, we need to translate that into a list of commands to get the robot there
    private static ArrayList<Command> getCommandList(RobotComponents components, DriveSystem driveSystem, List<Vec3> path) {

        // Convert list of points into driving instructions - need to parse out turns.
        ArrayList<Command> commandList = new ArrayList<>();

        double turn = 0;
        double forward = 0;

        for (int i = 0; i < path.size() - 1; i++) {
            Vec3 lastNode = path.get(i);
            Vec3 nextNode = path.get(i + 1);

            if (lastNode.getZ() != nextNode.getZ()) { // we turned

                if (forward != 0) {
                    // we changed from turn to go forward, save forward
                    commandList.add(new DriveForwards(forward, components, driveSystem));
                    forward = 0;
                }
                turn += 90.0 * ((lastNode.getZ() > nextNode.getZ()) ? -1 : 1);

            } else {//we are going forward/backward

                if (turn != 0) {
                    // we changed from forward to turn, save it
                    commandList.add(new Turn(turn, components, driveSystem));
                    turn = 0;
                }
                forward += FieldMeasurement.Companion.getBlockSize().toInches();

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
