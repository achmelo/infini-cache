package sk.cache.kes;

import org.infinispan.commons.api.CacheContainerAdmin;
import org.infinispan.commons.dataconversion.MediaType;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.parsing.ConfigurationBuilderHolder;
import org.infinispan.configuration.parsing.ParserRegistry;
import org.infinispan.manager.DefaultCacheManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class InfiniConfig {
    @Value("${jgroups.tcpping.initial_hosts}")
    private String initialHosts;
    @Bean
    DefaultCacheManager cacheManager() throws IOException {
        System.setProperty("jgroups.tcpping.initial_hosts", initialHosts);

        InputStream configurationStream = new FileInputStream("./src/main/resources/infinispan.xml");
        ConfigurationBuilderHolder holder = new ParserRegistry().parse(configurationStream, null, MediaType.APPLICATION_XML);

        DefaultCacheManager cacheManager = new DefaultCacheManager(holder, true);

        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.clustering().cacheMode(CacheMode.DIST_SYNC);
        builder.persistence().passivation(true)
                .addSoftIndexFileStore()
                .shared(false)
                .dataLocation("data").indexLocation("index");
        cacheManager.administration()
                .withFlags(CacheContainerAdmin.AdminFlag.VOLATILE)
                .getOrCreateCache("myCache", builder.build());
        return cacheManager;
    }

}
