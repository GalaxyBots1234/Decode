package org.firstinspires.ftc.teamcode.limelight;
// Specials thanks to Brogan M. Pratt for his helpful yt videos
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
// Pipiline 0

public class color extends OpMode {
    private Limelight3A limelight3A;
    @Override
    public void init() {
        limelight3A= hardwareMap.get(Limelight3A.class, "org/firstinspires/ftc/teamcode/limelight");
        limelight3A.pipelineSwitch(0);

    }

    @Override
    public void start(){
        limelight3A.start();
    }

    @Override
    public void loop(){
        LLResult llResult= limelight3A.getLatestResult();
        if (llResult!=null && llResult.isValid()){
            telemetry.addData("Target X offset", llResult.getTx());
            telemetry.addData(" Target Y Offset", llResult.getTy());
            telemetry.addData("Target Area Offset", llResult.getTa());
        }
    }

}