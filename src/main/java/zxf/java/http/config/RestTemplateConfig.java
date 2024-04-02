package zxf.java.http.config;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Configuration
public class RestTemplateConfig {
    @Bean("simpleRestTemplate")
    public RestTemplate simpleRestTemplate() {
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        return new RestTemplate(simpleClientHttpRequestFactory);
    }

    @Bean("okHttp3RestTemplate")
    public RestTemplate okHttp3RestTemplate() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectionPool(new ConnectionPool(200, 30, TimeUnit.MINUTES))
                .build();
        OkHttp3ClientHttpRequestFactory okHttp3ClientHttpRequestFactory = new OkHttp3ClientHttpRequestFactory(client);
        return new RestTemplate(okHttp3ClientHttpRequestFactory);
    }

    @Bean("defaultApacheRestTemplate")
    public RestTemplate defaultApacheRestTemplate() {
        HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        return new RestTemplate(httpComponentsClientHttpRequestFactory);
    }

    @Bean("apacheRestTemplateWithPool")
    public RestTemplate apacheRestTemplateWithPool() {
        HttpClient httpClient = HttpClients.custom()
                .setConnectionTimeToLive(15, TimeUnit.MINUTES)
                .setMaxConnTotal(1000)
                .setMaxConnPerRoute(20)
                .evictExpiredConnections()
                .evictIdleConnections(5, TimeUnit.MINUTES)
                .build();
        HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        return new RestTemplate(httpComponentsClientHttpRequestFactory);
    }
}
