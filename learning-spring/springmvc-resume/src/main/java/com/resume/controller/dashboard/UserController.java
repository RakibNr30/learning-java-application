package com.resume.controller.dashboard;

import com.resume.entity.Skill;
import com.resume.entity.User;
import com.resume.helpers.ValidationHelper;
import com.resume.service.SkillService;
import com.resume.service.UserService;
import com.resume.helpers.NotifierHelper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller("dashboardUserController")
@RequestMapping("/dashboard/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SkillService skillService;

    @ModelAttribute
    public void common(Model model) {
        model.addAttribute("title", "User");
    }

    @ModelAttribute
    public User getUser() {
        return new User();
    }

    @RequestMapping
    public String index(Model model) {
        List<User> users = this.userService.getAll();
        model.addAttribute("users", users);

        return "dashboard/user/index";
    }

    @RequestMapping("/create")
    public String create(Model model) {
        List<Skill> skills = this.skillService.getAll();

        model.addAttribute("skills", skills);
        return "dashboard/user/create";
    }

    @RequestMapping(value = "/store", method = RequestMethod.POST)
    public String store(@Valid @ModelAttribute User user, BindingResult result, @RequestParam("selectedSkills") List<Integer> selectedSkills, RedirectAttributes attributes) {

        if (result.hasErrors()) {
            new ValidationHelper(attributes).model("user", user).bind(result);
            return "redirect:/dashboard/user/create";
        }

        List<Skill> skills = new ArrayList<>();

        try {
            for (Integer skillId: selectedSkills) {
                skills.add(this.skillService.get(skillId));
            }

            user.setSkills(skills);

            this.userService.save(user);
            new NotifierHelper(attributes).message("User added successfully.").success();
        } catch (Exception e) {
            System.err.println(e.getMessage());

            new NotifierHelper(attributes).message("User can not be added.").error();
            return "redirect:/dashboard/user/create";
        }

        return "redirect:/dashboard/user";
    }

    @RequestMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model, RedirectAttributes attributes) {
        User user = this.userService.get(id);

        if (user == null) {
            new NotifierHelper(attributes).message("User not found.").error();
            return "redirect:/dashboard/user";
        }

        model.addAttribute("user", user);

        return "dashboard/user/show";
    }

    @RequestMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model, RedirectAttributes attributes) {
        User user = this.userService.get(id);

        if (user == null) {
            new NotifierHelper(attributes).message("User not found.").error();
            return "redirect:/dashboard/user";
        }

        List<Skill> skills = this.skillService.getAll();

        model.addAttribute("user", user);
        model.addAttribute("skills", skills);

        return "dashboard/user/edit";
    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public String update(@PathVariable("id") Long id, @Valid @ModelAttribute User user, BindingResult result, @RequestParam("selectedSkills") List<Integer> selectedSkills, RedirectAttributes attributes) {

        if (result.hasErrors()) {
            new ValidationHelper(attributes).model("user", user).bind(result);
            return "redirect:/dashboard/user/" + id + "/edit";
        }

        User updatableUser = this.userService.get(id);

        if (updatableUser == null) {
            new NotifierHelper(attributes).message("User not found.").error();
            return "redirect:/dashboard/user";
        }

        List<Skill> skills = new ArrayList<>();

        try {
            for (Integer skillId: selectedSkills) {
                skills.add(this.skillService.get(skillId));
            }

            user.setSkills(skills);

            this.userService.update(user);
            new NotifierHelper(attributes).message("User updated successfully.").success();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            new NotifierHelper(attributes).message("User can not be updated.").error();
        }

        return "redirect:/dashboard/user/" + id + "/edit";
    }

    @RequestMapping(value = "/{id}/destroy", method = RequestMethod.POST)
    public String destroy(@PathVariable("id") Long id, RedirectAttributes attributes) {

        User user = this.userService.get(id);

        if (user == null) {
            new NotifierHelper(attributes).message("User not found.").error();
        }

        try {
            this.userService.delete(user);
            new NotifierHelper(attributes).message("User deleted successfully.").success();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            new NotifierHelper(attributes).message("User can not ber deleted.").error();
        }

        return "redirect:/dashboard/user";
    }
}
