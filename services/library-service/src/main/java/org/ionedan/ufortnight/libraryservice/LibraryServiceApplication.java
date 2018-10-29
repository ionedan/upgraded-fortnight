package org.ionedan.ufortnight.libraryservice;

import org.hibernate.cfg.Environment;
import org.ionedan.ufortnight.libraryservice.config.UfortnightConfigurationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableSwagger2
@EnableConfigurationProperties(UfortnightConfigurationProperties.class)
@SpringBootApplication
public class LibraryServiceApplication {

    private final UfortnightConfigurationProperties properties;

    @Autowired
    public LibraryServiceApplication(UfortnightConfigurationProperties properties) {
        this.properties = properties;
    }

    //@Autowired
    //private final Environment env;


	@Bean
	public WebMvcConfigurer corsConfigurer() {
        var corsUrl = "http://localhost:4200"; //properties.getCors().getUrl();


        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins(corsUrl);
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(LibraryServiceApplication.class, args);
    }
}
