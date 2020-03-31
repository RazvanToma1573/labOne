package mpp.socket.client;

import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class MyTimerTask extends TimerTask {

    private ConcurrentHashMap<String, Future<String>> results;

    public MyTimerTask(ConcurrentHashMap<String, Future<String>> r) {
        this.results = r;
    }

    @Override
    public void run() {
        results.forEach((key, value) -> {
            if (value.isDone()) {
                System.out.println("Command: " + key);
                System.out.println("Result: ");
                try {
                    String resultToBeParsed = value.get();

                    String[] parsed = resultToBeParsed.split(";");
                    System.out.println();

                    String[] parsedKey = key.split(" ");

                    if (parsedKey[0].equals("16") || parsedKey[0].equals("8")){
                        for (int i = 0; i < parsed.length; i++) {
                            String[] parsedAgain = parsed[i].split("-");
                            for (int j = 0; j < parsedAgain.length; j++) {
                                System.out.println(parsedAgain[j]);
                            }
                        }
                    } else {
                        for (int i = 0; i < parsed.length; i++) {
                            System.out.println(parsed[i]);
                        }
                    }
                } catch (InterruptedException | ExecutionException e) {
                    System.out.println(e.getMessage());
                }
                this.results.remove(key);
            }
        });
    }
}
