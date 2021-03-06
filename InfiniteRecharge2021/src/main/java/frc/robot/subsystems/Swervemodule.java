/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.CANCoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

public class Swervemodule extends SubsystemBase {
    private CANSparkMax swerveMotor; // makes the wheel swerve
    private TalonFX driveMotor; // makes the wheel drive
    private Encoder swerveEncoder;
    private CANCoder driveEncoder;
    private PIDController swervePIDController;
    //private PIDController drivePIDController;
  /**
   * Creates a new Swervemodule
   */
  public Swervemodule(int swerveMotorCANID, int swerveEncoderDIOA, int swerveEncoderDIOB, int driveMotorCANID, int driveEncoderCANID) {
    this.swerveMotor = new CANSparkMax(swerveMotorCANID, MotorType.kBrushless);
    this.driveMotor = new TalonFX(driveMotorCANID);
    this.driveMotor.setNeutralMode(NeutralMode.Brake);

    this.swerveEncoder = new Encoder(swerveEncoderDIOA, swerveEncoderDIOB);
    this.driveEncoder = new CANCoder(driveEncoderCANID);

    this.swervePIDController = new PIDController(DriveConstants.SWERVE_KP, DriveConstants.SWERVE_KI, DriveConstants.SWERVE_KD);
    //this.drivePIDController = new PIDController(DriveConstants.DRIVE_KP, DriveConstants.DRIVE_KI, DriveConstants.DRIVE_KD);
    this.swervePIDController.enableContinuousInput(-Math.PI, Math.PI);
  }

  public SwerveModuleState getState() {
    return new SwerveModuleState(swerveEncoder.getRate(), new Rotation2d(swerveEncoder.getDistance()));
  }

  public void setDesiredState(SwerveModuleState desiredState) {
    // Optimize the reference state to avoid spinning further than 90 degrees. Explanation of what this does: https://www.desmos.com/calculator/62d9utippo
    SwerveModuleState state = SwerveModuleState.optimize(desiredState, new Rotation2d(swerveEncoder.getDistance()));

    // Calculate the drive output from the drive PID controller.
    //final double driveOutput = m_drivePIDController.calculate(m_driveEncoder.getRate(), state.speedMetersPerSecond);

    // Calculate the turning motor output from the turning PID controller.
    double swerveOutput = swervePIDController.calculate(state.angle.getRadians(), 0);

    SmartDashboard.putNumber("Swerve Motor Output", swerveOutput);
    swerveMotor.set(swerveOutput);
    driveMotor.set(ControlMode.PercentOutput, state.speedMetersPerSecond);
    
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
