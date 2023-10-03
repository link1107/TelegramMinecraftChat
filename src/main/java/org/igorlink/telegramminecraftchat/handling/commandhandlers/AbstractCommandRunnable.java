package org.igorlink.telegramminecraftchat.handling.commandhandlers;

import org.igorlink.telegramminecraftchat.telegrambot.Bot;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public abstract class AbstractCommandRunnable {

    final Bot bot;

    public AbstractCommandRunnable(Bot bot) {
        this.bot = bot;
    }

    public abstract void run();
}
