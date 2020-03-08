package com.home.bootShiro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.home.bootShiro.dao")
public class BootShiroApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootShiroApplication.class, args);
	}

}
