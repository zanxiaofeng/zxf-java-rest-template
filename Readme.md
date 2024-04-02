# Key Classes of RestTemplate
- org.springframework.web.client.RestTemplate
- org.springframework.http.client.ClientHttpRequestFactory;
- org.springframework.http.client.ClientHttpRequest;
- org.springframework.http.client.ClientHttpResponse;
- org.springframework.http.converter.HttpMessageConverter;
- org.springframework.http.client.SimpleClientHttpRequestFactory
- org.springframework.http.client.HttpComponentsClientHttpRequestFactory
- org.springframework.http.client.OkHttp3ClientHttpRequestFactory

# Key Classes for apache http-client
- org.apache.http.impl.client.HttpClients;
- org.apache.http.client.HttpClient;
- org.apache.http.conn.HttpClientConnectionManager
- org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
- org.apache.http.impl.conn.BasicHttpClientConnectionManager
- org.apache.http.conn.ConnectionKeepAliveStrategy
- org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy

# Key Classes for apache ok-http-3
- okhttp3.OkHttpClient
- okhttp3.ConnectionPool
- okhttp3.internal.platform.Platform

# Test
- curl http://localhost:8080/clients/ok-http?target=www.sina.com
- netstat -lnpt
- jstack <pid>