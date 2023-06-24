package org.igorlink.telegramminecraftchat.telegrambot;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.igorlink.telegramminecraftchat.TelegramMinecraftChat;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

public class Bot extends TelegramLongPollingBot {
    private final TelegramMinecraftChat telegramMinecraftChat; // Объект плагина, чтобы вытаскивать из него настройки при необходимости через #getConfig()
    private final Long adminId; // Переменная с телеграмным ID админа

    @SuppressWarnings("deprecation")
    public Bot(TelegramMinecraftChat telegramMinecraftChat) {
        super();
        this.telegramMinecraftChat = telegramMinecraftChat;
        this.adminId = telegramMinecraftChat.getConfig().getLong("telegram-bot-admin-id");
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        // Проверяем, есть ли в полученном апдейте сообщение от админа, и оно содержит текст...
        if (message != null && message.getChat().getId().equals(adminId) && message.hasText()) {
            User messageSender = message.getFrom();
            String messageSenderFullName = messageSender.getFirstName() +
                    (messageSender.getLastName() != null ? (" " + messageSender.getLastName()) : "");

            // Создаем текст сообщения для сервера
            // Формат сообщения для сервера: <ИМЯ> Сообщение
            TextComponent finalMessage = Component.text("<")
                    .append(Component.text(messageSenderFullName, TextColor.color(0, 255,255)))
                    .append(Component.text("> " + message.getText()));

            Bukkit.getServer().broadcast(finalMessage); // Отправляем сообщение на сервер всем пользователям
        }
    }

    @Override
    public String getBotUsername() {
        return telegramMinecraftChat.getConfig().getString("telegram-bot-username");
    }

    @Override
    @SuppressWarnings("deprecation")
    public String getBotToken() {
        return telegramMinecraftChat.getConfig().getString("telegram-bot-api-key");
    }
}
