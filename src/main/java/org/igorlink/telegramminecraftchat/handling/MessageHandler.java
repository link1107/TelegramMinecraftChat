package org.igorlink.telegramminecraftchat.handling;

import org.igorlink.telegramminecraftchat.handling.commandhandlers.AbstractCommandRunnable;
import org.igorlink.telegramminecraftchat.handling.commandhandlers.KillAllPlayersCommandHandler;
import org.igorlink.telegramminecraftchat.handling.commandhandlers.StartCommandHandler;
import org.igorlink.telegramminecraftchat.telegrambot.Bot;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;

import static org.igorlink.telegramminecraftchat.service.BotCommands.KILL_ALL_PLAYERS_COMMAND;
import static org.igorlink.telegramminecraftchat.service.BotCommands.START_COMMAND;

public class MessageHandler {

    public static void handleMessage(Message message, Bot bot) {
        if (!message.hasText()) {
            return;
        }

        String messageText = message.getText();

        // Если первый символ - это слеш, значит мы получили команду
        AbstractCommandRunnable commandRunnable = null;
        if (messageText.charAt(0) == '/') {
            switch (messageText.substring(1)) {
                case START_COMMAND -> new StartCommandHandler(bot).run();
                case KILL_ALL_PLAYERS_COMMAND -> new KillAllPlayersCommandHandler(bot).run();
            }
        }

    }

}
