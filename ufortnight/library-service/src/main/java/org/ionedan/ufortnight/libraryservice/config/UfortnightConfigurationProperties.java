package org.ionedan.ufortnight.libraryservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Primary
//@PropertySource(value="classpath:ufortnight.properties")
@org.springframework.boot.context.properties.ConfigurationProperties(prefix="ufortnight")
@Getter @Setter
public class UfortnightConfigurationProperties {

    public static class Cors {

        @Getter @Setter
        private String url;
    }

    @Getter @Setter
    private Cors cors;
}
