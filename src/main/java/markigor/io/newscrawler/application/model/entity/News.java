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
@Table(name = "news_lt")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_lt_id")
    Long Id;

    @Column(name = "crawler_api_mt_id")
    Long crawlerApiId;

    @Column(name = "crawler_mt_id")
    Long crawlerId;

    @Column(name = "news_title")
    String newsTitle;

    @Column(name = "news_post")
    String newsPost;

}
