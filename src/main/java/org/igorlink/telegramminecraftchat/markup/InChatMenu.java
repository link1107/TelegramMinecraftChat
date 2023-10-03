package org.igorlink.telegramminecraftchat.markup;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.igorlink.telegramminecraftchat.markup.buttons.AbstractInlineButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class InChatMenu {
    @Getter
    private final InlineKeyboardMarkup inlineKeyboardMarkUp;

    public InChatMenu(List<List<AbstractInlineButton>> inlineKeybaordLists) {
        List<List<InlineKeyboardButton>> listOfInlineKeyboardButtonsRows = new ArrayList<>();
        for (List<AbstractInlineButton> row : inlineKeybaordLists) {
            List<InlineKeyboardButton> rowOfInlineKeyboardButtons = new ArrayList<>();
            for (AbstractInlineButton abstractInlineButton : row) {
                rowOfInlineKeyboardButtons.add(abstractInlineButton.getInlineKeyboardButton());
            }
            listOfInlineKeyboardButtonsRows.add(rowOfInlineKeyboardButtons);
        }

        inlineKeyboardMarkUp = new InlineKeyboardMarkup(listOfInlineKeyboardButtonsRows);
    }

}
