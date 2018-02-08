package frc.team1091.robot.systems;

public enum PlatformPosition {
    UP(0), CENTER(90), DOWN(180);
    final int rotation;

    PlatformPosition(int i) {
        rotation = i;
    }
}
