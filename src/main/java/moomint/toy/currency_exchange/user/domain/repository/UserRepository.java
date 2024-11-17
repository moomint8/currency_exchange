package moomint.toy.currency_exchange.user.domain.repository;

import moomint.toy.currency_exchange.user.domain.aggregate.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByUsername(String nickname);
    Boolean existsByEmail(String email);

    User findByUsername(String username);
}
