package com.jieyou.adhd.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import com.jieyou.adhd.domain.Record;
import com.jieyou.adhd.domain.Scale;
import com.jieyou.adhd.service.ConclusionService;

@RooWebScaffold(path = "records", formBackingObject = Record.class)
@RequestMapping("/records")
@Controller
public class RecordController {
	private ConclusionService conclusionService;

    @Autowired
    public RecordController(ConclusionService conclusionService) {
	this.conclusionService = conclusionService;
    }
	@RequestMapping(method = RequestMethod.POST)
    public String create(@Valid Record record, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            model.addAttribute("record", record);
            addDateTimeFormatPatterns(model);
            return "records/create";
        }
        record.persist();
        return "redirect:/records/" + encodeUrlPathSegment(record.getId().toString(), request);
    }

	@RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model model) {
        model.addAttribute("record", new Record());
        addDateTimeFormatPatterns(model);
        List dependencies = new ArrayList();
        if (Scale.countScales() == 0) {
            dependencies.add(new String[]{"scale", "scales"});
        }
        model.addAttribute("dependencies", dependencies);
        return "records/create";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model model) {
        addDateTimeFormatPatterns(model);
        Record record = Record.findRecord(id);
		model.addAttribute("record", record);
		model.addAttribute("scale", record.getScale());
        model.addAttribute("conclusion",conclusionService.getResult(record));
        model.addAttribute("answers",conclusionService.getAnswers(record));
        return "records/show";
    }

	@RequestMapping(method = RequestMethod.GET)
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model model) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            model.addAttribute("records", Record.findRecordEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) Record.countRecords() / sizeNo;
            model.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            model.addAttribute("records", Record.findAllRecords());
        }
        addDateTimeFormatPatterns(model);
        return "records/list";
    }

	@RequestMapping(method = RequestMethod.PUT)
    public String update(@Valid Record record, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            model.addAttribute("record", record);
            addDateTimeFormatPatterns(model);
            return "records/update";
        }
        record.merge();
        return "redirect:/records/" + encodeUrlPathSegment(record.getId().toString(), request);
    }

	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("record", Record.findRecord(id));
        addDateTimeFormatPatterns(model);
        return "records/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model model) {
        Record.findRecord(id).remove();
        model.addAttribute("page", (page == null) ? "1" : page.toString());
        model.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/records?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }

	@ModelAttribute("scales")
    public Collection<Scale> populateScales() {
        return Scale.findAllScales();
    }

	void addDateTimeFormatPatterns(Model model) {
        model.addAttribute("record_doneday_date_format", DateTimeFormat.patternForStyle("S-", LocaleContextHolder.getLocale()));
    }

	String encodeUrlPathSegment(String pathSegment, HttpServletRequest request) {
        String enc = request.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        }
        catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
}
