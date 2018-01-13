package com.team1091.vision;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class TargetingOutput {
    private static final DecimalFormat df = new DecimalFormat("#.0");

    public int imageWidth;
    public int imageHeight;

    public int xCenter;
    public int yCenter;

    int width;


    public BufferedImage processedImage;
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
        g.drawLine(xCenter, yCenter - 10, xCenter, yCenter + 10);

        // width labels, px and % screen width
        g.drawString(width + " px", xCenter, yCenter - 25);

        //g.drawLine(outputImage.getWidth() / 2, yCenter + 20, calcXCenter, yCenter + 20);


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
