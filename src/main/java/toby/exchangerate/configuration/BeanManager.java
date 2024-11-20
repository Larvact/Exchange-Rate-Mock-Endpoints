package toby.exchangerate.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import toby.exchangerate.ResourceReader;

@Configuration
public class BeanManager
{
    @Bean
    ResourceReader resourceReader()
    {
        return new ResourceReader();
    }
}
