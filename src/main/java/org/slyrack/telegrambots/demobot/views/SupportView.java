package org.slyrack.telegrambots.demobot.views;

import lombok.SneakyThrows;
import org.apache.commons.codec.digest.DigestUtils;
import org.slyrack.telegrambots.annotations.ModelAtr;
import org.slyrack.telegrambots.annotations.SessionAtr;
import org.slyrack.telegrambots.annotations.View;
import org.slyrack.telegrambots.annotations.ViewController;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

@ViewController
public class SupportView {

    @SneakyThrows
    @View("support-answer")
    public void supportAnswer(final AbsSender absSender,
                              final Update update,
                              @SessionAtr("chat-id") final String chatId,
                              @ModelAtr("subject") final String subject,
                              @ModelAtr("mobile-phone") final String mobilePhone) {

        final String digest = DigestUtils.md5Hex(update.toString() + chatId + subject + mobilePhone);

        absSender.execute(
                SendMessage.builder()
                        .text(digest)
                        .chatId(chatId)
                        .build()
        );
    }
}
