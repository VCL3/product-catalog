/**
 * Created by wliu on 12/8/17.
 */
package com.intrence.backend.productcatalog.health;

import com.codahale.metrics.health.HealthCheck;

public class ProductCatalogHealthCheck extends HealthCheck {

    @Override
    public Result check() throws Exception { // NOPMD
        return 2 + 2 == 4 ? Result.healthy() : Result.unhealthy("2 + 2 != 4 !!!");
    }

}
