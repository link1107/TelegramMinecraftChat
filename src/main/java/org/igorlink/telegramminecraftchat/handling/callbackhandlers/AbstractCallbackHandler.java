package org.igorlink.telegramminecraftchat.handling.callbackhandlers;

import org.igorlink.telegramminecraftchat.TelegramMinecraftChat;
import org.igorlink.telegramminecraftchat.telegrambot.Bot;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.logging.Logger;

public abstract class AbstractCallbackHandler {

    final Bot bot;

    final Logger log;

    final TelegramMinecraftChat plugin;

    final CallbackQuery callbackQuery;

    public AbstractCallbackHandler(Bot bot, TelegramMinecraftChat plugin, CallbackQuery callbackQuery) {
        this.bot = bot;
        this.plugin = plugin;
        this.log = plugin.getLogger();
        this.callbackQuery = callbackQuery;
    }

    public abstract void run();
}
