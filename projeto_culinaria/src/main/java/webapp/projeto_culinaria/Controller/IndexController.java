package webapp.projeto_culinaria.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class IndexController {
    @GetMapping("/")
    public String acessHome() {
        return "index";
    }

    @GetMapping("/login")
    public String acessLogin() {
        return "authentication/login";
    }

    @GetMapping("/register")
    public String acessRegister() {
        return "authentication/register";
    }
}
