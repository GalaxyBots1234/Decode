package org.firstinspires.ftc.teamcode.limelight;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp(name = "AprilTag Detection", group = "Limelight")
public class Apriltag_detection extends OpMode {

    private Limelight3A limelight3A;
    private IMU imu;

    @Override
    public void init() {

        telemetry.setMsTransmissionInterval(11);

        limelight3A = hardwareMap.get(Limelight3A.class, "limelight");

        // Correct form (SDK 9.0+)
        limelight3A.pipelineSwitch(8);

        imu = hardwareMap.get(IMU.class, "imu");
        RevHubOrientationOnRobot revHubOrientationOnRobot = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
        );
        imu.initialize(new IMU.Parameters(revHubOrientationOnRobot));

        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    @Override
    public void start() {
        limelight3A.start();
    }

    @Override
    public void loop() {

        // Corrected: always use degrees
        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        limelight3A.updateRobotOrientation(orientation.getYaw(AngleUnit.DEGREES));

        LLResult result = limelight3A.getLatestResult();

        if (result != null && result.isValid()) {

            Pose3D botPose = result.getBotpose_MT2();

            telemetry.addData("Status", "Target Found");
            telemetry.addData("tx", result.getTx());
            telemetry.addData("ty", result.getTy());
            telemetry.addData("ta", result.getTa());

            if (botPose != null) {
                telemetry.addData("Bot X", botPose.getPosition().x);
                telemetry.addData("Bot Y", botPose.getPosition().y);
                telemetry.addData("Bot Z", botPose.getPosition().z);
                telemetry.addData("Bot Yaw", botPose.getOrientation().getYaw());
            }

        } else {
            telemetry.addData("Status", "No Target");
        }

        telemetry.addData("IMU Yaw", orientation.getYaw(AngleUnit.DEGREES));
        telemetry.update();
    }

    @Override
    public void stop() {
        limelight3A.stop();
    }
}
//package org.firstinspires.ftc.teamcode.limelight;
//
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.hardware.limelightvision.LLResult;
//import com.qualcomm.hardware.limelightvision.Limelight3A;
//import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
//import com.qualcomm.robotcore.hardware.IMU;
//import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
//import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
//import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
//
//@TeleOp(name = "AprilTag Detection", group = "Limelight")
//public class AprilTag extends OpMode {
//
//    private Limelight3A limelight3A;
//    private IMU imu;
//
//    @Override
//    public void init() {
//
//        telemetry.setMsTransmissionInterval(11);
//
//        limelight3A = hardwareMap.get(Limelight3A.class, "limelight");
//
//        // Correct form (SDK 9.0+)
//        limelight3A.pipelineSwitch(8);
//
//        imu = hardwareMap.get(IMU.class, "imu");
//        RevHubOrientationOnRobot revHubOrientationOnRobot = new RevHubOrientationOnRobot(
//                RevHubOrientationOnRobot.LogoFacingDirection.UP,
//                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
//        );
//        imu.initialize(new IMU.Parameters(revHubOrientationOnRobot));
//
//        telemetry.addData("Status", "Initialized");
//        telemetry.update();
//    }
//
//    @Override
//    public void start() {
//        limelight3A.start();
//    }
//
//    @Override
//    public void loop() {
//
//        // Corrected: always use degrees
//        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
//        limelight3A.updateRobotOrientation(orientation.getYaw(AngleUnit.DEGREES));
//
//        LLResult result = limelight3A.getLatestResult();
//
//        if (result != null && result.isValid()) {
//
//            Pose3D botPose = result.getBotpose_MT2();
//
//            telemetry.addData("Status", "Target Found");
//            telemetry.addData("tx", result.getTx());
//            telemetry.addData("ty", result.getTy());
//            telemetry.addData("ta", result.getTa());
//
//            if (botPose != null) {
//                telemetry.addData("Bot X", botPose.getPosition().x);
//                telemetry.addData("Bot Y", botPose.getPosition().y);
//                telemetry.addData("Bot Z", botPose.getPosition().z);
//                telemetry.addData("Bot Yaw", botPose.getOrientation().getYaw());
//            }
//
//        } else {
//            telemetry.addData("Status", "No Target");
//        }
//
//        telemetry.addData("IMU Yaw", orientation.getYaw(AngleUnit.DEGREES));
//        telemetry.update();
//    }
//
//    @Override
//    public void stop() {
//        limelight3A.stop();
//    }
//}