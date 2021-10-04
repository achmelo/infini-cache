package sk.cache.kes;

import org.infinispan.commons.api.CacheContainerAdmin;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfiniConfig {
	@Bean
	DefaultCacheManager init() {
	GlobalConfigurationBuilder global = GlobalConfigurationBuilder.defaultClusteredBuilder();
	DefaultCacheManager cacheManager = new DefaultCacheManager(global.build());
	ConfigurationBuilder builder = new ConfigurationBuilder();
	builder.clustering().cacheMode(CacheMode.DIST_SYNC);
	cacheManager.administration()
	.withFlags(CacheContainerAdmin.AdminFlag.VOLATILE)
	.getOrCreateCache("myCache", builder.build());
	return cacheManager;
}
}
