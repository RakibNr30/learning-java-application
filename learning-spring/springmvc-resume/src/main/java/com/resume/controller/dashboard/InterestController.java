package com.resume.controller.dashboard;

import com.resume.entity.Interest;
import com.resume.helpers.NotifierHelper;
import com.resume.helpers.ValidationHelper;
import com.resume.service.InterestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/dashboard/interest")
public class InterestController {

    @Autowired
    private InterestService interestService;

    @ModelAttribute
    public void common(Model model) {
        model.addAttribute("title", "Interest");
    }

    @ModelAttribute
    public Interest getInterest() {
        return new Interest();
    }

    @RequestMapping
    public String index(Model model) {
        List<Interest> interests = this.interestService.getAll();
        model.addAttribute("interests", interests);

        return "dashboard/interest/index";
    }

    @RequestMapping("/create")
    public String create() {
        return "dashboard/interest/create";
    }

    @RequestMapping(value = "/store", method = RequestMethod.POST)
    public String store(@Valid @ModelAttribute Interest interest, BindingResult result, RedirectAttributes attributes) {

        if (result.hasErrors()) {
            new ValidationHelper(attributes).model("interest", interest).bind(result);
            return "redirect:/dashboard/interest/create";
        }

        try {
            this.interestService.save(interest);
            new NotifierHelper(attributes).message("Interest added successfully.").success();
        } catch (Exception e) {
            System.err.println(e.getMessage());

            new NotifierHelper(attributes).message("Interest can not be added.").error();
            return "redirect:/dashboard/interest/create";
        }

        return "redirect:/dashboard/interest";
    }

    @RequestMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model, RedirectAttributes attributes) {
        Interest interest = this.interestService.get(id);

        if (interest == null) {
            new NotifierHelper(attributes).message("Interest not found.").error();
            return "redirect:/dashboard/interest";
        }

        model.addAttribute("interest", interest);

        return "dashboard/interest/show";
    }

    @RequestMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model, RedirectAttributes attributes) {

        Interest interest = this.interestService.get(id);

        if (interest == null) {
            new NotifierHelper(attributes).message("Interest not found.").error();
            return "redirect:/dashboard/interest";
        }

        model.addAttribute("interest", interest);

        return "dashboard/interest/edit";
    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public String update(@PathVariable("id") Long id, @Valid @ModelAttribute Interest interest, BindingResult result, RedirectAttributes attributes) {

        if (result.hasErrors()) {
            new ValidationHelper(attributes).model("interest", interest).bind(result);
            return "redirect:/dashboard/interest/" + id + "/edit";
        }

        Interest updatableInterest = this.interestService.get(id);

        if (updatableInterest == null) {
            new NotifierHelper(attributes).message("Interest not found.").error();
            return "redirect:/dashboard/interest";
        }

        try {
            this.interestService.update(interest);
            new NotifierHelper(attributes).message("Interest updated successfully.").success();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            new NotifierHelper(attributes).message("Interest can not be updated.").error();
        }

        return "redirect:/dashboard/interest/" + id + "/edit";
    }

    @RequestMapping(value = "/{id}/destroy", method = RequestMethod.POST)
    public String destroy(@PathVariable("id") Long id, RedirectAttributes attributes) {

        Interest interest = this.interestService.get(id);

        if (interest == null) {
            new NotifierHelper(attributes).message("Interest not found.").error();
        }

        try {
            this.interestService.delete(interest);
            new NotifierHelper(attributes).message("Interest deleted successfully.").success();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            new NotifierHelper(attributes).message("Interest can not be deleted.").error();
        }

        return "redirect:/dashboard/interest";
    }
}