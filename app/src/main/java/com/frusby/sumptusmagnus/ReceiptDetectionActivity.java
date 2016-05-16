package com.frusby.sumptusmagnus;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.frusby.sumptusmagnus.scan.CameraPreview;
import com.frusby.sumptusmagnus.scan.ImageAnalysisManager;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ReceiptDetectionActivity extends FragmentActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    private final String TAG = FragmentActivity.class.toString();
    private ImageAnalysisManager imageAnalysisManager = new ImageAnalysisManager();

    @Bind(R.id.camera_preview)
    CameraPreview cameraPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_detection);
        ButterKnife.bind(this);

        cameraPreview.setCvCameraViewListener(this);
    }

    @Override
    protected void onResume() {
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_1_0, this, mLoaderCallback);
        super.onResume();
    }

    @Override
    public void onCameraViewStarted(int width, int height) {

    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        return imageAnalysisManager.test(inputFrame.rgba());
    }

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    cameraPreview.setMaxFrameSize(1920, 1080);
                    //cameraView.setResolution(new Size(1280, 720));
                    cameraPreview.enableView();
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };
}
