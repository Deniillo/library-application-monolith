package itmo.deniill.controller;

import itmo.deniill.dao.model.News;
import itmo.deniill.dao.model.User;
import itmo.deniill.dao.model.UserProfile;
import itmo.deniill.dao.model.enums.NewsType;
import itmo.deniill.service.services.interfaces.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
        News n1 = new News(1,"Новость 1", "Книги книг кинки книги!", "news/news1.jpg", NewsType.BOOKS, LocalDateTime.now());
        News n2 = new News(2,"Новость 2", "аоаофоаоф книги", "news/news2.jpg", NewsType.BOOKS, LocalDateTime.now());
        News n3 = new News(3,"Книга 1", "Описание новинки книги 1", "news/book1.jpg", NewsType.BOOKS, LocalDateTime.now());
        News n4 = new News(4,"Книга 2", "Описание новинки книги 2", "news/book2.jpg", NewsType.BOOKS, LocalDateTime.now());
        newsService.save(n1);
        newsService.save(n2);
        newsService.save(n3);
        newsService.save(n4);
        model.addAttribute("generalNews", List.of(n1, n2));

        model.addAttribute("bookNews", List.of(n3, n4));
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
            User user = authService.registerUser(principal);
            UserProfile userProfile = userProfileService.findByUserId(user.getId());
            model.addAttribute("user", user);
            model.addAttribute("userProfile", userProfile);
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
