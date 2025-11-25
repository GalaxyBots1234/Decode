package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class Opmode extends LinearOpMode {


    private DcMotor FRW, FLW, BLW, BRW;

    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        FRW=hardwareMap.get(DcMotor.class,"FRW");
        FLW=hardwareMap.get(DcMotor.class,"FLW");
        BRW=hardwareMap.get(DcMotor.class,"BRW");
        BLW=hardwareMap.get(DcMotor.class,"BLW");


        while(opModeIsActive()){

        }
    }
}
