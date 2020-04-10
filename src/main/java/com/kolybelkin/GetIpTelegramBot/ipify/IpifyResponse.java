package com.kolybelkin.GetIpTelegramBot.ipify;

import java.io.Serializable;

public class IpifyResponse implements Serializable {
    private String ip;

    public String getIp() {
        return ip;
    }
}
