package org.igorlink.telegramminecraftchat.handling.commandhandlers;

import org.bukkit.entity.Player;
import org.igorlink.telegramminecraftchat.TelegramMinecraftChat;
import org.igorlink.telegramminecraftchat.handling.HandlingUtils;
import org.igorlink.telegramminecraftchat.markup.KeyboardFactory;
import org.igorlink.telegramminecraftchat.telegrambot.Bot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class StartCommandHandler extends AbstractCommandHandler {
    public StartCommandHandler(Bot bot, TelegramMinecraftChat plugin, Message message) {
        super(bot, plugin, message);
    }

    @Override
    public void run() {
        List<Player> onlinePlayersList = List.copyOf(plugin.getServer().getOnlinePlayers());

        HandlingUtils.getPlayerListMenu(
                onlinePlayersList,
                plugin,
                bot,
                1,
                message.getChatId());
    }
}
