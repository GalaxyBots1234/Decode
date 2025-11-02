package limelight;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

public class apriltag extends OpMode {
    private Limelight3A limelight3A;
    private IMU imu;

    @Override
    public void init() {
        limelight3A= hardwareMap.get(Limelight3A.class,"limelight");
        limelight3A.pipelineSwitch(3);
        imu = hardwareMap.get(IMU.class, "imu");
       RevHubOrientationOnRobot revHubOrientationOnRobot= new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.UP, RevHubOrientationOnRobot.UsbFacingDirection.FORWARD);
       imu.initialize(new IMU.Parameters(revHubOrientationOnRobot));
    }

    @Override
    public void start(){
        limelight3A.start();
    }

    @Override
    public void loop(){
        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        limelight3A.updateRobotOrientation(orientation.getYaw());
        LLResult llResult= limelight3A.getLatestResult();
        if (llResult!=null && llResult.isValid()){
            Pose3D botPose= llResult.getBotpose_MT2(); //
            telemetry.addData("Target X offset", llResult.getTx());
            telemetry.addData(" Target Y Offset", llResult.getTy());
            telemetry.addData("Target Area Offset", llResult.getTa());
        }
    }

}
