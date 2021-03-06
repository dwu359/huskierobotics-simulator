/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    public final class RobotConstants {
        public static final int COMPRESSOR_CANID = 19;
        public static final double LENGTH = 0.762; // 30 inches = 0.762 meters
        public static final double WIDTH = 0.762;
        /**
         *   width
         * FL----FR
         * |      |
         * |      |
         * |      | length
         * |      |
         * |      |
         * BL----BR
         */
        public static final int GYRO_CANID = 13;
    }

    public final class DriveConstants {
        public final class FrontLeftWheel {
            public static final int SWERVE_MOTOR_CANID = 13;
            public static final int DRIVE_MOTOR_CANID = 12;
            public static final int SWERVE_ENCODER_DIO_A = 0;
            public static final int SWERVE_ENCODER_DIO_B = 1;
            public static final int DRIVE_ENCODER_CANID = 12;
        }

        public final class FrontRightWheel {
            public static final int SWERVE_MOTOR_CANID = 4;
            public static final int DRIVE_MOTOR_CANID = 5;
            public static final int SWERVE_ENCODER_DIO_A = 0;
            public static final int SWERVE_ENCODER_DIO_B = 1;
            public static final int DRIVE_ENCODER_CANID = 7;
        }

        public final class BackLeftWheel {
            public static final int SWERVE_MOTOR_CANID = 8;
            public static final int DRIVE_MOTOR_CANID = 9;
            public static final int SWERVE_ENCODER_DIO_A = 0;
            public static final int SWERVE_ENCODER_DIO_B = 1;
            public static final int DRIVE_ENCODER_CANID = 11;
        }

        public final class BackRightWheel {
            public static final int SWERVE_MOTOR_CANID = 12;
            public static final int DRIVE_MOTOR_CANID = 13;
            public static final int SWERVE_ENCODER_DIO_A = 0;
            public static final int SWERVE_ENCODER_DIO_B = 1;
            public static final int DRIVE_ENCODER_CANID = 15;
        }
        
        public static final int JOYSTICK_0 = 0;
        public static final int JOYSTICK_1 = 1;
        public static final int NUM_JOYSTICK_BUTTONS = 13;
        public static final int JOYSTICK_INVERT_BUTTON = 3;
        public static final int JOYSTICK_HIGHGEAR_BUTTON = 0;
        public static final int JOYSTICK_XSTANCE_BUTTON = 1;
        
        public static final double DEADZONE = 0.1;

        public static final double ENCODER_CPR = 360;
        public static final double WHEEL_DIAMETER_METERS = 0.1524;
        public static final double ENCODER_DISTANCE_PER_COUNT = (WHEEL_DIAMETER_METERS * Math.PI) / (double) ENCODER_CPR;

        public static final double RATE_LIMIT = 0.5;
        
        public static final double SWERVE_KP = 1;
        public static final double SWERVE_KI = 0;
        public static final double SWERVE_KD = 0;

        /*
        public static final double DRIVE_KP = 1;
        public static final double DRIVE_KI = 0;
        public static final double DRIVE_KD = 0;*/
    }
}