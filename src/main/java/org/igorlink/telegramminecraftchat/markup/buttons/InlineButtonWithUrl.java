package org.igorlink.telegramminecraftchat.markup.buttons;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import javax.validation.constraints.NotNull;

public class InlineButtonWithUrl extends AbstractInlineButton {

    protected String textOnTheButton;
    protected String url;

    public InlineButtonWithUrl(@NotNull String textOnTheButton, @NotNull String url) {
        this.url = url;
        this.textOnTheButton = textOnTheButton;
    }

    @Override
    public InlineKeyboardButton getInlineKeyboardButton() {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton(textOnTheButton);
        inlineKeyboardButton.setUrl(url);
        return inlineKeyboardButton;
    }
}
