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

        g.drawLine(xCenter, yCenter - 10, xCenter, yCenter + 10);
        g.drawLine(xCenter, yCenter, rightX, yCenter);
        g.drawLine(xCenter, yCenter, leftX, yCenter);
        g.drawLine(rightX, yCenter - 8, rightX, yCenter + 8);
        g.drawLine(leftX, yCenter - 8, leftX, yCenter + 8);

        g.setColor(Color.cyan);

        g.drawLine(calcXCenter, yCenter + 15, calcXCenter, yCenter - 15);

        // width labels, px and % screen width
        g.drawString(width + " px", xCenter, yCenter - 25);
        g.drawString(df.format(distance) + " in", xCenter, yCenter + 35);

        g.drawLine(outputImage.getWidth() / 2, yCenter + 20, calcXCenter, yCenter + 20);


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
