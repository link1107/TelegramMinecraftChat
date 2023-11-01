package org.igorlink.telegramminecraftchat.handling.callbackhandlers;

import org.bukkit.entity.Player;
import org.igorlink.telegramminecraftchat.TelegramMinecraftChat;
import org.igorlink.telegramminecraftchat.handling.HandlingUtils;
import org.igorlink.telegramminecraftchat.markup.KeyboardFactory;
import org.igorlink.telegramminecraftchat.telegrambot.Bot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class PlayerListHandler extends AbstractCallbackHandler{

    public PlayerListHandler(Bot bot, TelegramMinecraftChat plugin, CallbackQuery callbackQuery) {
        super(bot, plugin, callbackQuery);
    }

    @Override
    public void run() {

        int page = Integer.parseInt(callbackQuery.getData().split(":")[1]);
        List<Player> onlinePlayersList = List.copyOf(plugin.getServer().getOnlinePlayers());

        try {
            bot.execute(
                    AnswerCallbackQuery.builder()
                            .callbackQueryId(callbackQuery.getId())
                            .text("")
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        HandlingUtils.getPlayerListMenu(
                onlinePlayersList,
                plugin,
                bot,
                page,
                callbackQuery.getMessage().getChatId(),
                callbackQuery.getMessage().getMessageId());
    }
}
