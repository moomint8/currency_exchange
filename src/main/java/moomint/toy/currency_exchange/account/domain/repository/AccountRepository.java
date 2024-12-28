package moomint.toy.currency_exchange.account.domain.repository;

import moomint.toy.currency_exchange.account.domain.aggregate.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Boolean existsByAccountNo(String accountNo);
    List<Account> findAllByUserId(Long userId);
    Account findByAccountNo(String accountNo);
}
