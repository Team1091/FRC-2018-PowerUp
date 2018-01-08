package com.team1091.vision;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This is used to locate cubes on the floor
 */
public class CubeFinderTests {


    @Test
    public void findCubeCenter() throws IOException {

        BufferedImage test = ImageIO.read(getClass().getClassLoader().getResourceAsStream("powercube.jpg"));

        TargetingOutput output = VisionStandalone.process(test);

        BufferedImage out = output.drawOntoImage(test);

        // TODO: check that the center is where we think it should be
//        float center = output.getCenter();
//        System.out.println(center);
//        assert center < 0.01;
//        assert center > -0.01;

        File testOut = new File("build/testOut");
        testOut.mkdir();

        ImageIO.write(out, "jpg", new File(testOut, "powercube_processed.jpg"));
    }
}
