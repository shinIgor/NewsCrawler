package markigor.io.newscrawler.application.model.entity.convert;


import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import markigor.io.newscrawler.application.util.JsonUtil;

import java.util.Map;

@Converter
public class JsonToMapConverter implements AttributeConverter<Map<String, Object>, String> {
    @Override
    public String convertToDatabaseColumn(Map<String, Object> attribute) {
        if (attribute == null) return null;
        return JsonUtil.toString(attribute);
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String dbData) {
        if (dbData == null) dbData = "{}";
        return JsonUtil.convert(dbData, new TypeReference<>() {});
    }
}
