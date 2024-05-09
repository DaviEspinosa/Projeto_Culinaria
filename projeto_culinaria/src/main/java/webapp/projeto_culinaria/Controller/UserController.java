package webapp.projeto_culinaria.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import webapp.projeto_culinaria.Model.UserDb;
import webapp.projeto_culinaria.Repository.UserRepository;

@Controller
public class UserController {

    @Autowired
    UserRepository ur;

    boolean acessUser = false;

    @GetMapping("/user")
    public String acessUserPage() {
        return "internals/user-page";
    }

    @PostMapping("form-login")
    public String acessLogin(@RequestParam String email,
            @RequestParam String password, Model model) {
        try {
            boolean verifyEmail = ur.existsByEmail(email);
            boolean verifyPassword = ur.findByEmail(email).getPassword().equals(password);
            String url = "";
            if (verifyEmail && verifyPassword) {
                acessUser = true;
                url = "internals/user-page";
            } else {
                url = "redirect:/login";
                model.addAttribute("errorMessage", "Erro de login: Email ou senha incorretos");
            }
            return url;
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Erro de login: " + e.getMessage());
            return "redirect:/login";
        }
    }

    @PostMapping("form-register")
public String userRegister(UserDb userDb, Model model) {
    try {
        boolean emailExists = ur.existsByEmail(userDb.getEmail());
        if (emailExists) {
            model.addAttribute("errorMessage", "Erro ao cadastrar: E-mail já está em uso");
            return "authentication/register"; 
        }
        
        if (userDb.getUser_id() == null) {
            ur.save(userDb);
            model.addAttribute("successMessage", "Novo usuário cadastrado com sucesso");
        } else {
            Optional<UserDb> existingUserOptional = ur.findById(userDb.getUser_id());
            if (existingUserOptional.isPresent()) {
                UserDb existingUser = existingUserOptional.get();
                existingUser.setName(userDb.getName());
                existingUser.setEmail(userDb.getEmail());
                ur.save(existingUser);
                model.addAttribute("successMessage", "Informações do usuário atualizadas com sucesso");
            } else {
                model.addAttribute("errorMessage", "Usuário não encontrado para atualização");
            }
        }
    } catch (Exception e) {
        model.addAttribute("errorMessage", "Erro ao tentar cadastrar/atualizar o usuário: " + e.getMessage());
        e.printStackTrace();
        return "errorPage";
    }

    return "authentication/login";
}

    @PostMapping("/delete-user")
    public String deleteUser(@RequestParam(required = false) Long user_id, Model model) {
        try {
            Optional<UserDb> userOptional = ur.findById(user_id);

            if (userOptional.isPresent()) {
                ur.deleteById(user_id);
                model.addAttribute("successMessage", "Usuário excluído com sucesso");
            } else {
                model.addAttribute("errorMessage", "Usuário não encontrado");
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Erro ao tentar excluir o usuário: " + e.getMessage());
            e.printStackTrace();
        }

        return "redirect:/user";
    }

}