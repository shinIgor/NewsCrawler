package markigor.io.newscrawler.application.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import markigor.io.newscrawler.application.model.entity.convert.AccountRoleTypeConverter;
import markigor.io.newscrawler.application.model.type.AccountRoleType;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "account_ut")
public class Account extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_ut_id")
    Long Id;

    @Size(max = 20)
    @Column(name = "user_id", length = 20)
    String userId;

    @Size(max = 64)
    @Column(name = "user_password", length = 64)
    String userPassword;

    @Size(max = 10)
    @Column(name = "user_name", length = 10)
    String userName;

    @Column(name = "account_role")
    @Convert(converter = AccountRoleTypeConverter.class)
    AccountRoleType accountRole;

    @Column(name = "last_processed_at", nullable = false)
    LocalDateTime lastProcessedAt;
}
