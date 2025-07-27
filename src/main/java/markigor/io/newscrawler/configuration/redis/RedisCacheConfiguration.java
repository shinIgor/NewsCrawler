package markigor.io.newscrawler.configuration.redis;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({RedisCacheProperties.class})
public class RedisCacheConfiguration {

    private final RedisCacheProperties redisCacheProperties;
    private final ObjectMapper objectMapper;

    // Redis connection
    @Bean
    RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName(redisCacheProperties.getHost());
        redisConfig.setPort(redisCacheProperties.getPort());
        redisConfig.setDatabase(redisCacheProperties.getDatabase());

        SocketOptions socketOptions = SocketOptions.builder()
            .connectTimeout(redisCacheProperties.getTimeout())
            .build();

        ClientOptions clientOptions = ClientOptions.builder()
            .socketOptions(socketOptions)
            .autoReconnect(true)
            //.disconnectedBehavior()
            .build();

        return new LettuceConnectionFactory(redisConfig, LettuceClientConfiguration.builder()
            .clientOptions(clientOptions).build());
    }

    @Bean
    RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());

        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(
            new Jackson2JsonRedisSerializer<>(objectMapper, Object.class));
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(objectMapper, Object.class));
        return template;
    }
}
