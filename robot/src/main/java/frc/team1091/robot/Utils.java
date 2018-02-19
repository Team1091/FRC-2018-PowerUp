package frc.team1091.robot;

public class Utils {

    public static double determineMotorSpeed(double currentPosition, double desiredPosition, double rampWidth) {
        double d = desiredPosition - currentPosition;

        if (d < -rampWidth) {
            return -1.0;
        }
        if (d > rampWidth) {
            return 1.0;
        }
        double power = d / rampWidth;
        return power;
    }

    public static double clamp(double val) {
        return Math.max(-1, Math.min(1, val));
    }

    public static double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }
}
