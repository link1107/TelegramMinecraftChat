package org.igorlink.telegramminecraftchat.handling.callbackhandlers;

import org.igorlink.telegramminecraftchat.TelegramMinecraftChat;
import org.igorlink.telegramminecraftchat.handling.HandlingUtils;
import org.igorlink.telegramminecraftchat.telegrambot.Bot;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public class CloseMessageHandler extends AbstractCallbackHandler{

    public CloseMessageHandler(Bot bot, TelegramMinecraftChat plugin, CallbackQuery callbackQuery) {
        super(bot, plugin, callbackQuery);
    }

    @Override
    public void run() {
        HandlingUtils.deleteMessage(
                bot,
                callbackQuery.getMessage().getChatId(),
                callbackQuery.getMessage().getMessageId());
    }
}
