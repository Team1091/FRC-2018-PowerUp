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

        BufferedImage inputImage = new BufferedImage (1920,1080,BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < inputImage.getWidth(); x++) {
            for (int y = 0; y < inputImage.getHeight(); y++) {
                float hue = (float)x/(float)inputImage.getWidth(); //hue
                float saturation= 1.0f; //saturation
                float brightness= (float)y/(float)inputImage.getHeight(); //brightness
                inputImage.setRGB(x,y,Color.getHSBColor(hue, saturation, brightness).getRGB());


            }
        }
        BufferedImage outputImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(),
                BufferedImage.TYPE_INT_RGB);


        for (int x = 0; x < inputImage.getWidth(); x++) {
            for (int y = 0; y < inputImage.getHeight(); y++) {
                Color color = new Color(inputImage.getRGB(x, y));
                float green = (float) color.getGreen()/255f;
                float red = (float) color.getRed()/255f;
                float blue = (float) color.getBlue()/255f+0.001f;

                float yellow = Math.min(red, green)/blue; // TODO: find a function to find yellowness
                //System.out.println(yellow);
                if (yellow > 100) { //was 10
                    outputImage.setRGB(x, y, new Color(0, 255, 0).getRGB());
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
