/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package sk.cache.kes;

import org.infinispan.manager.DefaultCacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    private final DefaultCacheManager defaultCacheManager;

    public SampleController(DefaultCacheManager defaultCacheManager) {
        this.defaultCacheManager = defaultCacheManager;
    }

    @GetMapping("/get/{key}")
    String getSomething(@PathVariable("key")String key){
       return(String) defaultCacheManager.getCache("myCache").get(key);
    }

    @GetMapping("/put/{key}/{value}")
    String insertSomething(@PathVariable("key")String key, @PathVariable("value")String value){
        return(String) defaultCacheManager.getCache("myCache").put(key, value);
    }
}
