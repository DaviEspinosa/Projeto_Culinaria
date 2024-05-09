package webapp.projeto_culinaria.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import webapp.projeto_culinaria.Model.UserDb;
import webapp.projeto_culinaria.Repository.UserRepository;

@Controller
public class IndexController {
    @GetMapping("/")
    public String acessHome() {
        return "index";
    }

    @Autowired
    UserRepository ur;

    @GetMapping("/login")
    public String acessLogin(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId != null) {
            UserDb userDb = ur.findById(userId).orElse(null);
            if (userDb != null) {
                model.addAttribute("userDb", userDb);
                return "internals/user-page";
            }
        }
        return "authentication/login";
    }

    @GetMapping("/register")
    public String acessRegister() {
        return "authentication/register";
    }
}
