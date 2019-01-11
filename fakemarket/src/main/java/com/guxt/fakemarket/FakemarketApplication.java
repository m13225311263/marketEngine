package com.guxt.fakemarket;

import com.guxt.fakemarket.service.MarketPlugin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Creator:guxt Date 2019/01/10
 */
@EnableAsync
@Configuration
@ComponentScan("com.guxt")
@EnableAutoConfiguration
public class FakemarketApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ca = SpringApplication
            .run(FakemarketApplication.class, args);
        MarketPlugin fm = (MarketPlugin) ca.getBean("marketPlugin");
        //fm.test();
        //MarketPlugin fm = new MarketPlugin();
        fm.systest();

    }

}

