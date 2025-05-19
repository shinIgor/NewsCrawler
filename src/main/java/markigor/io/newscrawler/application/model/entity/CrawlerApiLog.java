package markigor.io.newscrawler.application.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "crawler_api_lt")
public class CrawlerApiLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crawler_api_lt_id")
    Long Id;

    @Column(name = "crawler_api_mt_id")
    Long crawlerApiId;

    @Column(name = "before_data")
    String beforeData;

    @Column(name = "after_data")
    String afterData;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    LocalDateTime createdAt;
}
