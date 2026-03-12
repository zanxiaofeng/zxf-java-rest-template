package zxf.java.http.config;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.util.TimeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Configuration
public class RestTemplateConfig {
    @Bean("defaultRestTemplate")
    public RestTemplate defaultRestTemplate(@Autowired RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

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
