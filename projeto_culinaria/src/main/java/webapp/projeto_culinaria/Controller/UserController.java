package webapp.projeto_culinaria.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import webapp.projeto_culinaria.Model.UserDb;
import webapp.projeto_culinaria.Repository.UserRepository;

@Controller
public class UserController {

    @Autowired
    UserRepository ur;

    boolean acessUser = false;

    @GetMapping("/user")
    public String acessUserPage(HttpSession session, Model model) {
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

    @PostMapping("/form-login")
    public String acessLogin(@RequestParam String email, @RequestParam String password, HttpSession session,
            Model model) {
        try {
            UserDb user = ur.findByEmail(email);
            if (user != null && user.getPassword().trim().equals(password.trim())) {
                session.setAttribute("userId", user.getUser_id());
                return "redirect:user";
            } else if (user == null) {
                model.addAttribute("errorMessage", "Erro: Usuário não encontrado");
                return "authentication/login";
            } else {
                model.addAttribute("errorMessage", "Erro: Senha incorreta");
                return "authentication/login";
            }
        } catch (Exception e) {
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
            } else {
                userDb.setPassword(userDb.getPassword().trim());
                userDb.setEmail(userDb.getEmail().trim());
                ur.save(userDb);
                model.addAttribute("successMessage", "Novo usuário cadastrado com sucesso");
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Erro ao tentar cadastrar/atualizar o usuário: " + e.getMessage());
            e.printStackTrace();
            return "errorPage";
        }

        return "redirect:/login";
    }

    @PostMapping("delete-user")
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

        return "redirect:/index";
    }

    @PostMapping("form-update-user")
    public String postMethodName(UserDb userDb, Model model) {
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
        return "redirect:/internal/user-page";
    }

}