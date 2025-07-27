package markigor.io.newscrawler.application.model.entity.convert;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import markigor.io.newscrawler.application.model.type.AccountRoleType;

@Converter(autoApply = true)
public class AccountRoleTypeConverter implements AttributeConverter<AccountRoleType, String> {

    @Override
    public String convertToDatabaseColumn(AccountRoleType attribute) {
        return attribute.getRole();
    }

    @Override
    public AccountRoleType convertToEntityAttribute(String dbData) {
        return AccountRoleType.from(dbData);
    }
}
