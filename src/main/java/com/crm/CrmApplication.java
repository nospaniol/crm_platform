package com.crm;

import java.util.Arrays;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.Map;
import java.util.Random;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.crm")
@EnableCaching
public class CrmApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CrmApplication.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(CrmApplication.class, args);
        //  SpringApplication.run(SpringBootBatchProcessingApplication.class, args);
    }

}
