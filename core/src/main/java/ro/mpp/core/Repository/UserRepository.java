package ro.mpp.core.Repository;

import org.springframework.data.jpa.repository.Query;
import ro.mpp.core.Domain.User;

public interface UserRepository extends CatalogRepository<User, Integer> {

    @Query("select u from User u where u.username=?1")
    User getUserByUsername(String username);
}
