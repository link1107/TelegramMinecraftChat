package org.igorlink.telegramminecraftchat.markup;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import javax.validation.constraints.NotNull;
import java.util.Comparator;
import java.util.List;

public class KeyboardFactory {
    public static InlineKeyboardMarkup getListOfPlayersKeyboard(@NotNull JavaPlugin plugin, @NotNull List<Player> onlinePlayersList) {
        return getListOfPlayersKeyboard(plugin, onlinePlayersList, 1);
    }

    public static InlineKeyboardMarkup getListOfPlayersKeyboard(@NotNull JavaPlugin plugin, @NotNull List<Player> onlinePlayersList, int page) {
        if (onlinePlayersList.isEmpty()) {
            throw new IllegalArgumentException("OnlinePlayersList can't be empty!");
        }

        if (page < 1) {
            throw new IllegalArgumentException("Page can't be less than 1!");
        }

        if (page > onlinePlayersList.size() / 10 + 1) {
            throw new IllegalArgumentException("Player list page index is out of bounds!");
        }

        Keyboard tmpKeyboard = KeyboardBuilder.buildKeyboard();

        for (Player player :
                onlinePlayersList.stream()
                        .sorted(Comparator.comparing(Player::getName))
                        .toList().subList((page - 1) * 10, Math.min(page * 10, onlinePlayersList.size()))
        ) {
            tmpKeyboard
                    .addCallbackButton("<" + player.getName() + ">", "player:" + player.getName() + ":" + page)
                    .buildRow();
        }

        if (page > 1) {
            tmpKeyboard.addCallbackButton("<<", "player-list:" + (page - 1));
        }

        tmpKeyboard.addCallbackButton("Закрыть", "close");

        if (page < onlinePlayersList.size() / 10 + 1) {
            tmpKeyboard.addCallbackButton(">>", "player-list:" + (page + 1));
        }

        return tmpKeyboard.build();
    }


    public static InlineKeyboardMarkup getListOfActionsOnPlayer(String playerName, int currentListPage) {
        Keyboard tmpKeyboard = KeyboardBuilder.buildKeyboard();

        tmpKeyboard.addCallbackButton("<Взорвать>", "player-action:" + playerName + ":explode");
        tmpKeyboard.buildRow();
        tmpKeyboard.addCallbackButton("<Кикнуть>", "player-action:" + playerName + ":kick");
        tmpKeyboard.buildRow();
        tmpKeyboard.addCallbackButton("<Забанить>", "player-action:" + playerName + ":explode");
        tmpKeyboard.buildRow();
        tmpKeyboard.addCallbackButton("Назад", "player-list:" + currentListPage);

        return tmpKeyboard.build();
    }
}
