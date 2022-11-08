/**
 * Copyright Pravega Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.pravega.dataimporter.actions;

import io.pravega.dataimporter.AppConfiguration;
import io.pravega.dataimporter.jobs.KafkaMirroringJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaMirroringAction extends AbstractAction {
    final private static Logger log = LoggerFactory.getLogger(KafkaMirroringAction.class);

    public final static String NAME = "kafka-stream-mirroring";

    private final AppConfiguration config;

    public KafkaMirroringAction(AppConfiguration config) {
        this.config = config;
        super.job = new KafkaMirroringJob(this.config);
    }

    public AppConfiguration getConfig() {
        return config;
    }

    @Override
    public void commitMetadataChanges() {
        final AppConfiguration.StreamConfig outputStreamConfig = getConfig().getStreamConfig("output");
        log.info("output stream: {}", outputStreamConfig);
        AbstractAction.createStream(outputStreamConfig, "kafka-mirror");
    }

    @Override
    public String getJobName() {
        return job.getClass().getName();
    }
    
}
