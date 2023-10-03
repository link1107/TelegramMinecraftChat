package org.igorlink.telegramminecraftchat.markup;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BottomMenu {
    List<List<String>> keyboard;

    @Getter
    ReplyKeyboardMarkup replyKeyboardMarkup;


    public BottomMenu(List<List<String>> keyboard) {
        replyKeyboardMarkup = getMarkUpFromListOfLists(keyboard);
    }

    private ReplyKeyboardMarkup getMarkUpFromListOfLists(List<List<String>> keyboard) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        List<KeyboardRow> finalKeyboard = new ArrayList<>();
        for (List<String> row : keyboard) {
            KeyboardRow newKeyboardRow = new KeyboardRow();
            for (String buttonText : row) {
                newKeyboardRow.add(new KeyboardButton(buttonText));
            }
            finalKeyboard.add(newKeyboardRow);
        }

        replyKeyboardMarkup.setKeyboard(finalKeyboard);
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }
}
