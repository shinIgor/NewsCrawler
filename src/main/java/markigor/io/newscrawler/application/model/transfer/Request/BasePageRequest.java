package markigor.io.newscrawler.application.model.transfer.Request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BasePageRequest {
    @NotNull
    Integer pageSize;

    @NotNull
    Integer page;

    String keyword;

    String type;

    public Pageable makePageable() {
        return PageRequest.of(page, pageSize);
    }
    public Pageable makePageable(Sort sort) {
        return PageRequest.of(page, pageSize, sort);
    }
}
