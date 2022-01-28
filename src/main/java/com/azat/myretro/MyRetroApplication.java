package com.azat.myretro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import com.azat.myretro.config.FileStorageProperties;

@ComponentScan(basePackages = { "com.azat" })
@SpringBootApplication
@EnableConfigurationProperties({
    FileStorageProperties.class
})
public class MyRetroApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyRetroApplication.class, args);
	}

}
