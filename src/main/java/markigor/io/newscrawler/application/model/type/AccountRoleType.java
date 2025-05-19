package markigor.io.newscrawler.application.model.type;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;

@Getter
public enum AccountRoleType {
    UNKNOWN("unknown", 0),
    USER("user", 1),
    ADMIN("admin", 2);


    private final String role;
    private final Integer value;


    AccountRoleType(String role, Integer value) {
        this.role = role;
        this.value = value;
    }

    private static AccountRoleType find(Predicate<AccountRoleType> predicate) {
        return Arrays.stream(values())
                .filter(predicate)
                .findAny()
                .orElse(null);
    }

    public static AccountRoleType from(String role) {
        return find(entity -> Objects.equals(entity.role, role));
    }
}
