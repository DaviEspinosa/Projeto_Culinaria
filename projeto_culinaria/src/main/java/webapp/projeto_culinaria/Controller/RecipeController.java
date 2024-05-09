package webapp.projeto_culinaria.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import webapp.projeto_culinaria.Model.Recipe;
import webapp.projeto_culinaria.Model.UserDb;
import webapp.projeto_culinaria.Repository.RecipeRepository;
import webapp.projeto_culinaria.Repository.UserRepository;

@Controller
public class RecipeController {

    @Autowired
    RecipeRepository rp;

    @Autowired
    UserRepository ur;

    @PostMapping("form-share-recipe")
    public String shareRecipe(Recipe recipe, HttpServletRequest request, Model model, HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");
        UserDb userDb = ur.findById(userId).orElse(null);

        try {
            if (userId != null) {
                UserDb user = ur.findById(userId).orElse(null);

                if (user != null) {
                    recipe.setUser(user);
                    rp.save(recipe);
                } else {
                    System.out.println("Usuário não encontrado");
                }
            } else {
                System.out.println("ID do usuário não encontrado na sessão");
                return "authentication/login";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("userDb", userDb);
                Iterable<Recipe> recipeIterable = rp.findAll();
                List<Recipe> recipes = new ArrayList<>();
                recipeIterable.forEach(recipes::add);

                model.addAttribute("recipes", recipes);

        return "internals/user-page";
    }
}
