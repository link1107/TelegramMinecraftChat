package org.igorlink.telegramminecraftchat.markup;

import org.igorlink.telegramminecraftchat.markup.buttons.InlineButtonWithCallbackData;
import org.igorlink.telegramminecraftchat.markup.buttons.InlineButtonWithUrl;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class Keyboard {

    private List<InlineKeyboardButton> currentRow = new ArrayList<>();

    private final List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

    // 2 метода: первый добавляет в клавиатуру кнопку с CallbackData, второй добавляет в клавиатуру кнопку с URL
    public Keyboard addCallbackButton(String text, String callbackData) {
        currentRow.add(new InlineButtonWithCallbackData(text, callbackData).getInlineKeyboardButton());
        return this;
    }

    public Keyboard addUrlButton(String text, String url) {
        currentRow.add(new InlineButtonWithUrl(text, url).getInlineKeyboardButton());
        return this;
    }

    public Keyboard buildRow() {
        if (currentRow.isEmpty()) {
            throw new IllegalStateException("Row can't be empty!");
        }

        keyboard.add(currentRow);
        currentRow = new ArrayList<>();
        return this;
    }

    public InlineKeyboardMarkup build() {
        if (!currentRow.isEmpty()) {
            keyboard.add(currentRow);
            currentRow = new ArrayList<>();
        }

        return new InlineKeyboardMarkup(keyboard);
    }
}
