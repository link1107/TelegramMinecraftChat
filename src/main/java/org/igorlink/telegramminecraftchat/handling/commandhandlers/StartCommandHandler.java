package org.igorlink.telegramminecraftchat.handling.commandhandlers;

import org.igorlink.telegramminecraftchat.telegrambot.Bot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.concurrent.Callable;

public class StartCommandHandler extends AbstractCommandRunnable {
    public StartCommandHandler(Bot bot) {
        super(bot);
    }

    @Override
    public void run() {
        try {
            bot.execute(SendMessage.builder()
                    .chatId(bot.getAdminId())
                    .text("Ооооо! А я и не думал!")
                    .build()
            );
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
