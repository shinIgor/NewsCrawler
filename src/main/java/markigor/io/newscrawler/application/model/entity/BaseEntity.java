package markigor.io.newscrawler.application.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@MappedSuperclass
@FieldDefaults(level = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BaseEntity {

    @CreationTimestamp
    @Column(name = "updatedAt", nullable = false)
    LocalDateTime updatedAt;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    LocalDateTime createdAT;
}
