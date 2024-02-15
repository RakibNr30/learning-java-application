package com.resume.controller.dashboard;

import com.resume.entity.User;
import com.resume.entity.UserAward;
import com.resume.helpers.NotifierHelper;
import com.resume.helpers.ValidationHelper;
import com.resume.service.UserAwardService;
import com.resume.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/dashboard/user-award")
public class UserAwardController {

    private final UserService userService;

    private final UserAwardService userAwardService;

    @Autowired
    public UserAwardController(UserService userService, UserAwardService userAwardService) {
        this.userService = userService;
        this.userAwardService = userAwardService;
    }

    @ModelAttribute
    public void common(Model model) {
        model.addAttribute("title", "User Award");
    }

    @ModelAttribute
    public UserAward getUserAward() {
        return new UserAward();
    }

    @RequestMapping
    public String index(Model model) {
        List<UserAward> userAwards = this.userAwardService.getAll();
        model.addAttribute("userAwards", userAwards);

        return "dashboard/user-award/index";
    }

    @RequestMapping("/create")
    public String create(Model model) {
        List<User> users = this.userService.getAll();

        model.addAttribute("users", users);
        return "dashboard/user-award/create";
    }

    @RequestMapping(value = "/store", method = RequestMethod.POST)
    public String store(@Valid @ModelAttribute UserAward userAward, BindingResult result, @RequestParam("user_id") Long userId, RedirectAttributes attributes) {

        if (result.hasErrors()) {
            new ValidationHelper(attributes).model("userAward", userAward).bind(result);
            return "redirect:/dashboard/user-award/create";
        }

        User user = this.userService.get(userId);

        if (user == null) {
            new NotifierHelper(attributes).message("User not found.").error();
        }

        try {
            userAward.setUser(user);
            this.userAwardService.save(userAward);
            new NotifierHelper(attributes).message("User award added successfully.").success();
        } catch (Exception e) {
            System.err.println(e.getMessage());

            new NotifierHelper(attributes).message("User award can not be added.").error();
            return "redirect:/dashboard/user-award/create";
        }

        return "redirect:/dashboard/user-award";
    }

    @RequestMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model, RedirectAttributes attributes) {
        UserAward userAward = this.userAwardService.get(id);

        if (userAward == null) {
            new NotifierHelper(attributes).message("User award not found.").error();
            return "redirect:/dashboard/user-award";
        }

        model.addAttribute("userAward", userAward);

        return "dashboard/user-award/show";
    }

    @RequestMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model, RedirectAttributes attributes) {

        UserAward userAward = this.userAwardService.get(id);

        if (userAward == null) {
            new NotifierHelper(attributes).message("User award not found.").error();
            return "redirect:/dashboard/user-award";
        }

        List<User> users = this.userService.getAll();

        model.addAttribute("users", users);
        model.addAttribute("userAward", userAward);

        return "dashboard/user-award/edit";
    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public String update(@PathVariable("id") Long id, @Valid @ModelAttribute UserAward userAward, BindingResult result, @RequestParam("user_id") Long userId, RedirectAttributes attributes) {

        if (result.hasErrors()) {
            new ValidationHelper(attributes).model("userAward", userAward).bind(result);
            return "redirect:/dashboard/user-award/" + id + "/edit";
        }

        UserAward updatableUserAward = this.userAwardService.get(id);

        if (updatableUserAward == null) {
            new NotifierHelper(attributes).message("User award not found.").error();
            return "redirect:/dashboard-award/user";
        }

        User user = this.userService.get(userId);

        if (user == null) {
            new NotifierHelper(attributes).message("User not found.").error();
        }

        try {
            userAward.setUser(user);
            this.userAwardService.update(userAward);
            new NotifierHelper(attributes).message("User award updated successfully.").success();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            new NotifierHelper(attributes).message("User award can not be updated.").error();
        }

        return "redirect:/dashboard/user-award/" + id + "/edit";
    }

    @RequestMapping(value = "/{id}/destroy", method = RequestMethod.POST)
    public String destroy(@PathVariable("id") Long id, RedirectAttributes attributes) {

        UserAward userAward = this.userAwardService.get(id);

        if (userAward == null) {
            new NotifierHelper(attributes).message("User award not found.").error();
        }

        try {
            this.userAwardService.delete(userAward);
            new NotifierHelper(attributes).message("User award deleted successfully.").success();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            new NotifierHelper(attributes).message("User award can not ber deleted.").error();
        }

        return "redirect:/dashboard/user-award";
    }
}
