package com.team1091.vision;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BlurTest {

    @Test
    public void testBlurring() throws IOException {

        BufferedImage inputImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = inputImage.createGraphics();

        // Red background
        graphics.setPaint(Color.RED);
        graphics.fillRect(0, 0, inputImage.getWidth(), inputImage.getHeight());

        // With a blue square
        graphics.setPaint(Color.BLUE);
        graphics.fillRect(20, 20, inputImage.getWidth() - 20, inputImage.getHeight() - 20);

        // blur the input image
        BufferedImage outputImage = VisionStandalone.boxBlur(inputImage, 5);

        File testOut = new File("build/testOut");
        testOut.mkdir();

        ImageIO.write(outputImage, "png", new File(testOut, "blurred.png"));

    }
}
