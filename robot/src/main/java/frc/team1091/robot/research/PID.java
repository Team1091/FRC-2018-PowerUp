package frc.team1091.robot.research;

public class PID {


    private double setPoint;
    private double Kp;
    private double Ki;
    private double Kd;

    //private double integral;
    double integralTerm = 0;
    double lastValue = 0;

    public PID(double setPoint, double Kp, double Ki, double Kd) {
        this.setPoint = setPoint;
        this.Kp = Kp;
        this.Ki = Ki;
        this.Kd = Kd;
        //  integral = 0;
    }

    public double step(double dt, double measuredValue) {
        double error = setPoint - measuredValue;
        integralTerm += Ki * error * dt;
        integralTerm = clamp(integralTerm, -1, 1);

        double dInput = measuredValue - lastValue;
        double derivativeTerm = Kd * (dInput / dt);

        double proportionalTerm = Kp * (error);
        double output = proportionalTerm + integralTerm - derivativeTerm;

        lastValue = measuredValue;
        return clamp(output, -1, 1);
    }

    public double clamp(double value, double min, double max) {
        return Math.min(Math.max(value, min), max);
    }

}
