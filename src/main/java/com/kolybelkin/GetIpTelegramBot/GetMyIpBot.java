package com.kolybelkin.GetIpTelegramBot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class GetMyIpBot {
  private final static Logger LOGGER = LoggerFactory.getLogger(GetMyIpBot.class);

  @Value("${apiToken}")
  private String apiToken;

  @Autowired
  private IpService ipService;

  @PostConstruct
  public void runBot() {
    TelegramBot bot = new TelegramBot(apiToken);
    bot.setUpdatesListener(updates -> {
      updates.forEach(update -> {
        long chatId = update.message().chat().id();
        String myIp = ipService.getMyIp();
        if (myIp != null) {
          bot.execute(new SendMessage(chatId,
              String.format("IP address for machine, where the bot is running - %s", myIp)));
          LOGGER.debug("User requested my IP and got the following response - {}", myIp);
        } else {
          LOGGER.error("Error while getting my IP address.");
          bot.execute(new SendMessage(chatId, "My IP address is unavailable"));
        }
      });
      return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }, e -> LOGGER.error(e.getMessage(), e));
  }
}
