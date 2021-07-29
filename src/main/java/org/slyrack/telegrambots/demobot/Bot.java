package org.slyrack.telegrambots.demobot;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.slyrack.telegrambots.core.UpdateHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@AllArgsConstructor
public class Bot extends TelegramLongPollingBot {

    private final UpdateHandler updateHandler;

    @Override
    public String getBotUsername() {
        return "name";
    }

    @Override
    public String getBotToken() {
        return "token";
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(final Update update) {
        updateHandler.handleUpdate(update, this);
    }

}
