// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.jieyou.adhd.web;

import com.jieyou.adhd.domain.Question;
import com.jieyou.adhd.domain.Scale;
import java.io.UnsupportedEncodingException;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect QuestionController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST)
    public String QuestionController.create(@Valid Question question, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            model.addAttribute("question", question);
            return "questions/create";
        }
        question.persist();
        return "redirect:/questions/" + encodeUrlPathSegment(question.getId().toString(), request);
    }
    
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String QuestionController.createForm(Model model) {
        model.addAttribute("question", new Question());
        List dependencies = new ArrayList();
        if (Scale.countScales() == 0) {
            dependencies.add(new String[]{"scale", "scales"});
        }
        model.addAttribute("dependencies", dependencies);
        return "questions/create";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String QuestionController.show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("question", Question.findQuestion(id));
        model.addAttribute("itemId", id);
        return "questions/show";
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String QuestionController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model model) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            model.addAttribute("questions", Question.findQuestionEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) Question.countQuestions() / sizeNo;
            model.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            model.addAttribute("questions", Question.findAllQuestions());
        }
        return "questions/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public String QuestionController.update(@Valid Question question, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            model.addAttribute("question", question);
            return "questions/update";
        }
        question.merge();
        return "redirect:/questions/" + encodeUrlPathSegment(question.getId().toString(), request);
    }
    
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String QuestionController.updateForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("question", Question.findQuestion(id));
        return "questions/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String QuestionController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model model) {
        Question.findQuestion(id).remove();
        model.addAttribute("page", (page == null) ? "1" : page.toString());
        model.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/questions?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
    @ModelAttribute("scales")
    public Collection<Scale> QuestionController.populateScales() {
        return Scale.findAllScales();
    }
    
    String QuestionController.encodeUrlPathSegment(String pathSegment, HttpServletRequest request) {
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
