package com.guxt.fakemarket;

import com.guxt.fakemarket.service.MarketPlugin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
/**
 * Creator:guxt Date 2019/01/10
 */

@SpringBootApplication
public class FakemarketApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ca = SpringApplication
            .run(FakemarketApplication.class, args);
        MarketPlugin fm = (MarketPlugin) ca.getBean("marketPlugin");
        fm.test();
        //MarketPlugin fm = new MarketPlugin();

    }

}

