package frc.team1091.robot;

public class BoxSystem {
    private final double stopBoxIngestionAtValue = 3;
    private RobotComponents robotComponents;
    public BoxSystem (RobotComponents robotComponents) {
        this.robotComponents = robotComponents;
    }
    public void ingestBox(){
        //tell if box is present (ultrasonic, use mv to mm equation on the package)
        int averageRaw = robotComponents.ultraSonicAnalogInput.getAverageValue();
        boolean suckerSucking = robotComponents.suckerMotor.get() >= 0;
        boolean boxIngested = averageRaw >= stopBoxIngestionAtValue;
        if (boxIngested && !suckerSucking) return;
        if (robotComponents.pickUpPositionDigitalInput.get()) {
            if (!boxIngested){
                robotComponents.suckerMotor.set(1);
            }else{
                robotComponents.suckerMotor.set(0);
                while (robotComponents.gateClosePositionDigitalInput.get()){
                    //lift gate to pen in the box
                    robotComponents.gateMotor.set(.8);
                }
                robotComponents.gateMotor.set(0);
            }
        }


        //throw box

        //lift the platform at an angle and bring to drop box

    }
}