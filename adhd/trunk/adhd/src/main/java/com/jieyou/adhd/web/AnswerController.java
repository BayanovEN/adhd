package com.jieyou.adhd.web;

import com.jieyou.adhd.domain.Answer;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "answers", formBackingObject = Answer.class)
@RequestMapping("/answers")
@Controller
public class AnswerController {
}
