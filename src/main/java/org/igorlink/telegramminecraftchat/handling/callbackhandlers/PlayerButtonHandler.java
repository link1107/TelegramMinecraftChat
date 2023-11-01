package org.igorlink.telegramminecraftchat.handling.callbackhandlers;

import org.bukkit.entity.Player;
import org.igorlink.telegramminecraftchat.TelegramMinecraftChat;
import org.igorlink.telegramminecraftchat.handling.HandlingUtils;
import org.igorlink.telegramminecraftchat.markup.KeyboardFactory;
import org.igorlink.telegramminecraftchat.telegrambot.Bot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;

public class PlayerButtonHandler extends AbstractCallbackHandler {
    public PlayerButtonHandler(Bot bot, TelegramMinecraftChat plugin, CallbackQuery callbackQuery) {
        super(bot, plugin, callbackQuery);
    }

    @Override
    public void run() {
        String playerName = callbackQuery.getData().split(":")[1];
        int playerListPage = Integer.parseInt(callbackQuery.getData().split(":")[2]);

        Player player = plugin.getServer().getPlayerExact(playerName);
        if (player == null) {
            try {
                bot.execute(AnswerCallbackQuery.builder()
                        .callbackQueryId(callbackQuery.getId())
                        .text("Игрок не найден! Возможно, он уже не находится на сервере.")
                        .showAlert(true)
                        .build()
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            HandlingUtils.getPlayerListMenu(
                    List.copyOf(plugin.getServer().getOnlinePlayers()),
                    plugin,
                    bot,
                    playerListPage,
                    callbackQuery.getMessage().getChatId(),
                    callbackQuery.getMessage().getMessageId());

            return;
        }


        try {
            bot.execute(
                    AnswerCallbackQuery.builder()
                            .callbackQueryId(callbackQuery.getId())
                            .text("")
                            .build()
            );

            bot.execute(
                    EditMessageText.builder()
                            .chatId(callbackQuery.getMessage().getChatId())
                            .messageId(callbackQuery.getMessage().getMessageId())
                            .text("<b>Игрок " + playerName + "</b>\n\n" +
                                    "Состояние: <i>" + (player.isDead() ? "мёртв" : "жив") + "</i>\n" +
                                    (!player.isDead() ? "Здоровье: <i>" + player.getHealth() + "</i>\n" : "") +
                                    "\n" +
                                    "Выберите желаемое действие.")
                            .replyMarkup(
                                    KeyboardFactory.getListOfActionsOnPlayer(playerName, playerListPage)
                            )
                            .parseMode(ParseMode.HTML)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

