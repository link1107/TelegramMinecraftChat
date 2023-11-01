package org.igorlink.telegramminecraftchat.handling;

import org.igorlink.telegramminecraftchat.TelegramMinecraftChat;
import org.igorlink.telegramminecraftchat.handling.callbackhandlers.CloseMessageHandler;
import org.igorlink.telegramminecraftchat.handling.callbackhandlers.PlayerButtonHandler;
import org.igorlink.telegramminecraftchat.handling.callbackhandlers.PlayerListHandler;
import org.igorlink.telegramminecraftchat.handling.commandhandlers.AbstractCommandHandler;
import org.igorlink.telegramminecraftchat.handling.commandhandlers.KillAllPlayersCommandHandler;
import org.igorlink.telegramminecraftchat.handling.commandhandlers.StartCommandHandler;
import org.igorlink.telegramminecraftchat.telegrambot.Bot;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

import static org.igorlink.telegramminecraftchat.service.BotCommands.KILL_ALL_PLAYERS_COMMAND;
import static org.igorlink.telegramminecraftchat.service.BotCommands.START_COMMAND;

public class UpdateHandler {


    public static void handleMessage(Message message, Bot bot, TelegramMinecraftChat plugin) {
        if (!message.hasText()) {
            return;
        }

        String messageText = message.getText();

        // Если первый символ - это слеш, значит мы получили команду
        AbstractCommandHandler commandRunnable = null;
        if (messageText.charAt(0) == '/') {
            switch (messageText.substring(1)) {
                case START_COMMAND -> new StartCommandHandler(bot, plugin, message).run();
                case KILL_ALL_PLAYERS_COMMAND -> new KillAllPlayersCommandHandler(bot, plugin, message).run();
            }
        }

    }

    public static void handleCallbackQuery(CallbackQuery callbackQuery, Bot bot, TelegramMinecraftChat plugin) {
        switch (callbackQuery.getData().split(":")[0]) {
            case "player" -> new PlayerButtonHandler(bot, plugin, callbackQuery).run();
            case "player-list" -> new PlayerListHandler(bot, plugin, callbackQuery).run();
            case "close" -> new CloseMessageHandler(bot, plugin, callbackQuery).run();
        }
    }
}
