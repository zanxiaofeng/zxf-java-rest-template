package zxf.java.http.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/clients")
public class HttpClientController {
    @Autowired
    @Qualifier("simpleRestTemplate")
    private RestTemplate simpleRestTemplate;

//    @Autowired
//    @Qualifier("okHttp3RestTemplate")
//    private RestTemplate okHttp3RestTemplate;

    @Autowired
    @Qualifier("defaultApacheRestTemplate")
    private RestTemplate defaultApacheRestTemplate;

    @Autowired
    @Qualifier("apacheRestTemplateWithPool")
    private RestTemplate apacheRestTemplateWithPool;

    @GetMapping("/simple")
    public String simple(@RequestParam String target) {
        return simpleRestTemplate.getForObject("https://" + target, String.class);
    }

//    @GetMapping("/ok-http")
//    public String okHttp3(@RequestParam String target) {
//        return okHttp3RestTemplate.getForObject("https://" + target, String.class);
//    }

    @GetMapping("/apache/default")
    public String defaultApache(@RequestParam String target) {
        return defaultApacheRestTemplate.getForObject("https://" + target, String.class);
    }

    @GetMapping("/apache/pooling")
    public String apacheWithPooling(@RequestParam String target) {
        return apacheRestTemplateWithPool.getForObject("https://" + target, String.class);
    }
}
