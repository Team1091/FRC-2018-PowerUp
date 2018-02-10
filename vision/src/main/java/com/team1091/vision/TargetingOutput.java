package com.team1091.vision;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class TargetingOutput {
    private static final DecimalFormat df = new DecimalFormat("#.0");

    public int imageWidth;
    public int imageHeight;

    public int xCenterYellow;
    public int yCenterYellow;

    public int xCenterRed;
    public int yCenterRed;

    public int xCenterBlue;
    public int yCenterBlue;

    public BufferedImage processedImage;

    /**
     * This draws debug info onto the image before it's displayed.
     *
     * @param outputImage
     * @return
     */
    public BufferedImage drawOntoImage(BufferedImage outputImage) {

        Graphics2D g = outputImage.createGraphics();

        g.setColor(Color.YELLOW);
        g.drawLine(xCenterYellow, yCenterYellow - 10, xCenterYellow, yCenterYellow + 10);

        g.setColor(Color.RED);
        g.drawLine(xCenterRed, yCenterRed - 10, xCenterRed, yCenterRed + 10);

        g.setColor(Color.BLUE);
        g.drawLine(xCenterBlue, yCenterBlue - 10, xCenterBlue, yCenterBlue + 10);

        // width labels, px and % screen width
        //g.drawString(width + " px", xCenterYellow, yCenterYellow - 25);

        //g.drawLine(outputImage.getWidth() / 2, yCenterYellow + 20, calcXCenter, yCenterYellow + 20);


        return outputImage;
    }

    /**
     * Get the center as a fraction of total image width
     *
     * @return float from -0.5 to 0.5
     */
    public double getYellowCenter() {
        return ((double) xCenterYellow / (double) imageWidth) - 0.5;
    }

    public double getRedCenter() {
        return ((double) xCenterRed / (double) imageWidth) - 0.5;
    }

    public double getBlueCenter() {
        return ((double) xCenterBlue / (double) imageWidth) - 0.5;
    }

    public double getYellowDistance() {
        return 0;
    }

    public double getRedDistance() {
        return 0;
    }

    public double getBlueDistance() {
        return 0;
    }
}
