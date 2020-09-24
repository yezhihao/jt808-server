package org.yzh.web.service;

import org.yzh.protocol.t808.T0100;

public interface DeviceService {

    String register(T0100 request);

    boolean authentication(String token);

}