package com.michail.messengerclient;

import android.app.Application;

import org.drinkless.td.libcore.telegram.Client;
import org.drinkless.td.libcore.telegram.TG;
import org.drinkless.td.libcore.telegram.TdApi;

import java.io.File;

/**
 * Created by mmalykov on 8/12/15.
 */
public class MessengerApplication extends Application {

    private Client mClient;

    @Override
    public void onCreate() {
        super.onCreate();

        mClient = initTG();
    }

    public Client getClient() {
        return mClient;
    }

    private Client initTG() {
        File file = getApplicationContext().getDatabasePath("tl.db");
        file.mkdirs();

        String dbPath = file.getPath();
        TG.setDir(dbPath);

        TG.setUpdatesHandler(new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                android.util.Log.e("UPDATES_HANDLER", "Object = " + object.toString());
            }
        });

        return TG.getClientInstance();
    }
}
