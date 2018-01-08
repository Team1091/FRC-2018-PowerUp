package com.team1091.vision;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class TargetingOutput {
    public static final DecimalFormat df = new DecimalFormat("#.0");

    public int imageWidth;
    public int imageHeight;

    public int xcenter;
    public int ycenter;

    int width;

    public float distance;
    public BufferedImage processedImage;
    public int rightX;
    public int leftX;
    public int calcXCenter;

    /**
     * This draws debug info onto the image before it's displayed.
     *
     * @param outputImage
     * @return
     */
    public BufferedImage drawOntoImage(BufferedImage outputImage) {

        Graphics2D g = outputImage.createGraphics();
        g.setColor(Color.RED);

        g.drawLine(xcenter, ycenter - 10, xcenter, ycenter + 10);
        g.drawLine(xcenter, ycenter, rightX, ycenter);
        g.drawLine(xcenter, ycenter, leftX, ycenter);
        g.drawLine(rightX, ycenter - 8, rightX, ycenter + 8);
        g.drawLine(leftX, ycenter - 8, leftX, ycenter + 8);

        g.setColor(Color.cyan);

        g.drawLine(calcXCenter, ycenter + 15, calcXCenter, ycenter - 15);

        // width labels, px and % screen width
        g.drawString(width + " px", xcenter, ycenter - 25);
        g.drawString(df.format(distance) + " in", xcenter, ycenter + 35);

        g.drawLine(outputImage.getWidth() / 2, ycenter + 20, calcXCenter, ycenter + 20);


        return outputImage;
    }

    /**
     * Get the center as a fraction of total image width
     *
     * @return float from -0.5 to 0.5
     */
    public float getCenter() {
        return ((float) calcXCenter / (float) imageWidth) - 0.5f;
    }
}
