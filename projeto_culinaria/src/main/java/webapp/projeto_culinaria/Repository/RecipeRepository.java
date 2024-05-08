package webapp.projeto_culinaria.Repository;

import org.springframework.data.repository.CrudRepository;

import webapp.projeto_culinaria.Model.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    
}