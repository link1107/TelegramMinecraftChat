package org.igorlink.telegramminecraftchat.markup.buttons;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import javax.validation.constraints.NotNull;

@Slf4j
public class InlineButtonWithCallbackData extends AbstractInlineButton {

    protected String textOnTheButton;
    protected String callBackData;

    public InlineButtonWithCallbackData(@NotNull String textOnTheButton, @NotNull String callBackData) {
        this.callBackData = callBackData;
        this.textOnTheButton = textOnTheButton;
    }

    @Override
    public InlineKeyboardButton getInlineKeyboardButton() {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton(textOnTheButton);
        inlineKeyboardButton.setCallbackData(callBackData);
        return inlineKeyboardButton;
    }
}
