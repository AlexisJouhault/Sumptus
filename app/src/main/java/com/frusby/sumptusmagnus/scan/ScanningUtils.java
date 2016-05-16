package com.frusby.sumptusmagnus.scan;

import android.util.Log;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexisjouhault on 3/29/16.
 */
public class ScanningUtils {

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public static String PICTURE_EXTRA = "picture";

    public enum State {
        SCANNING_PAGE,
        EDIT_PAGE,
        CONFIRMATION_PAGE
    }

    public interface PictureTakenListener {
        public void pictureTaken(byte[] data);
    }

    private static int N = 11;

    // returns sequence of squares detected on the image.
    // the sequence is stored in the specified memory storage
    public static List<MatOfPoint> findSquares(Mat image)
    {
        List<MatOfPoint> squares = new ArrayList<>();
        double lastArea = -1;

        Mat smallerImg = new Mat(new Size(image.width()/2, image.height()/2),image.type());
        Mat gray = new Mat(image.size(),image.type());
        Mat gray0 = new Mat(image.size(), CvType.CV_8U);

        // down-scale and upscale the image to filter out the noise
        Imgproc.pyrDown(image, smallerImg, smallerImg.size());
        Imgproc.pyrUp(smallerImg, image, image.size());


        // find squares in every color plane of the image
        for( int c = 0; c < 3; c++ )
        {
            extractChannel(image, gray, c);
            // try several threshold levels
            for( int l = 1; l < N; l++ )
            {
                //Cany removed... Didn't work so well

                Imgproc.threshold(gray, gray0, (l + 1) * 255 / N, 255, Imgproc.THRESH_BINARY);
                Imgproc.Canny(gray0, gray0, 10, 100);
                List<MatOfPoint> contours=new ArrayList<MatOfPoint>();
                // find contours and store them all as a list
                Imgproc.findContours(gray0, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
                MatOfPoint approx = new MatOfPoint();
                // test each contour
                for( int i = 0; i < contours.size(); i++ )
                {
                    approx = approxPolyDP(contours.get(i),  Imgproc.arcLength(new MatOfPoint2f(contours.get(i).toArray()), true)*0.02, true);

                    if( approx.toArray().length == 4 &&
                            Math.abs(Imgproc.contourArea(approx)) > 1000 &&
                            Imgproc.isContourConvex(approx)
                            && (contours.get(i).width() < image.width() - 10 || contours.get(i).height() < image.height() - 10))
                    {
                        double maxCosine = 0;
                        for( int j = 2; j < 5; j++ )
                        {
                            // find the maximum cosine of the angle between joint edges
                            double cosine = Math.abs(angle(approx.toArray()[j%4], approx.toArray()[j-2], approx.toArray()[j-1]));
                            maxCosine = Math.max(maxCosine, cosine);
                        }
                        if( maxCosine < 0.3 && (squares.isEmpty() || Imgproc.contourArea(approx) > lastArea)) {
                            lastArea = Imgproc.contourArea(approx);
                            if (!squares.isEmpty())
                                squares.remove(0);
                            squares.add(0, approx);
                        }
                    }
                }
            }
        }
        Log.d("SQUARE", "SQUARE NB : " + squares.size());
        return squares;
    }

    private static void extractChannel(Mat source, Mat out, int channelNum) {
        List<Mat> sourceChannels=new ArrayList<Mat>();
        List<Mat> outChannel=new ArrayList<Mat>();

        Core.split(source, sourceChannels);
        outChannel.add(new Mat(sourceChannels.get(0).size(), sourceChannels.get(0).type()));
        Core.mixChannels(sourceChannels, outChannel, new MatOfInt(channelNum, 0));
        Core.merge(outChannel, out);
    }

    private static MatOfPoint approxPolyDP(MatOfPoint curve, double epsilon, boolean closed) {
        MatOfPoint2f tempMat=new MatOfPoint2f();
        Imgproc.approxPolyDP(new MatOfPoint2f(curve.toArray()), tempMat, epsilon, closed);

        return new MatOfPoint(tempMat.toArray());
    }

    private static double angle( Point pt1, Point pt2, Point pt0 ) {
        double dx1 = pt1.x - pt0.x;
        double dy1 = pt1.y - pt0.y;
        double dx2 = pt2.x - pt0.x;
        double dy2 = pt2.y - pt0.y;
        return (dx1*dx2 + dy1*dy2)/Math.sqrt((dx1*dx1 + dy1*dy1)*(dx2*dx2 + dy2*dy2) + 1e-10);
    }

    public static Mat testContour(Mat source) {
        Mat modifiedImage = new Mat(source.width(), source.height(), source.type());
        Imgproc.cvtColor(source, modifiedImage, Imgproc.COLOR_BGR2GRAY);
        Imgproc.blur(modifiedImage, modifiedImage, new Size(3, 3));
        Imgproc.Canny(modifiedImage, modifiedImage, 10, 100);
        Imgproc.blur(modifiedImage, modifiedImage, new Size(2, 2));

        return modifiedImage;
    }

    public static List<MatOfPoint> myFindShape(Mat source) {
        List<MatOfPoint> detectedShapes = new ArrayList<>();
        List<MatOfPoint> squares = new ArrayList<>();
        double lastArea = -1;

//        Mat modifiedImage = new Mat(source.width(), source.height(), source.type());
//        Imgproc.cvtColor(source, modifiedImage, Imgproc.COLOR_BGR2GRAY);
//        Imgproc.blur(modifiedImage, modifiedImage, new Size(3, 3));
//        Imgproc.Canny(modifiedImage, modifiedImage, 10, 100);

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
                    if (!squares.isEmpty()) {
                        squares.remove(0);
                    }
                    squares.add(0, approx);
                }
            }
        }

        if (squares.size() < 1) {
            squares = findSquares(source);
        }

        return squares;
    }
}
