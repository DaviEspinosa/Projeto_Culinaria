package webapp.projeto_culinaria.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import webapp.projeto_culinaria.Model.Recipe;
import webapp.projeto_culinaria.Repository.RecipeRepository;

@Controller
public class RecipeController {

    @Autowired
    RecipeRepository rp;

    @PostMapping("form-share-recipe")
    public String shareRecipe(Recipe recipe) {
        try {
            rp.save(recipe);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}
