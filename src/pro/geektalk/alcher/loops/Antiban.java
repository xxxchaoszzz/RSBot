package pro.geektalk.alcher.loops;

import org.powerbot.core.script.job.LoopTask;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import pro.geektalk.alcher.misc.Methods;

public class Antiban extends LoopTask {

    @Override
    public int loop() {
        final int random = Random.nextInt(0, 1000);
        if (random < 5 || random > 995) {
            Methods.s("Camera stuff");
            switch (random) {
                case 1:
                    Camera.setAngle(Random.nextInt(400, 1200));
                    Camera.setPitch(Random.nextInt(5, 35));
                    break;
                case 2:
                    Camera.setAngle(Random.nextInt(500, 2000));
                    Camera.setPitch(Random.nextInt(60, 90));
                    break;
                case 3:
                    break;
                case 4:
                    Camera.setPitch(Random.nextInt(25, 70));
                    break;
                case 995:
                    Camera.setAngle(Random.nextInt(500, 700));
                    break;
                case 996:
                    break;
                case 997:
                    Camera.setAngle(Random.nextInt(1200, 2000));
                    Camera.setPitch(Random.nextInt(5, 35));
                    break;
                case 999:
                default:
                    if (Random.nextInt(0, 11) < 8) {
                        Camera.setAngle(Random.nextInt(500, 2000));
                        Camera.setPitch(Random.nextInt(60, 90));
                    }
                    break;
            }
        }
        return 3000;
    }

}
