package org.firstinspires.ftc.teamcode.limelight;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

@TeleOp(name = "AprilTag Detection", group = "Limelight")
public class apriltag extends OpMode {
    private Limelight3A limelight3A;
    private IMU imu;

    @Override
    public void init() {
        // Initialize Limelight - use the name from your Robot Configuration
        // Change "limelight" to match your actual config name if different
        limelight3A = hardwareMap.get(Limelight3A.class, "limelight");

        // Switch to pipeline 8 (as you had it)
        limelight3A.pipelineSwitch(8);

        // Initialize IMU
        imu = hardwareMap.get(IMU.class, "imu");
        RevHubOrientationOnRobot revHubOrientationOnRobot = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
        );
        imu.initialize(new IMU.Parameters(revHubOrientationOnRobot));

        // Confirm initialization
        telemetry.addData("Status", "Initialized");
        telemetry.addData("Pipeline", "8");
        telemetry.update();
    }

    @Override
    public void start() {
        limelight3A.start();
        telemetry.addData("Status", "Running");
        telemetry.update();
    }

    @Override
    public void loop() {
        // Update robot orientation
        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        limelight3A.updateRobotOrientation(orientation.getYaw());

        // Get latest result from Limelight
        LLResult llResult = limelight3A.getLatestResult();

        if (llResult != null && llResult.isValid()) {
            // Get bot pose
            Pose3D botPose = llResult.getBotpose_MT2();

            // Display target information
            telemetry.addData("Status", "✓ Target Found");
            telemetry.addData("Target X Offset", "%.2f", llResult.getTx());
            telemetry.addData("Target Y Offset", "%.2f", llResult.getTy());
            telemetry.addData("Target Area", "%.2f", llResult.getTa());

            // Display bot pose if available
            if (botPose != null) {
                telemetry.addData("Bot X", "%.2f", botPose.getPosition().x);
                telemetry.addData("Bot Y", "%.2f", botPose.getPosition().y);
                telemetry.addData("Bot Z", "%.2f", botPose.getPosition().z);
                telemetry.addData("Bot Yaw", "%.2f", botPose.getOrientation().getYaw());
            }
        } else {
            // No valid target found
            telemetry.addData("Status", "✗ No Valid Target");
            telemetry.addData("Info", "Looking for AprilTags...");
        }

        // Display IMU data
        telemetry.addData("Robot Yaw", "%.2f", orientation.getYaw());

        // CRITICAL: Update telemetry to display on Driver Hub
        telemetry.update();
    }

    @Override
    public void stop() {
        // Stop the Limelight when OpMode ends
        limelight3A.stop();
        telemetry.addData("Status", "Stopped");
        telemetry.update();
    }
}