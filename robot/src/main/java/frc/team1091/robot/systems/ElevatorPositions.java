package frc.team1091.robot.systems;

public enum ElevatorPositions {

    GROUND_HEIGHT(0.0),
    SWITCH_HEIGHT(36.0),
    SCALE_HEIGHT(72.0);

    public final double inches;

    ElevatorPositions(double inches) {
        this.inches = inches;
    }
}
