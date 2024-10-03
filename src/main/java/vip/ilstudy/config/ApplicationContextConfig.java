package vip.ilstudy.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextConfig implements InitializingBean,  ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(org.springframework.context.ApplicationContext applicationContext) {
        ApplicationContextConfig.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
