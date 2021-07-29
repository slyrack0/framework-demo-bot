package org.slyrack.telegrambots.demobot.controllers;

import lombok.SneakyThrows;
import org.slyrack.telegrambots.Model;
import org.slyrack.telegrambots.ModelAndView;
import org.slyrack.telegrambots.StatefulModelAndView;
import org.slyrack.telegrambots.annotations.Command;
import org.slyrack.telegrambots.annotations.Controller;
import org.slyrack.telegrambots.annotations.HasText;
import org.slyrack.telegrambots.annotations.SessionAtr;
import org.slyrack.telegrambots.flags.TextTarget;
import org.slyrack.telegrambots.flags.UpdateType;
import org.slyrack.telegrambots.session.Session;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.regex.Pattern;

@Controller
public class InitController {

    private static final Pattern MOBILE_PHONE_PATTERN = Pattern.compile("^\\d{5,12}$");

    @Command(value = UpdateType.MESSAGE)
    public ModelAndView start() {
        return new StatefulModelAndView(
                "subject-select-state",
                "select-subject-view"
        );
    }

    @Command(value = UpdateType.CALLBACK_QUERY, state = "subject-select-state")
    public ModelAndView selectSubject(final Update update) {
        return new StatefulModelAndView(
                "enter-mobile-state",
                "enter-mobile-view"
        );
    }

    @SneakyThrows
    @Command(value = UpdateType.MESSAGE, state = "subject-select-state")
    public void removeMessages(final Update update,
                               final AbsSender absSender,
                               @SessionAtr("chat-id") final String chatId) {

        absSender.execute(DeleteMessage.builder()
                .chatId(chatId)
                .messageId(update.getMessage().getMessageId())
                .build());
    }

    @Command(value = UpdateType.MESSAGE, state = "enter-mobile-state")
    public ModelAndView enterMobile(final Update update, final Model model) {
        if (update.getMessage().hasText()) {
            final String text = update.getMessage().getText();
            if (MOBILE_PHONE_PATTERN.matcher(text).matches()) {
                model.setAttribute("mobile-phone", text);
                return new StatefulModelAndView(
                        "support-dialog",
                        "start-support-dialog-view",
                        model
                );
            }
        }

        return new StatefulModelAndView(
                "enter-mobile-state",
                "enter-mobile-bad-view",
                model
        );
    }

    @SneakyThrows
    @Command(
            value = UpdateType.MESSAGE,
            state = {
                    "subject-select-state",
                    "enter-mobile-state",
                    "support-dialog"
            },
            exclusive = true
    )
    @HasText(textTarget = TextTarget.MESSAGE_TEXT, equals = "/cancel")
    public ModelAndView cancelDialog(final Session session) {
        session.stop();
        return new ModelAndView("cancel-dialog");
    }
}
