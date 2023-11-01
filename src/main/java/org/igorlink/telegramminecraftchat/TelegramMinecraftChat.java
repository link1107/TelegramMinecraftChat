package org.igorlink.telegramminecraftchat;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.igorlink.telegramminecraftchat.commands.BoomCommand;
import org.igorlink.telegramminecraftchat.service.GeneralEventListener;
import org.igorlink.telegramminecraftchat.telegrambot.Bot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class TelegramMinecraftChat extends JavaPlugin {

    // Получаем логгер плагина, чтобы выдавать сообщения в консоль сервера
    private final Logger logger = this.getLogger();

    public static TelegramMinecraftChat instance;

    @Override
    public void onEnable() {
        // Сохраняем config.yml в папке с именем плагина, если папки еще нет или файла конфига в ней нет
        saveDefaultConfig();

        // Запускаем телеграм-бота
        try {
            // Запускаем телеграм-апи
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            // Создаем и регистрируем нашего телеграм-бота
            Bot telegramBot = new Bot(this);
            telegramBotsApi.registerBot(telegramBot);
            // Пишем в консоль, что бот запущен
            logger.log(Level.INFO, "Bot has been successfully initialized!");

            // Регистрируем слушателя событий
            Bukkit.getPluginManager().registerEvents(new GeneralEventListener(this, telegramBot), this);
        } catch (TelegramApiException e) {
            // Выдаем сообщение в случае ошибки
            String errorMessage = "Bot has failed to launch! ";
            // Если мы получили сообщение от телеграма об ошибке удалении вебхука...
            if (e.getMessage().equals("Error removing old webhook")) {
                // Советуем пользователю проверить, заполнил ли он токен и юзернейм бота в config.yml
                errorMessage += "Check your bot-token and bot-username in 'plugins/TelegramBotsApi/config.yml'";
            } else {
                // ... в ином случае отображаем в консоли сообщение ошибки, которое нам выдал телеграм
                errorMessage += "Reason: " + e.getMessage();
            }

            // Выдаем в консоль сообщение об ошибке
            logger.log(Level.SEVERE, errorMessage);
        }

        instance = this;

        new BoomCommand(this);
    }

    @Override
    public void onDisable() {

    }
}
