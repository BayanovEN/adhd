package com.jieyou.adhd.web;

import com.jieyou.adhd.domain.Record;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "records", formBackingObject = Record.class)
@RequestMapping("/records")
@Controller
public class RecordController {
}
