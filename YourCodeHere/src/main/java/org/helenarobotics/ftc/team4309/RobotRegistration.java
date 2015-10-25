package org.helenarobotics.ftc.team4309;

import android.content.Context;

import org.swerverobotics.library.SwerveUtil;
import org.swerverobotics.library.interfaces.Autonomous;
import org.swerverobotics.library.interfaces.IOpModeManager;
import org.swerverobotics.library.interfaces.OnRobotRunning;
import org.swerverobotics.library.interfaces.OnRobotStartupFailure;
import org.swerverobotics.library.interfaces.OpModeRegistrar;
import org.swerverobotics.library.interfaces.TeleOp;

/**
 * RobotRegistration is a container for 'administrative' methods that interact
 * with the Swerve library. It's not necessary to do this, but it does help keep them neat and tidy.
 * Administrative methods are each tagged with a Java annotation that bestows their significance; see the
 * individual example methods for details. Note that administrative methods don't reside in any
 * given OpMode, but rather are used and invoked outside of the OpMode life cycle.
 */
public class RobotRegistration {
    /**
     * Registers any library OpModes that you wish to display. Change this code to suit
     * your needs: the specific OpModes that are registered as this code comes from
     * the factory are probably not what you want. For your own OpModes, though you could register them
     * here, it is preferable to annotate them in their own source with {@link TeleOp}
     * or {@link Autonomous} annotations, as appropriate.
     *
     * @param context   the application context of the robot controller application. Not often
     *                  actually used in OpMode registrar functions.
     * @param manager   the object through which registrations are effected
     */
    @OpModeRegistrar
    public static void registerMyOpModes(Context context, IOpModeManager manager) {
//        manager.register(FourWheelDriveOp.class);
//        manager.register(MecanumOp.class);
    }

    /**
     * Any public static method annotated with {@link OnRobotRunning} is invoked when the robot
     * object in the robot controller application enters the running state following an initial
     * boot or a 'restart robot'. One thing useful to do here is to play a sound of some sort
     * to provide an audible indicator that the robot is ready for use with the driver station,
     * but you could do whatever you like.
     *
     * @param context   the application context of the robot controller application. Useful for
     *                  interacting with other parts of the Android system, such creating a
     *                  MediaPlayer.
     * @see OnRobotRunning
     * @see OnRobotStartupFailure
     * @see #playSoundOnRobotStartupFailure(Context)
     */
    @OnRobotRunning
    public static void playSoundOnRobotRunning(Context context) {
        SwerveUtil.playSound(context, R.raw.nxtstartup);
    }

    /**
     * Any public static method annotated with {@link OnRobotStartupFailure} is invoked when the robot
     * object in the robot controller application fails to enter the running state during
     * an attempt to do so. A common cause of such failures is a mismatch between the robot
     * configuration file and the devices currently attached to the robot.
     *
     * @param context   the application context of the robot controller application. Useful for
     *                  interacting with other parts of the Android system, such creating a
     *                  MediaPlayer.
     * @see #playSoundOnRobotRunning(Context)
     */
    @OnRobotStartupFailure
    public static void playSoundOnRobotStartupFailure(Context context) {
        SwerveUtil.playSound(context, R.raw.chord);
    }
}
