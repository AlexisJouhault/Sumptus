package com.frusby.sumptusmagnus.scan;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexisjouhault on 4/4/16.
 */
public class ImageAnalysisManager {

    private List<MatOfPoint> detectedShapes = new ArrayList<>();
    private double lastArea = -1;

    private MatOfPoint approxPolyDP(MatOfPoint curve, double epsilon, boolean closed) {
        MatOfPoint2f tempMat=new MatOfPoint2f();
        Imgproc.approxPolyDP(new MatOfPoint2f(curve.toArray()), tempMat, epsilon, closed);

        return new MatOfPoint(tempMat.toArray());
    }

    private double angle( Point pt1, Point pt2, Point pt0 ) {
        double dx1 = pt1.x - pt0.x;
        double dy1 = pt1.y - pt0.y;
        double dx2 = pt2.x - pt0.x;
        double dy2 = pt2.y - pt0.y;
        return (dx1*dx2 + dy1*dy2)/Math.sqrt((dx1*dx1 + dy1*dy1)*(dx2*dx2 + dy2*dy2) + 1e-10);
    }

    public Mat test(Mat source) {

        MatOfPoint biggestSquare;

        Mat modifiedImage = new Mat(source.width(), source.height(), source.type());
        Imgproc.cvtColor(source, modifiedImage, Imgproc.COLOR_BGR2GRAY);
        Imgproc.blur(modifiedImage, modifiedImage, new Size(3, 3));
        Imgproc.Canny(modifiedImage, modifiedImage, 10, 100);
        Imgproc.blur(modifiedImage, modifiedImage, new Size(2, 2));
        Imgproc.findContours(modifiedImage, detectedShapes, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

        MatOfPoint approx;
        for (int i = 0; i < detectedShapes.size(); i++) {
            approx = approxPolyDP(detectedShapes.get(i), Imgproc.arcLength(new MatOfPoint2f(detectedShapes.get(i).toArray()), true) * 0.02, true);

            if (approx.toArray().length == 4 &&
                    Math.abs(Imgproc.contourArea(approx)) > 1000 &&
                    Imgproc.isContourConvex(approx)) {

                double maxCosine = 0;
                for (int j = 2; j < 5; j++) {
                    // find the maximum cosine of the angle between joint edges
                    double cosine = Math.abs(angle(approx.toArray()[j % 4], approx.toArray()[j - 2], approx.toArray()[j - 1]));
                    maxCosine = Math.max(maxCosine, cosine);
                }

                if (maxCosine < 0.3 && (detectedShapes.isEmpty() || Imgproc.contourArea(approx) > lastArea)) {
                    lastArea = Imgproc.contourArea(approx);
                    biggestSquare = approx;
                }
            }
        }

        return modifiedImage;
    }
}
