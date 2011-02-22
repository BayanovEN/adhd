package com.jieyou.adhd.web;

import com.jieyou.adhd.domain.Scale;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "scales", formBackingObject = Scale.class)
@RequestMapping("/scales")
@Controller
public class ScaleController {
}
