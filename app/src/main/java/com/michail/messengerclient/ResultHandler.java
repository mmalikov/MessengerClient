package com.michail.messengerclient;

import org.drinkless.td.libcore.telegram.Client;
import org.drinkless.td.libcore.telegram.TdApi;

/**
 * Created by mmalykov on 8/12/15.
 */
public abstract class ResultHandler implements Client.ResultHandler {

    @Override
    public void onResult(TdApi.TLObject object) {
        if (object.getConstructor() == TdApi.Error.CONSTRUCTOR) {
            this.onResponse(null, (TdApi.Error) object);
        } else {
            this.onResponse(object, null);
        }
    }

    public abstract void onResponse(TdApi.TLObject object, TdApi.Error error);
}
