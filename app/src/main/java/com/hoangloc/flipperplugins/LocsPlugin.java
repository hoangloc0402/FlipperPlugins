package com.hoangloc.flipperplugins;

import com.facebook.sonar.core.SonarConnection;
import com.facebook.sonar.core.SonarObject;
import com.facebook.sonar.core.SonarPlugin;
import com.facebook.sonar.core.SonarReceiver;
import com.facebook.sonar.core.SonarResponder;

import java.util.Random;

public class LocsPlugin implements SonarPlugin {
    public SonarConnection mConnection;
    Random random = new Random();
    Thread dataPusher;

    public LocsPlugin() {
      //  super();
        dataPusher = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (mConnection == null) continue;
                    System.out.println("Sentttttttttttttttttttttttttt");
                    mConnection.send(
                            "randomDataUpdate",
                            new SonarObject.Builder()
                                    .put("time", System.currentTimeMillis())
                                    .put("time2", System.currentTimeMillis())
                                    .put("time3", System.currentTimeMillis())
                                    .put("time4", System.currentTimeMillis())
                                    .build());
                }
            }
        });
        dataPusher.start();
    }

    @Override
    protected void finalize() throws Throwable {
        dataPusher.join();
        super.finalize();
    }

    @Override
    public String getId() {
        return "locsplugin";
    }

    @Override
    public void onConnect(SonarConnection connection) {
        System.out.println("Connecttttttttttt");
        mConnection = connection;
//
//        connection.receive("getData", new SonarReceiver() {
//            @Override
//            public void onReceive(SonarObject params, SonarResponder responder) throws Exception {
//                responder.success(
//                        new SonarObject.Builder()
//                                .put("time", System.currentTimeMillis())
//                                .build());
//            }
//        });
    }

    @Override
    public void onDisconnect() throws Exception {
        mConnection = null;
        System.out.println("Disconnectttttttt");
    }
}
