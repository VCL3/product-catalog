package com.intrence.backend.productcatalog;

import com.google.inject.Injector;
import com.intrence.backend.productcatalog.health.ProductCatalogHealthCheck;
import com.intrence.backend.productcatalog.resources.ProductCatalogResource;
import com.intrence.core.modules.ElasticSearchModule;
import com.intrence.core.modules.PostgresModule;
import com.netflix.governator.guice.LifecycleInjector;
import io.dropwizard.Application;
import io.dropwizard.lifecycle.setup.LifecycleEnvironment;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.util.Arrays;

public class ProductCatalogApplication extends Application<ProductCatalogConfiguration> {

    public static void main(final String[] args) throws Exception {
        new ProductCatalogApplication().run(args);
    }

    @Override
    public String getName() {
        return "product-catalog";
    }

    @Override
    public void initialize(final Bootstrap<ProductCatalogConfiguration> bootstrap) {
        // TODO: application initialization, add bundle etc.
    }

    @Override
    public void run(final ProductCatalogConfiguration configuration, final Environment environment) {
        // Governator Guice Injector
        Injector injector = LifecycleInjector.builder().withModules(
                Arrays.asList(
                        new PostgresModule(),
                        new ElasticSearchModule(configuration.getElasticSearchConfig(), environment)))
                .build()
                .createInjector();
        environment.jersey().register(injector);

        // Lifecycle Management
        LifecycleEnvironment lifecycleEnvironment = environment.lifecycle();

        // Register Resources
        environment.jersey().register(injector.getInstance(ProductCatalogResource.class));
        environment.healthChecks().register("math", new ProductCatalogHealthCheck());
    }

}
