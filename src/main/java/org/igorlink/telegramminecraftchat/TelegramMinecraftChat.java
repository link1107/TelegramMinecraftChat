package org.igorlink.telegramminecraftchat;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.igorlink.telegramminecraftchat.service.GeneralEventListener;
import org.igorlink.telegramminecraftchat.telegrambot.Bot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class TelegramMinecraftChat extends JavaPlugin {
    private final Logger logger = this.getLogger(); // Получаем логгер, чтобы выдавать сообщения в консоль сервера

    @Override
    public void onEnable() {
        this.saveDefaultConfig(); // Сохраняем config.yml в папке с именем плагина, если файла конфига в ней нет

        try { // Запускаем телеграм-бота
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class); // Запускаем телеграм-апи
            Bot telegramBot = new Bot(this); // Создаем и регистрируем нашего телеграм-бота
            telegramBotsApi.registerBot(telegramBot);
            logger.log(Level.INFO, "Bot has been successfully initialized!"); // Пишем в консоль, что бот запущен

            Bukkit.getPluginManager().registerEvents(new GeneralEventListener(this, telegramBot), this); // Регистрируем слушателя событий
        } catch (TelegramApiException e) {
            // Если мы получили сообщение от телеграма об ошибке удаления вебхука, то советуем пользователю проверить,
            // заполнил ли он токен и юзернейм бота в config.yml, иначе отображаем в консоли сообщение ошибки телеграма
            logger.log(Level.SEVERE, "Bot has failed to launch! " +
                    (e.getMessage().equalsIgnoreCase("Error removing old webhook") ?
                            "Check your bot-token and bot-username in 'plugins/TelegramBotsApi/config.yml'" :
                            "Reason: " + e.getMessage()
                    )
            );
        }
    }

    @Override
    public void onDisable() {
        logger.log(Level.INFO, "Bot has been disabled!"); // Пишем в консоль, что бот выключен
    }
}
