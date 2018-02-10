package frc.team1091.robot.systems;

import com.google.gson.Gson;
import frc.team1091.robot.ImageInfo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.URL;

/**
 * This communicates with the camera server
 */
public class VisionSystem {

    private ImageInfo imageInfo = new ImageInfo();
    private Gson gson = new Gson();


    public void init() {
        Runnable visionUpdater = () -> {
            while (true) {
                try {

                    // URL visionURL = new URL("http://sif.local:5605"); // We should try to get mDNS working
                    URL visionURL = new URL("http://10.10.91.20:5805/"); // competition we hard code ip
                    // URL visionURL = new URL("http://169.254.71.106:5805/"); // For Testing

                    BufferedReader in = new BufferedReader(new InputStreamReader(visionURL.openStream()));

                    String inputLine = in.readLine();
                    this.imageInfo = gson.fromJson(inputLine, ImageInfo.class);
                    this.imageInfo.lastUpdated = System.currentTimeMillis();

                    in.close();
                    Thread.sleep(100); // wait a 10th of a second before we ask again
                } catch (ConnectException e) {
                    // System.out.println("No connection");
                } catch (Exception e) {
                    // e.printStackTrace();
                }
            }
        };
        new Thread(visionUpdater).start();
    }

    public double getRedCenter() {
        return imageInfo.red;
    }

    public double getBlueCenter() {
        return imageInfo.blue;
    }

    public double getYellowCenter() {
        return imageInfo.yellow;
    }

    public double getRedDistance() {
        return imageInfo.redDistance;
    }

    public double getBlueDistance() {
        return imageInfo.blueDistance;
    }

    public double getYellowDistance() {
        return imageInfo.yellowDistance;
    }
}
