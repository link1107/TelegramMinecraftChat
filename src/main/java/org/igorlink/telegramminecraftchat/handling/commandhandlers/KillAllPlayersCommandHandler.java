package org.igorlink.telegramminecraftchat.handling.commandhandlers;

import org.igorlink.telegramminecraftchat.telegrambot.Bot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class KillAllPlayersCommandHandler extends AbstractCommandRunnable{
    public KillAllPlayersCommandHandler(Bot bot) {
        super(bot);
    }

    @Override
    public void run() {
        try {
            bot.execute(SendMessage.builder()
                    .chatId(bot.getAdminId())
                    .text("БАМ БУМ Я ВСЕХ УБИЛ!")
                    .build()
            );
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
