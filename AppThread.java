import javafx.application.Platform;

import java.util.concurrent.CountDownLatch;

public class AppThread {
    public static void runAndWait(Runnable action) {
        if (action == null)
            throw new NullPointerException("action");

        if (Platform.isFxApplicationThread()) {
            action.run();
            return;
        }

        final CountDownLatch doneLatch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                action.run();
            } finally {
                doneLatch.countDown();
            }
        });

        try {
            doneLatch.await();
        } catch (InterruptedException e) {
        }
    }
}
