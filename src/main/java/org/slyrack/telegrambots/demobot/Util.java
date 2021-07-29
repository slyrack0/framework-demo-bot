package org.slyrack.telegrambots.demobot;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Optional;
import java.util.function.Predicate;

import static java.util.Objects.nonNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Util {

    public static Optional<User> getUser(final Update update) {
        if (update == null)
            return Optional.empty();
        else if (Flag.MESSAGE.test(update))
            return Optional.ofNullable(update.getMessage().getFrom());
        else if (Flag.CALLBACK_QUERY.test(update))
            return Optional.ofNullable(update.getCallbackQuery().getFrom());
        else if (Flag.INLINE_QUERY.test(update))
            return Optional.of(update.getInlineQuery().getFrom());
        else if (Flag.CHANNEL_POST.test(update))
            return Optional.ofNullable(update.getChannelPost().getFrom());
        else if (Flag.EDITED_CHANNEL_POST.test(update))
            return Optional.ofNullable(update.getEditedChannelPost().getFrom());
        else if (Flag.EDITED_MESSAGE.test(update))
            return Optional.ofNullable(update.getEditedMessage().getFrom());
        else if (Flag.CHOSEN_INLINE_QUERY.test(update))
            return Optional.of(update.getChosenInlineQuery().getFrom());
        else if (Flag.SHIPPING_QUERY.test(update))
            return Optional.ofNullable(update.getShippingQuery().getFrom());
        else if (Flag.PRECHECKOUT_QUERY.test(update))
            return Optional.ofNullable(update.getPreCheckoutQuery().getFrom());
        else if (Flag.POLL_ANSWER.test(update))
            return Optional.ofNullable(update.getPollAnswer().getUser());
        else
            return Optional.empty();
    }

    public static Optional<Long> getChatId(final Update update) {
        if (update == null)
            return Optional.empty();
        else if (Flag.MESSAGE.test(update))
            return Optional.ofNullable(update.getMessage().getChatId());
        else if (Flag.CALLBACK_QUERY.test(update))
            return Optional.ofNullable(update.getCallbackQuery().getMessage().getChatId());
        else if (Flag.INLINE_QUERY.test(update))
            return Optional.of(update.getInlineQuery().getFrom().getId());
        else if (Flag.CHANNEL_POST.test(update))
            return Optional.ofNullable(update.getChannelPost().getChatId());
        else if (Flag.EDITED_CHANNEL_POST.test(update))
            return Optional.ofNullable(update.getEditedChannelPost().getChatId());
        else if (Flag.EDITED_MESSAGE.test(update))
            return Optional.ofNullable(update.getEditedMessage().getChatId());
        else if (Flag.CHOSEN_INLINE_QUERY.test(update))
            return Optional.of(update.getChosenInlineQuery().getFrom().getId());
        else if (Flag.SHIPPING_QUERY.test(update))
            return Optional.of(update.getShippingQuery().getFrom().getId());
        else if (Flag.PRECHECKOUT_QUERY.test(update))
            return Optional.of(update.getPreCheckoutQuery().getFrom().getId());
        else if (Flag.POLL_ANSWER.test(update))
            return Optional.of(update.getPollAnswer().getUser().getId());
        else
            return Optional.empty();
    }


    private enum Flag implements Predicate<Update> {
        MESSAGE(Update::hasMessage),
        CALLBACK_QUERY(Update::hasCallbackQuery),
        CHANNEL_POST(Update::hasChannelPost),
        EDITED_CHANNEL_POST(Update::hasEditedChannelPost),
        EDITED_MESSAGE(Update::hasEditedMessage),
        INLINE_QUERY(Update::hasInlineQuery),
        CHOSEN_INLINE_QUERY(Update::hasChosenInlineQuery),
        SHIPPING_QUERY(Update::hasShippingQuery),
        PRECHECKOUT_QUERY(Update::hasPreCheckoutQuery),
        POLL(Update::hasPoll),
        POLL_ANSWER(Update::hasPollAnswer);


        private final Predicate<Update> predicate;

        Flag(Predicate<Update> predicate) {
            this.predicate = predicate;
        }

        public boolean test(Update update) {
            return nonNull(update) && predicate.test(update);
        }
    }
}
