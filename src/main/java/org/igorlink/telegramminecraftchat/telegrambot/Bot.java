package org.igorlink.telegramminecraftchat.telegrambot;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.util.RGBLike;
import org.bukkit.Bukkit;
import org.igorlink.telegramminecraftchat.TelegramMinecraftChat;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.logging.Logger;

public class Bot extends TelegramLongPollingBot {

    // Сохраняем объект плагина, чтобы вытаскивать из него настройки при необходимости через #getConfig()
    private final TelegramMinecraftChat telegramMinecraftChat;

    // Переменная с телеграмным ID админа
    private final Long adminId;

    // Получаем логгер плагина, чтобы выдавать сообщения в консоль сервера
    private final Logger logger;

    public Bot(TelegramMinecraftChat telegramMinecraftChat) {
        super();
        this.telegramMinecraftChat = telegramMinecraftChat;
        this.adminId = telegramMinecraftChat.getConfig().getLong("telegram-bot-admin-id");
        this.logger = telegramMinecraftChat.getLogger();
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        // Проверяем, если в полученном апдейте есть сообщение от админа, и оно содержит текст...
        if (message != null && message.getChat().getId().equals(adminId) && message.hasText()) {
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
