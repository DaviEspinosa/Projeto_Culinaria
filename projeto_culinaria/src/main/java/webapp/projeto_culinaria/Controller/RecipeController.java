package webapp.projeto_culinaria.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import webapp.projeto_culinaria.Model.Recipe;
import webapp.projeto_culinaria.Model.UserDb;
import webapp.projeto_culinaria.Repository.RecipeRepository;
import webapp.projeto_culinaria.Repository.UserRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
public class RecipeController {

    @Autowired
    RecipeRepository rp;

    @Autowired
    UserRepository ur;

    @PostMapping("form-share-recipe")
    public String shareRecipe(Recipe recipe) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUserName = authentication.getName();

            UserDb user = ur.findByEmail(currentUserName);

            recipe.setUser(user);

            rp.save(recipe);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:internals/user-page";
    }
}
