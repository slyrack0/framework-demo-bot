package org.slyrack.telegrambots.demobot.controllers;

import org.slyrack.telegrambots.Model;
import org.slyrack.telegrambots.ModelAndView;
import org.slyrack.telegrambots.StatefulModelAndView;
import org.slyrack.telegrambots.annotations.Command;
import org.slyrack.telegrambots.annotations.Controller;
import org.slyrack.telegrambots.flags.UpdateType;

@Controller
public class SupportController {

    @Command(value = UpdateType.MESSAGE, state = "support-dialog")
    public ModelAndView supportDialog(final Model model) {
        return new StatefulModelAndView(
                "support-dialog",
                "support-answer",
                model);
    }

}
