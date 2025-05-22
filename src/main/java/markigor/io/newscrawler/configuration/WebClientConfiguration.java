package markigor.io.newscrawler.configuration;

import io.netty.channel.ChannelOption;
import io.netty.channel.socket.nio.NioChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import io.netty.resolver.DefaultAddressResolverGroup;
import jdk.net.ExtendedSocketOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfiguration {

    @Bean
    public WebClient webClient() {
        HttpClient client = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000) // Remote connect timeout
                .option(ChannelOption.SO_KEEPALIVE, true) // use keep-alive
                .option(NioChannelOption.of(ExtendedSocketOptions.TCP_KEEPIDLE) , 300) // send keep-alive probe after 5M idle connection
                .option(NioChannelOption.of(ExtendedSocketOptions.TCP_KEEPINTERVAL), 30) // send keep-alive probe at duration 60S
                .option(NioChannelOption.of(ExtendedSocketOptions.TCP_KEEPCOUNT), 8) // Maximum send keep-alive probe count
                .resolver(DefaultAddressResolverGroup.INSTANCE)
                .responseTimeout(Duration.ofMillis(10000)) // response timeout
                .doOnConnected(
                        conn -> conn
                                .addHandlerLast(new ReadTimeoutHandler(5, TimeUnit.SECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(5))
                );

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(client))
                .build();
    }

}
