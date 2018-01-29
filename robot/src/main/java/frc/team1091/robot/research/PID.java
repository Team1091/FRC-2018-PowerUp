package frc.team1091.robot.research;

public class PID {


    private double setpoint;
    private double Kp ;
    private double Ki ;
    private double Kd ;

    //private double integral;
    double integralTerm = 0;
    double lastValue = 0;

    public PID(double setpoint, double Kp, double Ki, double Kd) {
        this.setpoint = setpoint;
        this.Kp = Kp;
        this.Ki = Ki;
        this.Kd = Kd;
      //  integral = 0;
    }

    public double step(double dt, double measuredValue){
        double error = setpoint - measuredValue;
        integralTerm += Ki * error * dt;
        integralTerm =  clamp(integralTerm, -1,1);

        double dInput = measuredValue - lastValue;
        double derivitiveTerm = Kd * (dInput/dt);

        double proportionalTerm = Kp * (error);
        double output = proportionalTerm + integralTerm - derivitiveTerm;

        lastValue = measuredValue;
        return clamp(output,-1,1);
    }

    public double clamp(double value, double min, double max) {
        return Math.min(Math.max(value, min), max);
    }

}
