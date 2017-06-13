/**
 *
 *    Copyright 2016-2017, Optimizely and contributors
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.optimizely.ab.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectConfigUtils {

    /**
     * Helper method for creating convenience mappings from key to entity
     */
    public static <T extends IdKeyMapped> Map<String, T> generateNameMapping(List<T> nameables) {
        Map<String, T> nameMapping = new HashMap<String, T>();
        for (T nameable : nameables) {
            nameMapping.put(nameable.getKey(), nameable);
        }

        return Collections.unmodifiableMap(nameMapping);
    }

    /**
     * Helper method for creating convenience mappings from ID to entity
     */
    public static <T extends IdMapped> Map<String, T> generateIdMapping(List<T> nameables) {
        Map<String, T> nameMapping = new HashMap<String, T>();
        for (T nameable : nameables) {
            nameMapping.put(nameable.getId(), nameable);
        }

        return Collections.unmodifiableMap(nameMapping);
    }

    /**
     * Helper method to create a map from variation ID to variable ID to {@link LiveVariableUsageInstance}
     */
    public static Map<String, Map<String, LiveVariableUsageInstance>> generateVariationToLiveVariableUsageInstancesMap(
            List<Experiment> experiments) {

        Map<String, Map<String, LiveVariableUsageInstance>> liveVariableValueMap =
                new HashMap<String, Map<String, LiveVariableUsageInstance>>();
        for (Experiment experiment : experiments) {
            for (Variation variation : experiment.getVariations()) {
                if (variation.getLiveVariableUsageInstances() != null) {
                    for (LiveVariableUsageInstance usageInstance : variation.getLiveVariableUsageInstances()) {
                        Map<String, LiveVariableUsageInstance> liveVariableIdToValueMap =
                                liveVariableValueMap.get(variation.getId());
                        if (liveVariableIdToValueMap == null) {
                            liveVariableIdToValueMap = new HashMap<String, LiveVariableUsageInstance>();
                        }

                        liveVariableIdToValueMap.put(usageInstance.getId(), usageInstance);
                        liveVariableValueMap.put(variation.getId(), liveVariableIdToValueMap);
                    }
                }
            }
        }

        return liveVariableValueMap;
    }
}
