# Key Classes of RestTemplate
- org.springframework.web.client.RestTemplate
- org.springframework.http.client.ClientHttpRequestFactory;
- org.springframework.http.client.ClientHttpRequestInterceptor
- org.springframework.http.client.InterceptingClientHttpRequestFactory
- org.springframework.http.client.ClientHttpRequest;
- org.springframework.http.client.ClientHttpResponse;
- org.springframework.http.converter.HttpMessageConverter;
- org.springframework.http.client.SimpleClientHttpRequestFactory
- org.springframework.http.client.HttpComponentsClientHttpRequestFactory
- org.springframework.http.client.OkHttp3ClientHttpRequestFactory
- org.springframework.http.client.support.InterceptingHttpAccessor

# Key Classes for apache http-client
- org.apache.http.impl.client.HttpClients;
- org.apache.http.client.HttpClient;
- org.apache.http.conn.HttpClientConnectionManager
- org.apache.http.impl.conn.PoolingHttpClientConnectionManager(ValidateAfterInactivity=2000ms);
- org.apache.http.impl.conn.BasicHttpClientConnectionManager
- org.apache.http.impl.conn.DefaultHttpClientConnectionOperator
- org.apache.http.impl.conn.DefaultHttpResponseParser.parseHead
- org.apache.http.impl.io.SessionInputBufferImpl.streamRead
- org.apache.http.impl.conn.CPool
- org.apache.http.conn.ConnectionKeepAliveStrategy
- org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy
- org.apache.http.impl.client.IdleConnectionEvictor
- org.apache.http.client.config.CookieSpecs
- org.apache.http.client.protocol.RequestAddCookies
- org.apache.http.cookie.CookieSpec
- org.apache.http.cookie.CookieSpecProvider

# Key classes of java.net
- java.net.Socket.SocketInputStream.read(byte[], int, int)

# IO模型与应用层TCP连接状态检测
- 对非阻塞式IO模型，OS层可以通过Callback来通知应用层TCP连接状态的变化。
- 对阻塞式IO模型，只有在执行进入阻塞式IO相关的系统调用时，应用层才有可能检测到OS层TCP连接的状态变化。

# Checks whether this connection has gone down
## Description
- Network connections may get closed during some time of inactivity for several reasons. 
- The next time a read is attempted on such a connection it will throw an IOException. 
- This method tries to alleviate this inconvenience by trying to find out if a connection is still usable. 
- Implementations may do that by attempting a read with a very small timeout. 
- Thus this method may block for a small amount of time before returning a result. 
- It is therefore an expensive operation.
## Implementation
- org.apache.http.HttpConnection.isStale
- org.apache.http.impl.BHttpConnectionBase.isStale
## Control Parameter
- org.apache.http.impl.conn.PoolingHttpClientConnectionManager.setValidateAfterInactivity(int)
- org.apache.http.pool.AbstractConnPool.lease(T, java.lang.Object, org.apache.http.concurrent.FutureCallback<E>)
- org.apache.http.pool.AbstractConnPool.validate
- org.apache.http.impl.conn.CPool.validate
- org.apache.http.HttpConnection.isStale
- org.apache.http.impl.BHttpConnectionBase.isStale

# Key Classes for apache ok-http-3
- okhttp3.OkHttpClient
- okhttp3.ConnectionPool
- okhttp3.internal.platform.Platform

# Key Classes for Auto Configure
- org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration
- org.springframework.boot.autoconfigure.web.client.RestTemplateBuilderConfigurer
- org.springframework.boot.web.client.ClientHttpRequestFactorySupplier

# Test
- curl http://localhost:8080/clients/apache/pooling?target=https://111.124.200.101:443
- netstat -cnpt|grep <pid>
- jstack <pid>

# Test steps for reproduce connection reset
1. Start server
2. Start tcp reset: python ./tcp-connection-reset.py <client_id> 111.124.200.101 443
3. curl http://localhost:8080/clients/apache/pooling?target=https://111.124.200.101:443 [Will success multiple times, then will optionally timeout, and then will quick failed with connection reset exception]

# Test step for reproduce connection refused
1. Start server
2. iptables -A OUTPUT -p tcp -s <client_id> -d 111.124.200.101 -j REJECT
3. curl http://localhost:8080/clients/apache/pooling?target=https://111.124.200.101:443
