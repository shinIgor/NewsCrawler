package markigor.io.newscrawler.application.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import markigor.io.newscrawler.application.model.entity.convert.JsonToMapConverter;

import java.util.Map;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "crawler_site_mt")
public class CrawlerSite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crawler_site_mt_id")
    Long Id;

    @Size(max = 20)
    @Column(name = "site_name", length = 20)
    String siteName;

    @Column(name = "url")
    String url;

    @Convert(converter = JsonToMapConverter.class)
    @Column(name = "header", columnDefinition = "JSON")
    Map<String, Object> header;

    @Column(name = "body")
    String body;
}
