package org.yzh.framework.session;

import org.yzh.framework.message.SyncFuture;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum MessageManager {

    INSTANCE;

    private Map<String, SyncFuture> map = new ConcurrentHashMap<>();


    public SyncFuture receive(String key) {
        SyncFuture future = new SyncFuture();
        map.put(key, future);
        return future;
    }

    public void remove(String key) {
        map.remove(key);
    }

    public void put(String key, Object value) {
        SyncFuture syncFuture = map.get(key);
        if (syncFuture == null)
            return;
        syncFuture.setResponse(value);
    }

}
