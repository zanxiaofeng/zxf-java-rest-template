package zxf.java.http.config;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClients;
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
        HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        return new RestTemplate(httpComponentsClientHttpRequestFactory);
    }

    @Bean("apacheRestTemplateWithPool")
    public RestTemplate apacheRestTemplateWithPool() {
        HttpClient httpClient = HttpClients.custom()
                .setMaxConnTotal(200)
                .setMaxConnPerRoute(20)
                .evictExpiredConnections()
                .evictIdleConnections(30, TimeUnit.MINUTES)
                .setConnectionTimeToLive(60, TimeUnit.MINUTES)
//                .setDefaultRequestConfig(RequestConfig.custom()
//                        .setRedirectsEnabled(false)
//                        .setConnectionRequestTimeout(30 * 1000)
//                        .setSocketTimeout(30 * 1000)
//                        .setConnectTimeout(10 * 1000)
//                        .setCookieSpec(CookieSpecs.IGNORE_COOKIES)
//                        .build())
                .build();
        HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        return new RestTemplate(httpComponentsClientHttpRequestFactory);
    }
}
