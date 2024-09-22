package moomint.toy.currency_exchange.user.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;
import moomint.toy.currency_exchange.user.domain.aggregate.enums.UserRole;
import moomint.toy.currency_exchange.user.domain.aggregate.enums.UserRoleConverter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SQLDelete(sql = "UPDATE tbl_user SET DELETED_AT = CURRENT_DATE WHERE ID = ?")
@SQLRestriction("DELETED_AT IS NULL")
@Table(name = "tbl_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    // 로그인 아이디
    @Column(name = "USERNAME")
    private String username;

    // 비밀번호
    @Column(name = "PASSWORD")
    private String password;

    // 회원 이름
    @Column(name = "NAME")
    private String name;

    // 이메일
    @Column(name = "EMAIL")
    private String email;

    // 회원 생성일
    @CreatedDate
    @Column(name = "CREATED_AT")
    private LocalDate createdAt;

    // 회원 수정일
    @UpdateTimestamp
    @Column(name = "UPDATED_AT")
    private LocalDate updatedAt = LocalDate.now();

    // 회원 탈퇴일
    @Column(name = "DELETED_AT")
    private LocalDate deletedAt;

    // 유저 권한
    @Column(name = "USER_ROLE")
    @Convert(converter = UserRoleConverter.class)
    private UserRole userRole;

    @Builder
    public User(String username, String password, String name, String email, LocalDate createdAt, LocalDate updatedAt,
                LocalDate deletedAt, UserRole userRole) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.userRole = userRole;
    }
}
