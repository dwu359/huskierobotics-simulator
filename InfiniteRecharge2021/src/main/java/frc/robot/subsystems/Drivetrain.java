/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.lang.Math;
import java.util.Arrays;

import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.DriveConstants.FrontLeftWheel;
import frc.robot.Constants.DriveConstants.FrontRightWheel;
import frc.robot.Constants.DriveConstants.BackLeftWheel;
import frc.robot.Constants.DriveConstants.BackRightWheel;
import frc.robot.Constants.RobotConstants;

public class Drivetrain extends SubsystemBase {
  /**
   * Creates a new Drivetrain.
   */
  private Swervemodule frontLeft;
  // private Swervemodule frontRight;
  // private Swervemodule backLeft;
  // private Swervemodule backRight;

  private Translation2d frontLeftLocation;
  private Translation2d frontRightLocation; 
  private Translation2d backLeftLocation;
  private Translation2d backRightLocation; 
  private PigeonIMU gyro;
  private boolean isJoystickControlAllowed;
  private SwerveDriveKinematics kinematics;

  public Drivetrain() {
    this.frontLeft = new Swervemodule(FrontLeftWheel.SWERVE_MOTOR_CANID, FrontLeftWheel.SWERVE_ENCODER_DIO_A, FrontLeftWheel.SWERVE_ENCODER_DIO_B, FrontLeftWheel.DRIVE_MOTOR_CANID, FrontLeftWheel.DRIVE_ENCODER_CANID);
    // this.frontRight = new Swervemodule(FrontRightWheel.SWERVE_MOTOR_CANID, FrontRightWheel.SWERVE_ENCODER_DIO_A, FrontRightWheel.SWERVE_ENCODER_DIO_B, FrontRightWheel.DRIVE_MOTOR_CANID, FrontRightWheel.DRIVE_ENCODER_CANID);
    // this.backLeft = new Swervemodule(BackLeftWheel.SWERVE_MOTOR_CANID, BackLeftWheel.SWERVE_ENCODER_DIO_A, BackLeftWheel.SWERVE_ENCODER_DIO_B, BackLeftWheel.DRIVE_MOTOR_CANID, BackLeftWheel.DRIVE_ENCODER_CANID);
    // this.backRight = new Swervemodule(BackRightWheel.SWERVE_MOTOR_CANID, BackRightWheel.SWERVE_ENCODER_DIO_A, BackRightWheel.SWERVE_ENCODER_DIO_B, BackRightWheel.DRIVE_MOTOR_CANID, BackRightWheel.DRIVE_ENCODER_CANID);

    this.frontLeftLocation = new Translation2d(-RobotConstants.WIDTH/2, RobotConstants.LENGTH/2);
    this.frontRightLocation = new Translation2d(RobotConstants.WIDTH/2, RobotConstants.LENGTH/2);
    this.backLeftLocation = new Translation2d(-RobotConstants.WIDTH/2, -RobotConstants.LENGTH/2);
    this.backRightLocation = new Translation2d(RobotConstants.WIDTH/2, -RobotConstants.LENGTH/2);
    
    this.kinematics = new SwerveDriveKinematics(frontLeftLocation, frontRightLocation, backLeftLocation, backRightLocation);
    this.gyro = new PigeonIMU(RobotConstants.GYRO_CANID);
    this.isJoystickControlAllowed = true;
  }

  /**
   * Converts x,y joystick values to left,right motor outputs & sets motor power
   * joystick y -> forward, backward
   * joystick x -> turning
   */
  public void drive(double joystickX, double joystickY, double joystickZ, boolean fieldRelative) {
    if (this.isJoystickControlAllowed) {
      joystickX = deadZone(joystickX);
      joystickY = deadZone(joystickY);
      joystickZ = deadZone(joystickZ);

      SwerveModuleState[] swerveModuleStates = getSwerveModuleStates(joystickX, joystickY, joystickZ, fieldRelative);
      
      SwerveDriveKinematics.normalizeWheelSpeeds(swerveModuleStates, 1); // normalizes to 1 because of PercentOutput
      this.frontLeft.setDesiredState(swerveModuleStates[0]);
      // this.frontRight.setDesiredState(swerveModuleStates[1]);
      // this.backLeft.setDesiredState(swerveModuleStates[2]);
      // this.backRight.setDesiredState(swerveModuleStates[3]);
    }
  }

  public SwerveModuleState[] driveSim(double joystickX, double joystickY, double joystickZ, boolean fieldRelative) {
    joystickX = deadZone(joystickX);
    joystickY = deadZone(joystickY);
    joystickZ = deadZone(joystickZ);

    SwerveModuleState[] swerveModuleStates = getSwerveModuleStates(joystickX, joystickY, joystickZ, fieldRelative);
    
    SwerveDriveKinematics.normalizeWheelSpeeds(swerveModuleStates, 1); // normalizes to 1 because of PercentOutput
    return swerveModuleStates;
  }
  /**
   * sets joystick x,y to 0 if less than deadzone value
   */
  public double deadZone(double joystickVal) {
    if (Math.abs(joystickVal) < DriveConstants.DEADZONE) return 0.0;
    return joystickVal;
  }

  public void disableJoystickControls() {
    this.isJoystickControlAllowed = false;
  }

  public void enableJoystickControls() {
    this.isJoystickControlAllowed = true;
  }

  public void setXStance() {
    this.frontLeft.setDesiredState(new SwerveModuleState(0, new Rotation2d(this.frontLeftLocation.getX(), this.frontLeftLocation.getY())));
    // this.frontRight.setDesiredState(new SwerveModuleState(0, new Rotation2d(this.frontRightLocation.getX(), this.frontRightLocation.getY())));
    // this.backLeft.setDesiredState(new SwerveModuleState(0, new Rotation2d(this.backLeftLocation.getX(), this.backLeftLocation.getY())));
    // this.backRight.setDesiredState(new SwerveModuleState(0, new Rotation2d(this.backRightLocation.getX(), this.backRightLocation.getY())));
    
  }
  
  public SwerveModuleState[] getSwerveModuleStates(double joystickX, double joystickY, double joystickZ, boolean fieldRelative) {
    if (fieldRelative) return kinematics.toSwerveModuleStates(ChassisSpeeds.fromFieldRelativeSpeeds(joystickX, joystickY, joystickZ, getHeading()));
    return kinematics.toSwerveModuleStates(new ChassisSpeeds(joystickX, joystickY, joystickZ));
  }

  public Rotation2d getHeading() {
    double[] ypr = new double[3];
    gyro.getYawPitchRoll(ypr);
    return Rotation2d.fromDegrees(ypr[0]);
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
