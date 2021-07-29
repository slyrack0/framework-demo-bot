package org.slyrack.telegrambots.demobot.views;

import lombok.SneakyThrows;
import org.slyrack.telegrambots.annotations.SessionAtr;
import org.slyrack.telegrambots.annotations.View;
import org.slyrack.telegrambots.annotations.ViewController;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.List;

@ViewController
public class InitViews {

    private static final String SELECT_SUBJECT = "Привествуем. Выберите тему вопроса.";
    private static final String SUBJECT = "Тема вопроса: ";
    private static final String ENTER_MOBILE_TEXT = "Введите ваш номер телефона для обратной связи.";
    private static final String ENTER_MOBILE_BAD = "Вы ввели некорректный номер телефона, повторите попытку.";
    private static final String START_DIALOG = "Специалист подключен. Напишите нам о вашей проблеме.";
    private static final String CANCEL_DIALOG_TEXT = "Спасибо за ваше обращение!\n" +
            "Если у вас снова возникнут вопросы мы будем рады вам помочь!";

    @SneakyThrows
    @View("select-subject-view")
    public void selectSubject(final AbsSender absSender,
                              @SessionAtr("chat-id") final String chatId) {

        absSender.execute(SendMessage.builder()
                .text(SELECT_SUBJECT)
                .chatId(chatId)
                .replyMarkup(InlineKeyboardMarkup.builder()
                        .keyboardRow(List.of(InlineKeyboardButton.builder()
                                .text("Оплата")
                                .callbackData("Оплата")
                                .build()))
                        .keyboardRow(List.of(InlineKeyboardButton.builder()
                                .text("Тарифы")
                                .callbackData("Тарифы")
                                .build()))
                        .keyboardRow(List.of(InlineKeyboardButton.builder()
                                .text("Интернет не работает")
                                .callbackData("Интернет не работает")
                                .build()))
                        .keyboardRow(List.of(InlineKeyboardButton.builder()
                                .text("Соединить с оператором")
                                .callbackData("Соединить с оператором")
                                .build()))
                        .build())
                .build());
    }

    @SneakyThrows
    @View("enter-mobile-view")
    public void enterMobile(final Update update,
                            final AbsSender absSender,
                            @SessionAtr("chat-id") final String chatId) {

        // answer callback select subject
        absSender.execute(AnswerCallbackQuery.builder()
                .callbackQueryId(update.getCallbackQuery().getId())
                .build());

        // edit select subject message
        absSender.execute(EditMessageText.builder()
                .text(SUBJECT.concat(update.getCallbackQuery().getData()))
                .chatId(chatId)
                .messageId(update.getCallbackQuery().getMessage().getMessageId())
                .build());

        // send enter mobile message
        absSender.execute(SendMessage.builder()
                .text(ENTER_MOBILE_TEXT)
                .chatId(chatId)
                .build());
    }

    @SneakyThrows
    @View("enter-mobile-bad-view")
    public void enterMobileBad(final AbsSender absSender,
                               @SessionAtr("chat-id") final String chatId) {

        absSender.execute(SendMessage.builder()
                .text(ENTER_MOBILE_BAD)
                .chatId(chatId)
                .build());
    }

    @SneakyThrows
    @View("start-support-dialog-view")
    public void startSupportDialog(final AbsSender absSender,
                                   @SessionAtr("chat-id") final String chatId) {

        absSender.execute(SendMessage.builder()
                .text(START_DIALOG)
                .chatId(chatId)
                .build());
    }

    @SneakyThrows
    @View("cancel-dialog")
    public void cancelDialog(final AbsSender absSender,
                             @SessionAtr("chat-id") final String chatId) {

        absSender.execute(
                SendMessage.builder()
                        .text(CANCEL_DIALOG_TEXT)
                        .chatId(chatId)
                        .build()
        );
    }
}
