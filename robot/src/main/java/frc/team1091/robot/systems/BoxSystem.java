package frc.team1091.robot.systems;

import frc.team1091.robot.RobotComponents;

public class BoxSystem {
    private final double stopBoxIngestionAtValue = 3;
    private RobotComponents robotComponents;

    private BoxConsumptionState currentState;

    public BoxSystem(RobotComponents robotComponents) {
        currentState = BoxConsumptionState.Idle;
        this.robotComponents = robotComponents;
    }

    public void ingestBox() {

        switch (currentState) {
            case Idle:
                //tell if box is present (ultrasonic, use mv to mm equation on the package)
         /*       if (canIngestBox()) {
                    startIngestingBox();
                }
                break;
            case IngestingBox:
                if (isBoxIngested()) {
                    finishedIngestingBox();
                }
                break;
            case IngestedBox:
                closeGate();
                break;
            case ClosingGate:
                if (isGateClosed()) {
                    gateClosed();
                }
                break;*/
        }
        //throw box

        //lift the platform at an angle and bring to drop box
    }

    public boolean isBoxIngested() {
        int averageRaw = robotComponents.ultraSonicAnalogInput.getAverageValue();
        boolean boxIngested = averageRaw >= stopBoxIngestionAtValue;
        return boxIngested;
    }


    public void startIngestingBox() {
        currentState = BoxConsumptionState.IngestingBox;
        robotComponents.suckerMotor.set(1);
    }

    public void finishedIngestingBox() {
        currentState = BoxConsumptionState.IngestedBox;
        robotComponents.suckerMotor.set(0);
    }

    public void closeGate() {
        currentState = BoxConsumptionState.ClosingGate;
        //Keep moving motor to close
        robotComponents.platformMotor.set(.8);
    }

    public void gateClosed() {
        currentState = BoxConsumptionState.Idle;
        //Stop moving motor
        robotComponents.platformMotor.set(0);
    }


}

enum BoxConsumptionState {
    Idle,
    IngestingBox,
    IngestedBox,
    ClosingGate
}