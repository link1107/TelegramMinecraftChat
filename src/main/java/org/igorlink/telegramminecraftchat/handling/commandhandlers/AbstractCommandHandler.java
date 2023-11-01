package org.igorlink.telegramminecraftchat.handling.commandhandlers;

import org.igorlink.telegramminecraftchat.TelegramMinecraftChat;
import org.igorlink.telegramminecraftchat.telegrambot.Bot;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.logging.Logger;

public abstract class AbstractCommandHandler {

    final Bot bot;

    final Logger log;

    final TelegramMinecraftChat plugin;

    final Message message;

    public AbstractCommandHandler(Bot bot, TelegramMinecraftChat plugin, Message message) {
        this.bot = bot;
        this.plugin = plugin;
        this.log = plugin.getLogger();
        this.message = message;
    }

    public abstract void run();
}
