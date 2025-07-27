package markigor.io.newscrawler.application.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "crawler_mt")
public class Crawler extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crawler_mt_id")
    Long Id;

    @Column(name = "crawler_site_mt_id")
    Long crawlerSiteId;

    @Size(max = 50)
    @Column(name = "key_word", length = 50)
    String keyWord;

    @Column(name = "duration")
    Long duration;

    @Column(name = "is_scrap")
    Boolean isScrap;

    @OneToMany(mappedBy = "crawlerData", fetch = FetchType.LAZY)
    @JsonBackReference
    List<UserService> userService;
}
