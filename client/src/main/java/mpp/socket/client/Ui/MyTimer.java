package mpp.socket.client.Ui;

import java.util.Map;

public class MyTimer {
    private int period;
    private Map<String, String> results;
    private Thread thread;

    public MyTimer(int period, Map<String, String> results) {
        this.period = period;
        this.results = results;
        this.thread = new Thread(() -> {
            try {
                Thread.sleep(this.period);
                results.entrySet().stream().map(entry -> entry.getValue())
                        .map(result -> result.replaceAll(";", "\n"))
                        .forEach(System.out::println);
                results.clear();
            } catch (InterruptedException e) {

            }
        });
    }

    public void start() {
        this.thread.start();
    }

    public void stop() {
        this.thread.interrupt();
    }
}
