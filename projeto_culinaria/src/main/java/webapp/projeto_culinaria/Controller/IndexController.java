package webapp.projeto_culinaria.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import webapp.projeto_culinaria.Model.Recipe;
import webapp.projeto_culinaria.Model.UserDb;
import webapp.projeto_culinaria.Repository.RecipeRepository;
import webapp.projeto_culinaria.Repository.UserRepository;

@Controller
public class IndexController {

    @Autowired
    RecipeRepository rp;

    @GetMapping("/")
    public String acessHome(Model model, HttpSession session) {
        Iterable<Recipe> recipeIterable = rp.findAll();
        List<Recipe> recipes = new ArrayList<>();
        recipeIterable.forEach(recipes::add);

        model.addAttribute("recipes", recipes);
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
    public String acessRegister(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId != null) {
            UserDb userDb = ur.findById(userId).orElse(null);
            if (userDb != null) {
                model.addAttribute("userDb", userDb);
                return "internals/user-page";
            }
        }
        return "authentication/register";
    }
}
