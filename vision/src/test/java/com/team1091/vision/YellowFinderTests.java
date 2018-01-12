package com.team1091.vision;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class YellowFinderTests {

    @Test
    public void yellowTest() throws IOException {

        BufferedImage inputImage = ImageIO.read(getClass().getClassLoader()
                .getResourceAsStream("phone/IMG_20180108_191234.jpg"));


        BufferedImage outputImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(),
                BufferedImage.TYPE_INT_RGB);


        for (int x = 0; x < inputImage.getWidth(); x++) {
            for (int y = 0; y < inputImage.getHeight(); y++) {
                Color color = new Color(inputImage.getRGB(x, y));
                int green = color.getGreen();
                int red = color.getRed();
                int blue = color.getBlue();

                int yellow = 0; // TODO: find a function to find yellowness

                if (yellow > 10) {
                    outputImage.setRGB(x, y, new Color(0, yellow, 0).getRGB());
                } else {
                    outputImage.setRGB(x, y, color.getRGB());
                }
            }
        }

        File testOut = new File("build/testOut");
        testOut.mkdir();

        ImageIO.write(outputImage, "jpg", new File(testOut, "powercube_processed.jpg"));
    }
}
