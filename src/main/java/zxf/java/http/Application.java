package zxf.java.http;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        //System.setProperty("javax.net.debug", "all");
        SpringApplication.run(Application.class, args);
    }
}
