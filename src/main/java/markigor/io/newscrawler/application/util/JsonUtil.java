package markigor.io.newscrawler.application.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.StreamWriteFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import markigor.io.newscrawler.application.model.exception.CommonErrorMessage;
import markigor.io.newscrawler.application.model.exception.CommonException;
import markigor.io.newscrawler.configuration.objectmapper.FormatDefine;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Slf4j
@UtilityClass
public class JsonUtil<T> {
    private ObjectMapper objectMapper = null;

    public void initialize() {
        if (false == Objects.isNull(objectMapper))
            return;

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(FormatDefine.DATE)))
                .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(FormatDefine.DATETIME)))
                .addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(FormatDefine.DATE)))
                .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(FormatDefine.DATETIME)));

        objectMapper = JsonMapper.builder()
                .addModule(javaTimeModule)
                .enable(StreamWriteFeature.WRITE_BIGDECIMAL_AS_PLAIN)
                .enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT)
                .enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)          // BigDecimal 사용 지정
                .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)                // Enum 대소 문자 구분 하지 않게 지정
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)            // JSON에서 날짜를 문자열로 표시하도록 지정
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .build();
        log.info("JSON Utility initialized.");
    }
    public <T> String toString(T obj) {
        initialize();

        try {

            return objectMapper.writeValueAsString(obj);
        } catch (IOException ex) {
            log.error("Failed to convert JSON String. message: {}", ex.getMessage());

            throw new CommonException(CommonErrorMessage.FAIL_PARSE);
        }
    }

    public <T> T convert(Object obj, TypeReference<T> type) {
        initialize();

        try {
            return objectMapper.convertValue(obj, type);
        } catch (IllegalArgumentException ex) {
            log.error("Failed to convert Object. message: {}", ex.getMessage());
            throw new CommonException(CommonErrorMessage.FAIL_PARSE);
        }
    }

    public <T> T convert(String jsonStr, TypeReference<T> typeRefValue) {
        initialize();

        try {
            return objectMapper.readValue(jsonStr, typeRefValue);
        } catch (JsonMappingException ex) {
            log.error("Failed to JSON mapping error. message: {}", ex.getMessage());
            throw new CommonException(CommonErrorMessage.FAIL_PARSE);
        } catch (JsonProcessingException ex) {
            log.error("Failed to JSON processing error. message: {}", ex.getMessage());
            throw new CommonException(CommonErrorMessage.FAIL_PARSE);
        }
    }

    public <T> T convert(Object obj, Class<T> clazz) {
        initialize();
        try {
            return objectMapper.convertValue(obj, clazz);
        } catch (IllegalArgumentException ex) {
            log.error("Failed to convert Object. message: {}", ex.getMessage());
            throw new CommonException(CommonErrorMessage.FAIL_PARSE);
        }
    }
    public <T> T convert(String jsonStr, Class<T> clazz) {
        initialize();

        try {
            return objectMapper.readValue(jsonStr, clazz);
        } catch (JsonMappingException ex) {
            log.error("Failed to JSON mapping error. message: {}", ex.getMessage());
            throw new CommonException(CommonErrorMessage.FAIL_PARSE);
        } catch (JsonProcessingException ex) {
            log.error("Failed to JSON processing error. message: {}", ex.getMessage());
            throw new CommonException(CommonErrorMessage.FAIL_PARSE);
        }
    }
}
