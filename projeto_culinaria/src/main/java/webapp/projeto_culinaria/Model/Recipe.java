package webapp.projeto_culinaria.Model;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Recipe implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rec_id;
    private String title;
    private String description;
    private String recipe;
    @ManyToOne(cascade = CascadeType.ALL)
    private UserDb userDb;

    public Long getRec_id() {
        return rec_id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getRecipe() {
        return recipe;
    }
    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }
    public UserDb getUser() {
        return userDb;
    }
    public void setUser(UserDb userDb) {
        this.userDb = userDb;
    }
}