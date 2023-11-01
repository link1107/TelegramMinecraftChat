package org.igorlink.telegramminecraftchat.handling.commandhandlers;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.igorlink.telegramminecraftchat.TelegramMinecraftChat;
import org.igorlink.telegramminecraftchat.service.MinecraftUtils;
import org.igorlink.telegramminecraftchat.telegrambot.Bot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class KillAllPlayersCommandHandler extends AbstractCommandHandler {
    public KillAllPlayersCommandHandler(Bot bot, TelegramMinecraftChat plugin, Message message) {
        super(bot, plugin, message);
    }

    @Override
    public void run() {
        try {
            int killedPlayers = 0;

            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                if (player.isDead() || player.getGameMode() != GameMode.SURVIVAL) {
                    continue;
                }
                player.setHealth(0.5);
                player.damage(100000);
                killedPlayers++;
            }

            bot.execute(SendMessage.builder()
                    .chatId(bot.getAdminId())
                    .text("Команда выполнена! Было убито " + killedPlayers + " игроков!")
                    .build()
            );

            if (killedPlayers != 0) {
                MinecraftUtils.broadcastMessage("Администратор убил " + killedPlayers + " " +
                        (killedPlayers == 1 ? "игрока!" : "игроков!"));
            }

        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
