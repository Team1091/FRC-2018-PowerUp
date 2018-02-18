package frc.team1091.robot.systems;

public enum PlatformPosition {
    UP(0), CENTER(50), DOWN(70);
    final int rotation;

    PlatformPosition(int i) {
        rotation = i;
    }
}
