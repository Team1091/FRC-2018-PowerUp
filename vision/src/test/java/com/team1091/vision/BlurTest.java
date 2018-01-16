package com.team1091.vision;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BlurTest {

    @Test
    public void testBluring() throws IOException {

        BufferedImage inputImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = inputImage.createGraphics();

        // Red background
        graphics.setPaint(Color.RED);
        graphics.fillRect(0, 0, inputImage.getWidth(), inputImage.getHeight());

        // With a blue square
        graphics.setPaint(Color.BLUE);
        graphics.fillRect(20, 20, inputImage.getWidth() - 20, inputImage.getHeight() - 20);

        // blur the input image
        BufferedImage outputImage = boxBlur(inputImage, 5);


        File testOut = new File("build/testOut");
        testOut.mkdir();

        ImageIO.write(outputImage, "png", new File(testOut, "blurred.png"));

    }


    // https://en.wikipedia.org/wiki/Box_blur
    public BufferedImage boxBlur(BufferedImage inputImage, int radius) {

        BufferedImage outputImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(),
                BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < inputImage.getWidth(); x++) {
            for (int y = 0; y < inputImage.getHeight(); y++) {

                int pixel=0;
                int red=0;
                int green=0;
                int blue=0;

                for (int ix = x - radius; ix <= x + radius; ix++) {
                    for (int iy = y - radius; iy <= y + radius; iy++) {

                        if (ix<0||iy<0||ix>=inputImage.getWidth()||iy>=inputImage.getHeight())
                            continue;
                        //TODO: blur
                        Color rgb = new Color(inputImage.getRGB(ix, iy));
                        red+=Math.pow(rgb.getRed(),2);
                        green+=Math.pow(rgb.getGreen(),2);
                        blue+=Math.pow(rgb.getBlue(),2);
                        pixel++;

                    }
                }
                outputImage.setRGB(x, y, new Color(
                        (int)Math.sqrt(red/pixel),
                        (int)Math.sqrt(green/pixel),
                        (int)Math.sqrt(blue/pixel)).getRGB());


            }
        }

        return outputImage;
    }


}
