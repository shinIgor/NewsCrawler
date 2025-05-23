package markigor.io.newscrawler.application.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "user_service_ut")
public class UserService extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_service_ut_id")
    Long Id;

    @Column(name = "account_ut_id")
    Long accountId;

    @Column(name = "crawler_mt_id")
    Long crawlerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "crawler_mt_id", referencedColumnName = "crawler_mt_id", insertable = false, updatable = false)
    Crawler crawlerData;

}
