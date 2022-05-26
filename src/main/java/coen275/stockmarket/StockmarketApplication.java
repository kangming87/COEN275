package coen275.stockmarket;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication(exclude = { SecurityAutoConfiguration.class} )
@MapperScan("coen275.stockmarket.Mapper")
public class StockmarketApplication extends SpringBootServletInitializer {

    @Override protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(StockmarketApplication.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(StockmarketApplication.class, args);
    }

}
