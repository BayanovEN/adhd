package com.jieyou.adhd.web;

import com.jieyou.adhd.domain.Conclusion;
import com.jieyou.adhd.domain.Scale;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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

@RooWebScaffold(path = "conclusions", formBackingObject = Conclusion.class)
@RequestMapping("/conclusions")
@Controller
public class ConclusionController {

	@RequestMapping(method = RequestMethod.POST)
    public String create(@Valid Conclusion conclusion, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            model.addAttribute("conclusion", conclusion);
            return "conclusions/create";
        }
        conclusion.persist();
        return "redirect:/conclusions/" + encodeUrlPathSegment(conclusion.getId().toString(), request);
    }

	@RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model model) {
        model.addAttribute("conclusion", new Conclusion());
        List dependencies = new ArrayList();
        if (Scale.countScales() == 0) {
            dependencies.add(new String[]{"scale", "scales"});
        }
        model.addAttribute("dependencies", dependencies);
        return "conclusions/create";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("conclusion", Conclusion.findConclusion(id));
        model.addAttribute("itemId", id);
        return "conclusions/show";
    }

	@RequestMapping(method = RequestMethod.GET)
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model model) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            model.addAttribute("conclusions", Conclusion.findConclusionEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) Conclusion.countConclusions() / sizeNo;
            model.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            model.addAttribute("conclusions", Conclusion.findAllConclusions());
        }
        return "conclusions/list";
    }

	@RequestMapping(method = RequestMethod.PUT)
    public String update(@Valid Conclusion conclusion, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            model.addAttribute("conclusion", conclusion);
            return "conclusions/update";
        }
        conclusion.merge();
        return "redirect:/conclusions/" + encodeUrlPathSegment(conclusion.getId().toString(), request);
    }

	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("conclusion", Conclusion.findConclusion(id));
        return "conclusions/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model model) {
        Conclusion.findConclusion(id).remove();
        model.addAttribute("page", (page == null) ? "1" : page.toString());
        model.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/conclusions?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }

	@ModelAttribute("scales")
    public Collection<Scale> populateScales() {
        return Scale.findAllScales();
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
