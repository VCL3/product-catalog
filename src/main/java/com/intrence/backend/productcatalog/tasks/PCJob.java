package com.intrence.backend.productcatalog.tasks;

import com.intrence.config.ConfigProvider;
import com.intrence.config.collection.ConfigMap;

/**
 * Created by wliu on 12/1/17.
 */
public class PCJob {

    public static void main(String[] args) throws Exception {
        ConfigMap configMap = ConfigProvider.getConfig();
        System.out.println(configMap);
    }
}
