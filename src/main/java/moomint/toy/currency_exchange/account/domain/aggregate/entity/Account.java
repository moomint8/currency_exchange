package moomint.toy.currency_exchange.account.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;
import moomint.toy.currency_exchange.user.domain.aggregate.entity.User;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@ToString
@SQLDelete(sql = "UPDATE tbl_account SET DELETED_AT = CURRENT_DATE WHERE ID = ?")
@SQLRestriction("DELETED_AT IS NULL")
@Table(name = "tbl_account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    // 계좌 번호
    @Column(name = "ACCOUNT_NO", unique = true, nullable = false)
    private String accountNo;

    // 통화 정보
    @Column(name = "CURRENCY", nullable = false)
    private String currency;

    // 잔고
    @Column(name = "BALANCE", nullable = false)
    private BigDecimal balance;

    // 계좌 생성일
    @CreatedDate
    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    // 계좌 최근 수정일
    @UpdateTimestamp
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt = LocalDateTime.now();

    // 계좌 삭제일
    @Column(name = "DELETED_AT")
    private LocalDateTime deletedAt;

    // 계좌 소유자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    public void updateBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Builder
    public Account(Long id, String accountNo, String currency, BigDecimal balance, LocalDateTime createdAt,
                   LocalDateTime updatedAt, LocalDateTime deletedAt, User user) {
        this.id = id;
        this.accountNo = accountNo;
        this.currency = currency;
        this.balance = balance;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.user = user;
    }
}
