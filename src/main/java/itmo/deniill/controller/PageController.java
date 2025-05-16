package itmo.deniill.controller;

import itmo.deniill.dao.model.User;
import itmo.deniill.dao.model.UserProfile;
import itmo.deniill.service.services.interfaces.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
@AllArgsConstructor
public class PageController {

    @Autowired private final UserService userService;
    @Autowired private final AuthService authService;
    @Autowired private final UserProfileService userProfileService;
    @Autowired private final NewsService newsService;
    @Autowired private final BookService bookService;

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) {
            model.addAttribute("profile", principal.getAttributes());
        }
        return "index";
    }


    @GetMapping("/authors")
    public String authors(Model model, @AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) {
            model.addAttribute("profile", principal.getAttributes());
        }
        return "authors";
    }

    @GetMapping("/genres")
    public String genres() {
        return "genres";
    }

    @GetMapping("/profile")
    public String profile(Model model, @AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) {
            model.addAttribute("profile", principal.getAttributes());
            Map<String, Object> attributes = principal.getAttributes();
            User user = authService.registerUser(principal);
            UserProfile profile = userProfileService.findByUserId(user.getId());
        }
        return "profile";
    }

    @GetMapping("/whatToRead")
    public String whatToRead(Model model, @AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) {
            model.addAttribute("profile", principal.getAttributes());
        }
        return "whatToRead";
    }
}
