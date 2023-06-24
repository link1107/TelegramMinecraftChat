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
    private final Logger logger;
    private final Bot telegramBot;
    private final Long adminId;

    public GeneralEventListener(TelegramMinecraftChat plugin, Bot telegramBot) { // Конструктор слушателя событий
        this.logger = plugin.getLogger();
        this.telegramBot = telegramBot;
        this.adminId = plugin.getConfig().getLong("telegram-bot-admin-id");
    }

    @EventHandler
    public void handleChatMessage(AsyncChatEvent event) { // Событие чата
        String chatMessageText = ((TextComponent) event.message()).content() // получаем текст сообщения
                .replace("&", "&amp") // заменяем в нем служебные символы HTML-разметки
                .replace("<", "&lt")
                .replace(">", "&gt");
        try {
            telegramBot.execute(new SendMessage() {{ // Отправляем сообщение
                setChatId(adminId); // Устанавливаем чат отправки сообщения - чат с админом
                setParseMode(ParseMode.HTML); // Устанавливаем режим форматирования - HTML-разметка
                setText("<code>" + event.getPlayer().getName() + "</code>:  " + chatMessageText); // Устанавливаем текст сообщения
            }});
        } catch (TelegramApiException e) {
            logger.log(Level.WARNING, "Failed to send a message! Reason: " + e.getMessage()); // Выводим ошибку
        }
    }
}
