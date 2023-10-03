package org.igorlink.telegramminecraftchat.telegrambot;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.igorlink.telegramminecraftchat.TelegramMinecraftChat;
import org.igorlink.telegramminecraftchat.handling.MessageHandler;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScope;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeChat;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import static org.igorlink.telegramminecraftchat.service.BotCommands.KILL_ALL_PLAYERS_COMMAND;
import static org.igorlink.telegramminecraftchat.service.BotCommands.START_COMMAND;


public class Bot extends TelegramLongPollingBot {

    // Сохраняем объект плагина, чтобы вытаскивать из него настройки при необходимости через #getConfig()
    private final TelegramMinecraftChat telegramMinecraftChat;

    // Переменная с телеграмным ID админа
    @Getter
    private final Long adminId;

    // Получаем логгер плагина, чтобы выдавать сообщения в консоль сервера
    private final Logger logger;


    public Bot(TelegramMinecraftChat telegramMinecraftChat) {
        super();
        this.telegramMinecraftChat = telegramMinecraftChat;
        this.adminId = telegramMinecraftChat.getConfig().getLong("telegram-bot-admin-id");
        this.logger = telegramMinecraftChat.getLogger();

        List<BotCommand> botRuCommands = Arrays.asList(
                new BotCommand(START_COMMAND, "Открыть список игроков"),
                new BotCommand(KILL_ALL_PLAYERS_COMMAND, "Убить всех игроков на сервере")
        );

        List<BotCommand> botEnCommands = Arrays.asList(
                new BotCommand(START_COMMAND, "Open players list"),
                new BotCommand(KILL_ALL_PLAYERS_COMMAND, "Kill all players")
        );

        BotCommandScope botCommandScope = BotCommandScopeChat.builder().chatId(adminId).build();

        SetMyCommands setMyRuCommands = SetMyCommands.builder().commands(botRuCommands).scope(botCommandScope).languageCode("ru").build();
        SetMyCommands setMyEnCommands = SetMyCommands.builder().commands(botEnCommands).scope(botCommandScope).languageCode("en").build();

        try {
            this.execute(setMyRuCommands);
            logger.info("RU-commands has been successfully set!");
        } catch (TelegramApiException e) {
            logger.warning("Error while setting RU-commands: " + e.getMessage());
        }

        try {
            this.execute(setMyEnCommands);
            logger.info("EN-commands has been successfully set!");
        } catch (TelegramApiException e) {
            logger.warning("Error while setting EN-commands: " + e.getMessage());
        }

    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        // Проверяем, если в полученном апдейте есть сообщение от админа, и оно содержит текст...
        if (message != null && message.getChat().getId().equals(adminId)) {
            MessageHandler.handleMessage(message, this);

            User messageSender = message.getFrom();
            String messageSenderFullName = messageSender.getFirstName() +
                    (messageSender.getLastName() != null ? (" " + messageSender.getLastName()) : "");

            // Создаем текст сообщения для сервера
            // Формат сообщения для сервера: <ИМЯ> Сообщение
            TextComponent finalMessage = Component.text("<");
            finalMessage = finalMessage.append(Component.text(messageSenderFullName, TextColor.color(0, 255,255)));
            finalMessage = finalMessage.append(Component.text("> " + message.getText()));

            // Отправляем сообщение на сервер всем пользователям
            Bukkit.getServer().broadcast(finalMessage);
        }

    }

    @Override
    public String getBotUsername() {
        return telegramMinecraftChat.getConfig().getString("telegram-bot-username");
    }

    @Override
    public String getBotToken() {
        return telegramMinecraftChat.getConfig().getString("telegram-bot-api-key");
    }
}
