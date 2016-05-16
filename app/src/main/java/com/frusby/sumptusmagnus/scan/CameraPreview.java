package com.frusby.sumptusmagnus.scan;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;

import java.io.FileOutputStream;

/**
 * Created by alexisjouhault on 3/29/16.
 */
public class CameraPreview extends MyJavaCameraView implements Camera.PictureCallback {

    private final String TAG = CameraPreview.class.toString();
    private String pictureFilename = null;
    private ScanningUtils.PictureTakenListener listener = null;

    public CameraPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     *
     * @param fileName picture destination, null if none
     */
    public void takePicture(final String fileName, ScanningUtils.PictureTakenListener listener) {
        // Postview and jpeg are sent in the same buffers if the queue is not empty when performing a capture.
        // Clear up buffers to avoid mCamera.takePicture to be stuck because of a memory issue
        mCamera.setPreviewCallback(null);

        this.listener = listener;
        pictureFilename = fileName;

        // PictureCallback is implemented by the current class
        try {
            mCamera.takePicture(null, null, this);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {

        // Write the image in a file (in jpeg format)
        if (pictureFilename != null) {
            try {
                FileOutputStream fos = new FileOutputStream(pictureFilename);

                fos.write(data);
                fos.close();

            } catch (java.io.IOException e) {
                Log.e("PictureDemo", "Exception in photoCallback", e);
            }
        }
        mCamera.startPreview();
        mCamera.setPreviewCallback(this);
        listener.pictureTaken(data);
    }
}
