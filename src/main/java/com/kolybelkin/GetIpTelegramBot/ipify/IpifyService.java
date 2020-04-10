package com.kolybelkin.GetIpTelegramBot.ipify;

import com.kolybelkin.GetIpTelegramBot.GetMyIpBot;
import com.kolybelkin.GetIpTelegramBot.IpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class IpifyService implements IpService {
  private final static Logger LOGGER = LoggerFactory.getLogger(GetMyIpBot.class);
  private static final String MY_IP_SERVICE_API_URL = "https://api.ipify.org?format=json";
  @Autowired
  private RestTemplate restTemplate;

  public String getMyIp() {
    IpifyResponse result = restTemplate.getForObject(MY_IP_SERVICE_API_URL, IpifyResponse.class);
    if (result != null) {
      return result.getIp();
    } else {
      LOGGER.error("Could not get my ip using service API '{}'", MY_IP_SERVICE_API_URL);
      return null;
    }
  }
}
