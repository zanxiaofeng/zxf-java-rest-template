package zxf.java.http.config;

import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.util.TimeValue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Configuration
public class RestTemplateConfig {
    @Bean("defaultRestTemplate")
    public RestTemplate defaultRestTemplate() {
        // RestTemplateBuilder was removed in Spring Boot 4.x; use a plain default RestTemplate.
        // RestTemplate itself is feature-complete (deprecated in docs as of Spring Framework 7.0).
        return new RestTemplate();
    }

    @Bean("defaultRestClient")
    public RestClient defaultRestClient() {
        // RestClient is the modern, fluent successor to RestTemplate (Spring Framework 6.1+),
        // recommended over the deprecated RestTemplate. Uses the default ClientHttpRequestFactory.
        return RestClient.create();
    }

    @Bean("simpleRestTemplate")
    public RestTemplate simpleRestTemplate() {
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        return new RestTemplate(simpleClientHttpRequestFactory);
    }

    @Bean("defaultApacheRestTemplate")
    public RestTemplate defaultApacheRestTemplate() {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        return new RestTemplate(httpComponentsClientHttpRequestFactory);
    }

    @Bean("apacheRestTemplateWithPool")
    public RestTemplate apacheRestTemplateWithPool() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(200);
        connectionManager.setDefaultMaxPerRoute(20);

        RequestConfig requestConfig = RequestConfig.custom()
                .setRedirectsEnabled(false)
                .setConnectionRequestTimeout(30, TimeUnit.SECONDS)
                .setResponseTimeout(30, TimeUnit.SECONDS)
                .build();

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(requestConfig)
                .evictExpiredConnections()
                .evictIdleConnections(TimeValue.ofMinutes(30))
                .build();

        HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        return new RestTemplate(httpComponentsClientHttpRequestFactory);
    }
}
