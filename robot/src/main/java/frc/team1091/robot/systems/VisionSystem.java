package frc.team1091.robot.systems;

import com.google.gson.Gson;
import com.team1091.math.Rectangle;
import com.team1091.math.Vec2;
import com.team1091.planning.Obstacle;
import frc.team1091.robot.ImageInfo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * This communicates with the camera server
 */
public class VisionSystem {

    private ImageInfo imageInfo = new ImageInfo();
    private Gson gson = new Gson();
    private ArrayList<Integer> pathMap = new ArrayList<>();


    public void init() {
        Runnable visionUpdater = () -> {
            while (true) {
                try {

                    // URL visionURL = new URL("http://sif.local:5605"); // We should try to get mDNS working
                    URL visionURL = new URL("http://10.10.91.5:5805/"); // competition we hard code ip
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
        Runnable pathFinder = () -> {
            while (true) {
                try {
                    URL rgbURL = new URL("http://10.10.91.5:5805/pathing");
                    BufferedReader brightness = new BufferedReader(new InputStreamReader(rgbURL.openStream()));
                    String inputValue = brightness.readLine();
                    this.pathMap = gson.fromJson(inputValue, ArrayList.class);
                    brightness.close();
                    Thread.sleep(100);
                } catch (Exception e) {

                }
            }
        };
        new Thread(pathFinder).start();
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

    public List<Obstacle> getObstacles() {
        ArrayList<Obstacle> result = new ArrayList<Obstacle>();

        for (int i = 0; i < pathMap.size(); i++) {
            int val = pathMap.get(i);
            int x = i % 27;
            int y = i / 30;
            if (val < 10) {
                result.add(
                        new Rectangle(Vec2.Companion.get(x, y), Vec2.Companion.get(x, y)));
            }
        }
        return result;
    }
}
