package toby.exchangerate;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ResourceReader
{
    public String readResource(final String resourcePath, final Class<?> classPathClz) throws IOException
    {
        ClassPathResource resource = new ClassPathResource(resourcePath, classPathClz);
        try (InputStream inputStream = resource.getInputStream())
        {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
