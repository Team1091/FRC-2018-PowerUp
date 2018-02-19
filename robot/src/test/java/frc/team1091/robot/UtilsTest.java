package frc.team1091.robot;

import frc.team1091.robot.systems.ElevatorSystem;
import org.junit.Assert;
import org.junit.Test;

import static frc.team1091.robot.Utils.determineMotorSpeed;

public class UtilsTest {

    @Test
    public void determineMotorSpeed_ShouldReturnOne() {
        double speed = determineMotorSpeed(0, 1000, 10);
        Assert.assertEquals(1, speed, .00001);
    }

    @Test
    public void determineMotorSpeed_ShouldStepDown() {
        double result1 = determineMotorSpeed(15, 20.0, 5);
        double result2 = determineMotorSpeed(19, 20.0, 5);
        double result3 = determineMotorSpeed(20, 20.0, 5);
        double result4 = determineMotorSpeed(100, 20.0, 5);

        Assert.assertTrue(result1 == 1);
        Assert.assertTrue(result2 < result1);
        Assert.assertTrue(result3 == 0);
        Assert.assertTrue(result4 < 0);
    }

    @Test
    public void testDetermineMotorSpeed() {
        ElevatorSystem elevatorSystem = new ElevatorSystem(null);

        assert determineMotorSpeed(10, 20, 2) == 1;
        assert determineMotorSpeed(30, 20, 2) == -1;

        assert determineMotorSpeed(9.5, 10, 2) == 0.25;
        assert determineMotorSpeed(9.0, 10, 2) == 0.5;

        assert determineMotorSpeed(10.5, 10, 2) == -0.25;
        assert determineMotorSpeed(11, 10, 2) == -0.5;

        assert determineMotorSpeed(10, 10, 2) == 0;

    }
}
