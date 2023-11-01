package org.igorlink.telegramminecraftchat.service;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class MinecraftUtils {
    // Метод, который будет выводить всем игрокам на сервере заданное сообщение, добавляя префикс плагина
     public static void broadcastMessage(String message) {
         Bukkit.getServer().broadcastMessage(
                 ChatColor.AQUA + "[TelegramMinecraftChat] " + ChatColor.RESET + message
         );
     }
}
