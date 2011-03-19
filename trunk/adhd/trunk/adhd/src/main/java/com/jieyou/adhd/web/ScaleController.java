package com.jieyou.adhd.web;

import com.jieyou.adhd.domain.Answer;
import com.jieyou.adhd.domain.Question;
import com.jieyou.adhd.domain.Record;
import com.jieyou.adhd.domain.Scale;
import com.jieyou.adhd.reference.ScaleType;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.joda.time.format.DateTimeFormat;
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

@RooWebScaffold(path = "scales", formBackingObject = Scale.class)
@RequestMapping("/scales")
@Controller
public class ScaleController {

	@RequestMapping(method = RequestMethod.POST)
    public String create(@Valid Scale scale, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            model.addAttribute("scale", scale);
            return "scales/create";
        }
        scale.persist();
        return "redirect:/scales/" + encodeUrlPathSegment(scale.getId().toString(), request);
    }

	@RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model model) {
        model.addAttribute("scale", new Scale());
        return "scales/create";
    }
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("scale", Scale.findScale(id));
        model.addAttribute("itemId", id);
        return "scales/show";
    }
	@RequestMapping(value = "/{id}",params = "test", method = RequestMethod.GET)
	public String test(@PathVariable("id") Long id, Model model) {

        Record record = new Record();
        record.setPatientId("1");
        record.setDoneDay(new Date());
        record.setIsFinished(true);
        Scale scale = Scale.findScale(id);
        record.setScale(scale);
		model.addAttribute("record", record);
        model.addAttribute("record_doneday_date_format", DateTimeFormat.patternForStyle("S-", LocaleContextHolder.getLocale()));
        List dependencies = new ArrayList();
        if (Scale.countScales() == 0) {
            dependencies.add(new String[]{"scale", "scales"});
        }
		model.addAttribute("scale", scale);
        model.addAttribute("itemId", id);
        model.addAttribute("dependencies", dependencies);
        return "scales/test";
	    
		
	}
	@ModelAttribute("scales")
    public Collection<Scale> populateScales() {
        return Scale.findAllScales();
    }

	@RequestMapping(method = RequestMethod.GET)
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model model) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            model.addAttribute("scales", Scale.findScaleEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) Scale.countScales() / sizeNo;
            model.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            model.addAttribute("scales", Scale.findAllScales());
        }
        return "scales/list";
    }

	@RequestMapping(method = RequestMethod.PUT)
    public String update(@Valid Scale scale, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            model.addAttribute("scale", scale);
            return "scales/update";
        }
        scale.merge();
        return "redirect:/scales/" + encodeUrlPathSegment(scale.getId().toString(), request);
    }

	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("scale", Scale.findScale(id));
        return "scales/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model model) {
        Scale.findScale(id).remove();
        model.addAttribute("page", (page == null) ? "1" : page.toString());
        model.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/scales?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }

	@ModelAttribute("answers")
    public Collection<Answer> populateAnswers() {
        return Answer.findAllAnswers();
    }

	@ModelAttribute("questions")
    public Collection<Question> populateQuestions() {
        return Question.findAllQuestions();
    }

	@ModelAttribute("records")
    public Collection<Record> populateRecords() {
        return Record.findAllRecords();
    }

	@ModelAttribute("scaletypes")
    public Collection<ScaleType> populateScaleTypes() {
        return Arrays.asList(ScaleType.class.getEnumConstants());
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
