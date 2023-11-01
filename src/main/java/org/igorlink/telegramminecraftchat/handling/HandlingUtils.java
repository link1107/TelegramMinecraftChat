package org.igorlink.telegramminecraftchat.handling;

import org.bukkit.entity.Player;
import org.igorlink.telegramminecraftchat.TelegramMinecraftChat;
import org.igorlink.telegramminecraftchat.markup.KeyboardFactory;
import org.igorlink.telegramminecraftchat.telegrambot.Bot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

import javax.annotation.Nullable;
import java.util.List;

public class HandlingUtils {
    public static void getPlayerListMenu(List<Player> playerList, TelegramMinecraftChat plugin, Bot bot, int listPage, Long chatId) {
        getPlayerListMenu(playerList, plugin, bot, listPage, chatId, null);
    }

    public static void getPlayerListMenu(List<Player> playerList, TelegramMinecraftChat plugin, Bot bot, int listPage, Long chatId, Integer messageToEditId) {

        if (playerList.size() == 0) {
            try {
                BotApiMethod<?> botApiMethod = messageToEditId == null ?
                        SendMessage.builder()
                                .chatId(chatId)
                                .text("<i>В данный момент на сервере нет игроков!</i>")
                                .parseMode(ParseMode.HTML)
                                .build()
                        :
                        EditMessageText.builder()
                                .chatId(chatId)
                                .messageId(messageToEditId)
                                .replyMarkup(null)
                                .parseMode(ParseMode.HTML)
                                .text("<i>В данный момент на сервере нет игроков!</i>")
                                .build();

                bot.execute(
                        botApiMethod
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return;
        }

        try {
            if (listPage > playerList.size() / 10 + 1) {
                listPage = playerList.size() / 10 + 1;
            }

            BotApiMethod<?> botApiMethod = messageToEditId == null ?
                    SendMessage.builder()
                            .chatId(chatId)
                            .text("<b>Список игроков на сервере</b>" + "\n\n" +
                                    "Выберите игрока для произведения пыток над ним." + "\n\n" +
                                    "<i>Страница " + listPage + " из " + (playerList.size() / 10 + 1) + "</i>")
                            .replyMarkup(
                                    KeyboardFactory.getListOfPlayersKeyboard(plugin, playerList, listPage)
                            )
                            .parseMode(ParseMode.HTML)
                            .build()
                    :
                    EditMessageText.builder()
                            .chatId(chatId)
                            .messageId(messageToEditId)
                            .text("<b>Список игроков на сервере</b>" + "\n\n" +
                                    "Выберите игрока для произведения пыток над ним." + "\n\n" +
                                    "<i>Страница " + listPage + " из " + (playerList.size() / 10 + 1) + "</i>")
                            .replyMarkup(
                                    KeyboardFactory.getListOfPlayersKeyboard(plugin, playerList, listPage)
                            )
                            .parseMode(ParseMode.HTML)
                            .build();

            bot.execute(
                    botApiMethod
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void deleteMessage(Bot bot, Long chatId, Integer messageId) {
        try {
            bot.execute(
                    DeleteMessage.builder()
                            .chatId(chatId)
                            .messageId(messageId)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
