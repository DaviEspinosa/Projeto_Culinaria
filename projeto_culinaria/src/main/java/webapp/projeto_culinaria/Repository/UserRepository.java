package webapp.projeto_culinaria.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import webapp.projeto_culinaria.Model.UserDb;

public interface UserRepository extends CrudRepository<UserDb, Long>{
    @Query("SELECT a FROM UserDb a")
    List<UserDb> findByIdList();

    UserDb findByEmail(String email);
    boolean existsByEmail(String email);
}
