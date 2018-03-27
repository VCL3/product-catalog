package com.intrence.backend.productcatalog;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.intrence.core.elasticsearch.ElasticSearchConfiguration;
import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.db.DataSourceFactory;
import org.hibernate.validator.constraints.*;

import javax.validation.Valid;
import javax.validation.constraints.*;

public class ProductCatalogConfiguration extends Configuration {

    private final ElasticSearchConfiguration elasticSearchConfig;

    @JsonCreator
    public ProductCatalogConfiguration(@JsonProperty(value = "elasticSearch", required = true) ElasticSearchConfiguration elasticSearchConfig) {
        this.elasticSearchConfig = elasticSearchConfig;
    }

    public ElasticSearchConfiguration getElasticSearchConfig() {
        return this.elasticSearchConfig;
    }

}
