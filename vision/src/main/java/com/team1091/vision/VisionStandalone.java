package com.team1091.vision;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.ds.ipcam.IpCamDeviceRegistry;
import com.github.sarxos.webcam.ds.ipcam.IpCamDriver;
import com.github.sarxos.webcam.ds.ipcam.IpCamMode;
import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import static spark.Spark.get;
import static spark.Spark.port;

public class VisionStandalone {

    private static ImageInfo imageInfo = new ImageInfo();

    private static Gson gson = new Gson();

    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            Webcam.setDriver(new IpCamDriver());
            IpCamDeviceRegistry.register("RoboRioCam", "http://roborio-1091-frc.local:1181/stream.mjpg", IpCamMode.PUSH);
        }

        List<Webcam> webcams = Webcam.getWebcams();
        Webcam webcam = webcams.get(webcams.size() - 1);
        WebcamPanel panel = new WebcamPanel(webcam);

        panel.setPainter(new WebcamPanel.Painter() {
            @Override
            public void paintPanel(WebcamPanel panel, Graphics2D g2) {

            }

            @Override
            public void paintImage(WebcamPanel panel, BufferedImage image, Graphics2D g2) {

                TargetingOutput targetingOutput = process(image);

                // pull out results we care about, let web server serve them as quick as possible
                imageInfo.yellow = targetingOutput.getYellowCenter();
                imageInfo.yellowDistance = targetingOutput.getYellowDistance();

                imageInfo.red = targetingOutput.getRedCenter();
                imageInfo.redDistance = targetingOutput.getRedDistance();

                imageInfo.blue = targetingOutput.getBlueCenter();
                imageInfo.blueDistance = targetingOutput.getBlueDistance();

                // Draw our results onto the image, so that the driver can see if the autonomous code is tracking
                BufferedImage outImage = targetingOutput.drawOntoImage(targetingOutput.processedImage);

                writeToPanel(panel, g2, outImage);

            }

            /**
             * Writes an image onto the panel, and deals with stretching it while keeping aspect ratio
             * @param panel
             * @param g2
             * @param outImage
             */
            private void writeToPanel(WebcamPanel panel, Graphics2D g2, BufferedImage outImage) {

                int imageX = outImage.getWidth();
                int imageY = outImage.getHeight();

                float imageAspectRatio = (float) imageX / (float) imageY;

                int panelX = panel.getWidth();
                int panelY = panel.getHeight();

                float screenAspectRatio = (float) panelX / (float) panelY;

                if (imageAspectRatio < screenAspectRatio) {
                    // wide screen - y to the max
                    int scaledImageX = (int) (panelY * imageAspectRatio);
                    int scaledImageY = panelY;
                    g2.drawImage(outImage, (panelX - scaledImageX) / 2, 0, scaledImageX, scaledImageY, null);

                } else {
                    // tall screen - x to the max
                    int scaledImageX = panelX;
                    int scaledImageY = (int) (panelX / imageAspectRatio);
                    g2.drawImage(outImage, 0, (panelY - scaledImageY) / 2, scaledImageX, scaledImageY, null);
                }
            }
        });

        JFrame window = new JFrame("Test webcam panel");
        window.add(panel);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.pack();
        window.setVisible(true);

        // We host a small webserver so that the robot can ask us where the centerYellow is and how far it is.
        port(5805);
        get("/", (req, res) -> gson.toJson(imageInfo));
    }

//    public static float getDistance(int widthInPixels) {
//        // TODO: deal with different camera pixel counts
//        return (float) (104.4742249664 * Math.exp(-0.0484408778 * (double) widthInPixels));
//    }

    public static TargetingOutput process(BufferedImage inputImage) {

        BufferedImage outputImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(),
                BufferedImage.TYPE_INT_RGB);

        int radius = 1;
        long xSumY = 0;
        long ySumY = 0;
        int totalCountY = 0;

        long xSumR = 0;
        long ySumR = 0;
        int totalCountR = 0;

        long xSumB = 0;
        long ySumB = 0;
        int totalCountB = 0;

        for (int x = 0; x < inputImage.getWidth(); x++) {
            for (int y = 0; y < inputImage.getHeight(); y++) {

                int pixel = 0;
                int red = 0;
                int green = 0;
                int blue = 0;

                for (int ix = x - radius; ix <= x + radius; ix++) {
                    for (int iy = y - radius; iy <= y + radius; iy++) {

                        if (ix < 0 || iy < 0 || ix >= inputImage.getWidth() || iy >= inputImage.getHeight())
                            continue;

                        Color rgb = new Color(inputImage.getRGB(ix, iy));
                        red += Math.pow(rgb.getRed(), 2);
                        green += Math.pow(rgb.getGreen(), 2);
                        blue += Math.pow(rgb.getBlue(), 2);
                        pixel++;

                    }
                }

                double r = Math.sqrt(red / pixel) / 255.0;
                double g = Math.sqrt(green / pixel) / 255.0;
                double b = Math.sqrt(blue / pixel) / 255.0;

                double yellow = Math.min(r, g) * (1 - b); // TODO: find a function to find yellowness

                if (yellow > .35) { //was 10
                    outputImage.setRGB(x, y, Color.YELLOW.getRGB());
                    xSumY += x;
                    ySumY += y;
                    totalCountY++;

                } else if (r > 0.5 && r > b + 0.18 && r > g + 0.18) {
                    // its Red
                    outputImage.setRGB(x, y, Color.RED.getRGB());
                    xSumR += x;
                    ySumR += y;
                    totalCountR++;

                } else if (b > 0.5 && b > r + 0.15 && b > g + 0.15) {
                    outputImage.setRGB(x, y, Color.BLUE.getRGB());
                    // its blue
                    xSumB += x;
                    ySumB += y;
                    totalCountB++;

                } else {
                    outputImage.setRGB(x, y, inputImage.getRGB(x, y));
                }

            }
        }

        int xCenterY;
        int yCenterY;
        if (totalCountY == 0) {
            xCenterY = inputImage.getWidth() / 2;
            yCenterY = inputImage.getHeight() / 2;
        } else {
            xCenterY = (int) (xSumY / totalCountY);
            yCenterY = (int) (ySumY / totalCountY);
        }

        int xCenterR;
        int yCenterR;
        if (totalCountR == 0) {
            xCenterR = inputImage.getWidth() / 2;
            yCenterR = inputImage.getHeight() / 2;
        } else {
            xCenterR = (int) (xSumR / totalCountR);
            yCenterR = (int) (ySumR / totalCountR);
        }

        int xCenterB;
        int yCenterB;
        if (totalCountB == 0) {
            xCenterB = inputImage.getWidth() / 2;
            yCenterB = inputImage.getHeight() / 2;
        } else {
            xCenterB = (int) (xSumB / totalCountB);
            yCenterB = (int) (ySumB / totalCountB);
        }

        TargetingOutput targetingOutput = new TargetingOutput();
        targetingOutput.imageWidth = inputImage.getWidth();
        targetingOutput.imageHeight = inputImage.getHeight();

        targetingOutput.xCenterYellow = xCenterY;
        targetingOutput.yCenterYellow = yCenterY;

        targetingOutput.xCenterRed = xCenterR;
        targetingOutput.yCenterRed = yCenterR;

        targetingOutput.xCenterBlue = xCenterB;
        targetingOutput.yCenterBlue = yCenterB;

        targetingOutput.processedImage = outputImage;
        return targetingOutput;
    }

    public static BufferedImage boxBlur(BufferedImage inputImage, int radius) {

        BufferedImage outputImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(),
                BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < inputImage.getWidth(); x++) {
            for (int y = 0; y < inputImage.getHeight(); y++) {

                int pixel = 0;
                int red = 0;
                int green = 0;
                int blue = 0;

                for (int ix = x - radius; ix <= x + radius; ix++) {
                    for (int iy = y - radius; iy <= y + radius; iy++) {

                        if (ix < 0 || iy < 0 || ix >= inputImage.getWidth() || iy >= inputImage.getHeight())
                            continue;
                        //TODO: blur
                        Color rgb = new Color(inputImage.getRGB(ix, iy));
                        red += Math.pow(rgb.getRed(), 2);
                        green += Math.pow(rgb.getGreen(), 2);
                        blue += Math.pow(rgb.getBlue(), 2);
                        pixel++;

                    }
                }
                outputImage.setRGB(x, y, new Color(
                        (int) Math.sqrt(red / pixel),
                        (int) Math.sqrt(green / pixel),
                        (int) Math.sqrt(blue / pixel)).getRGB());

            }
        }

        return outputImage;
    }

}