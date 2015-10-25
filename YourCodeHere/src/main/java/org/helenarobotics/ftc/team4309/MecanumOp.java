package org.helenarobotics.ftc.team4309;

import org.swerverobotics.library.interfaces.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "Mecanum")
public class MecanumOp extends OpMode {
//    private final DcMotorController leftCtrl;
//    private final DcMotorController rightCtrl;

    private final DcMotor leftFront;
    private final DcMotor leftRear;
    private final DcMotor rightFront;
    private final DcMotor rightRear;

    public MecanumOp() {
        //
        // Connect the motor controllers
        //
//        leftCtrl = hardwareMap.dcMotorController.get("left_controller");
//        rightCtrl = hardwareMap.dcMotorController.get("right_controller");

        leftFront = hardwareMap.dcMotor.get("left_front");
        leftRear = hardwareMap.dcMotor.get("left_rear");
        rightFront = hardwareMap.dcMotor.get("right_front");
        rightRear = hardwareMap.dcMotor.get("right_rear");

        // Revert the motor direction on the left side
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftRear.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void start() {
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
        float xJoy1 = joyToExpoMotor(-gamepad1.left_stick_x);
        float yJoy1 = joyToExpoMotor(-gamepad1.left_stick_y);
        float xJoy2 = joyToExpoMotor(-gamepad1.right_stick_x);

        // Calculte the power for each motor based on joystick settings
        float forward = yJoy1;
        float right = xJoy1;
        float rotCw = xJoy2;

        float leftFrontPow = forward + rotCw + right;
        float rightFrontPow = forward - rotCw - right;
        float leftRearPow = forward + rotCw - right;
        float rightRearPow = forward - rotCw + right;

        // Make sure none of the wheel power's exceed 100%.  If so, reduce
        // all by the same amount.
        float max = Math.abs(leftFrontPow);
        if (Math.abs(rightFrontPow) > max)
            max = Math.abs(rightFrontPow);
        if (Math.abs(leftRearPow) > max)
            max = Math.abs(leftRearPow);
        if (Math.abs(rightRearPow) > max)
            max = Math.abs(rightRearPow);

        if (max > 1.0) {
            leftFrontPow = leftFrontPow / max;
            leftRearPow = leftRearPow / max;
            rightFrontPow = rightFrontPow / max;
            rightRearPow = rightRearPow / max;
        }
        leftFront.setPower(leftFrontPow);
        leftRear.setPower(leftRearPow);
        rightFront.setPower(rightFrontPow);
        rightRear.setPower(rightRearPow);
    }

    private final float SENSITIVITY = 0.7f;
    float joyToExpoMotor(float joyVal) {
        return (float)(SENSITIVITY * Math.pow(joyVal, 3.0) + (1 - SENSITIVITY) * joyVal);
    }
}
