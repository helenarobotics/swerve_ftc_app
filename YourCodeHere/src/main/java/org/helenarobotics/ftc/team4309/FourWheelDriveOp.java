package org.helenarobotics.ftc.team4309;

import org.swerverobotics.library.interfaces.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "4WD")
public class FourWheelDriveOp extends OpMode {
    private DcMotor leftFront;
    private DcMotor leftRear;
    private DcMotor rightFront;
    private DcMotor rightRear;

    @Override
    public void init() {
        leftFront = hardwareMap.dcMotor.get("left_front");
        leftRear = hardwareMap.dcMotor.get("left_rear");
        rightFront = hardwareMap.dcMotor.get("right_front");
        rightRear = hardwareMap.dcMotor.get("right_rear");

        // Revert the motor direction on the left side
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftRear.setDirection(DcMotor.Direction.REVERSE);

        leftFront.setPower(0.0);
        leftRear.setPower(0.0);
        rightFront.setPower(0.0);
        rightRear.setPower(0.0);
    }

    @Override
    public void stop() {
        leftFront.setPower(0.0);
        leftRear.setPower(0.0);
        rightFront.setPower(0.0);
        rightRear.setPower(0.0);
    }

    @Override
    public void loop() {
        // Manage the drive wheel motors.
        // Make things less sensitive around the center (a slight
        // dead-band), and more aggressive at the extremes.
        float xJoy2 = joyToExpoMotor(gamepad1.right_stick_x);
        float yJoy1 = joyToExpoMotor(gamepad1.left_stick_y);

        float leftPower = yJoy1 - xJoy2;
        float rightPower = yJoy1 + xJoy2;

        // Make sure none of the wheel power's exceed 100%.  If so, reduce
        // all by the same amount.
        float max = Math.abs(leftPower);
        if (Math.abs(rightPower) > max)
            max = Math.abs(rightPower);
        if (max > 1.0) {
            leftPower /= max;
            rightPower /= max;
        }
        leftFront.setPower(leftPower);
        leftRear.setPower(leftPower);
        rightFront.setPower(rightPower);
        rightRear.setPower(rightPower);
    }

    private final float SENSITIVITY = 0.7f;
    float joyToExpoMotor(float joyVal) {
        return (float)(SENSITIVITY * Math.pow(joyVal, 3.0) + (1 - SENSITIVITY) * joyVal);
    }
}
