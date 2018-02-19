package frc.team1091.robot.research;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static frc.team1091.robot.Utils.clamp;

class PidParams {
    double kp;
    double ki;
    double kd;

    double cost;

    public PidParams(double p, double i, double d, double cost) {
        this.kp = p;
        this.ki = i;
        this.kd = d;
        this.cost = cost;

    }

    public String toString() {
        return "Kp: " + kp + " Ki: " + ki + " Kd: " + kd + " time: " + cost;
    }
}

public class TestPid {


    @Test
    public void testPid() {

        final int setPoint = 100;
        List<PidParams> params = new ArrayList<>();

        Random r = new Random();

        for (int j = 0; j < 1000; j++) {
            double kp = 6.44;//r.nextDouble() * 100;
            double ki = 23.77;//r.nextDouble() * 100;
            double kd = 31.15;//r.nextDouble() * 100;

            float pos = 10;
            float vel = 0;


            PID pid = new PID(setPoint, kp, ki, kd);
            int step = 0;
            // If it takes more than 1000 to stabilize we don't want it.
            while (step++ < 1000) {

                double power = pid.step(0.1, pos);

                double acceleration = clamp(power, -1, 1);

                vel += acceleration * 0.1;
                pos += vel * 0.1;

                // System.out.println("Pos: " + pos + " Vel: " + vel + " Acc: " + acceleration + " Recommended: " + power);

                if (vel < 0.01 && vel > -0.01 && pos < setPoint + 0.1 && pos > setPoint - 0.1) {
                    // System.out.println("Stabilized in " + step);
                    PidParams pidParams = new PidParams(kp, ki, kd, step);
                    params.add(pidParams);
                    break;
                }

            }
        }

        params.stream().sorted(Comparator.comparingDouble((PidParams one) -> one.cost)).limit(10).forEach(System.out::println);
//        System.out.println(min);
    }


    @Test
    public void testClamp() {

        assert clamp(-2, -1, 1) == -1;
        assert clamp(2, -1, 1) == 1;
        assert clamp(0.5, -1, 1) == 0.5;
    }
}
