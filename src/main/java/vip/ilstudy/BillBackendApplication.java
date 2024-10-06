package vip.ilstudy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@MapperScan(value = "vip.ilstudy.mapper")
@SpringBootApplication
@EnableAspectJAutoProxy
public class BillBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillBackendApplication.class, args);
    }

}
