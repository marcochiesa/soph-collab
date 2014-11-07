package soph.collab;

import java.util.Arrays;
import java.util.Properties;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class DebugHelper implements InitializingBean, ApplicationContextAware, CommandLineRunner {

    public void afterPropertiesSet() {
        System.out.println("**props set");
    }

    public void run(String... args) {
        System.out.println("**jvm args: " + StringUtils.arrayToCommaDelimitedString(args));
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        System.out.println("**application context beans");
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }

        System.out.println("**System properties");
        java.util.Properties props = System.getProperties();
        props.list(System.out);

        Environment env = applicationContext.getEnvironment();
        System.out.println("spring.view.prefix=" + env.getProperty("spring.view.prefix"));
        System.out.println("spring.view.suffix=" + env.getProperty("spring.view.suffix"));
        System.out.println("spring.thymeleaf.prefix=" + env.getProperty("spring.thymeleaf.prefix"));
        System.out.println("spring.thymeleaf.suffix=" + env.getProperty("spring.thymeleaf.suffix"));
        System.out.println("spring.thymeleaf.cache=" + env.getProperty("spring.thymeleaf.cache"));
    }
}
