package org.helenarobotics.ftc.team4309;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.swerverobotics.library.ClassFactory;
import org.swerverobotics.library.interfaces.Autonomous;

/**
 * This autonomous OpMode drives the robot in a square. The length of
 * the straight section and turns are calculated based on timing, which
 * will need to be calculated on a per-robot basis. The intent of this
 * class is to demonstrate use of the code, and is not intended to be an
 * accurate autonomous program without changes.
 *
 * This OpMode expects two motors, named 'motor_left' and 'motor_right'
 * respectively, which works with both legacy and modern motor
 * controllers.
 */
@Autonomous(name="Autonomous TimeBased")
public class AutonomousTiming extends LinearOpMode {
    // Should be measured/calculated on a per-robot basis.  Note, when
    // doing so, the power must be held constant for the numbers to be
    // accurate.
    private static final double MOTOR_POWER = 0.8;
    private static final double MS_PER_CM = 200;
    private static final double MS_PER_DEGREE = 90;

    // We stop the robot in between movements to keep the behavior as
    // repeatable as possible (no intertia).
    private static final long MS_TO_STOP = 100;

    private DcMotor rightMotor;
    private DcMotor leftMotor;

    @Override
    public void runOpMode() throws InterruptedException {
        leftMotor = hardwareMap.dcMotor.get("motor_left");
        rightMotor = hardwareMap.dcMotor.get("motor_right");
        leftMotor.setDirection(DcMotor.Direction.REVERSE);

        ClassFactory.createEasyLegacyMotorController(
            this, leftMotor, rightMotor);

        waitForStart();

        // Drive in a square
        for (int i = 0; i < 4; i++) {
            moveStraight(25.0);
            turnClockwise(90);
        }
        stopRobot();
    }

    private void moveStraight(double distanceCm) {
        long timeToWait = (long)(Math.abs(distanceCm) * MS_PER_CM);
        double power = MOTOR_POWER;
        if (distanceCm < 0) {
            power *= 1.0;
        }
        moveRobot(power, power, timeToWait);

        stopRobot();
    }

    private void turnClockwise(double degrees) {
        long timeToWait = (long)(Math.abs(degrees) * MS_PER_DEGREE);
        double leftPower = MOTOR_POWER;
        if (degrees < 0.0) {
            leftPower *= -1.0;
        }
        moveRobot(leftPower, -leftPower, timeToWait);

        stopRobot();
    }

    private void moveRobot(double leftPower, double rightPower, long msToWait) {
        leftMotor.setPower(leftPower);
        rightMotor.setPower(rightPower);
        delay(msToWait);
    }

    private void stopRobot() {
        leftMotor.setPower(0.0);
        rightMotor.setPower(0.0);
        delay(MS_TO_STOP);
    }

    private void delay(long msToDelay) {
        // Protect from silly programming errors
        if (msToDelay > 0) {
            try {
                Thread.sleep(msToDelay);
            } catch (InterruptedException ignored) {
            }
        }
    }
}
