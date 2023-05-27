package org.igorlink.telegramminecraftchat.service;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.igorlink.telegramminecraftchat.TelegramMinecraftChat;
import org.igorlink.telegramminecraftchat.telegrambot.Bot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GeneralEventListener implements Listener {
    private Logger logger;
    private Bot telegramBot;
    private TelegramMinecraftChat plugin;
    private Long adminId;

    // Конструктор слушателя событий
    public GeneralEventListener(TelegramMinecraftChat plugin, Bot telegramBot) {
        this.logger = plugin.getLogger();
        this.plugin = plugin;
        this.telegramBot = telegramBot;
        this.adminId = plugin.getConfig().getLong("telegram-bot-admin-id");
    }

    // Аннотация EventHandler означает, что метод будет вызван, если произойдет событие, соответствующее классу аргумента
    @EventHandler
    public void handleChatMessage(AsyncChatEvent event) {
        // Получаем текст сообщения
        String chatMessageText = ((TextComponent) event.message()).content();
        // Получаем имя игрока-отправителя
        String senderPlayerName = event.getPlayer().getName();

        // Заменяем служебные символы HTML-разметки на соответствующие им
        chatMessageText = chatMessageText.replace("&", "&amp").replace("<", "&lt").replace(">", "&gt");

        // Создаем метод отпарвки сообщение в телеграм
        SendMessage sendMessage = new SendMessage();
        // Устанавливаем чат отправки сообщения - чат с админом
        sendMessage.setChatId(adminId);
        // Устанавливаем режим форматирования - HTML-разметка
        sendMessage.setParseMode(ParseMode.HTML);
        // Устанавливаем текст сообщения
        sendMessage.setText("<code>" + senderPlayerName + "</code>:  " + chatMessageText);

        try {
            // Отправляем сообщение
            telegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            // Выдаем ошибку в случае ошибки
            logger.log(Level.WARNING, "Failed to send a message! Reason: " + e.getMessage());
        }

    }

}
