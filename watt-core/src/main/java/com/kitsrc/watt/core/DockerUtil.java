/*
 * Copyright (c) 2017 Baidu, Inc. All Rights Reserve.
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
package com.kitsrc.watt.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DockerUtil
 *
 * @author yutianbao
 */
public final class DockerUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(DockerUtil.class);

    /** Environment param keys */
    private static final String ENV_KEY_HOST = "JPAAS_HOST";
    private static final String ENV_KEY_PORT = "JPAAS_HTTP_PORT";
    private static final String ENV_KEY_PORT_ORIGINAL = "JPAAS_HOST_PORT_8080";

    /** Docker host & port */
    private static String DOCKER_HOST = "";
    private static String DOCKER_PORT = "";

    /** Whether is docker */
    private static boolean IS_DOCKER;

    static {
        DockerUtil.retrieveFromEnv();
    }

    /**
     * Retrieve docker host
     *
     * @return empty string if not a docker
     */
    public static String getDockerHost() {
        return DockerUtil.DOCKER_HOST;
    }

    /**
     * Retrieve docker port
     *
     * @return empty string if not a docker
     */
    public static String getDockerPort() {
        return DockerUtil.DOCKER_PORT;
    }

    /**
     * Whether a docker
     *
     * @return
     */
    public static boolean isDocker() {
        return DockerUtil.IS_DOCKER;
    }

    /**
     * Retrieve host & port from environment
     */
    private static void retrieveFromEnv() {
        // retrieve host & port from environment
        DockerUtil.DOCKER_HOST = System.getenv(DockerUtil.ENV_KEY_HOST);
        DockerUtil.DOCKER_PORT = System.getenv(DockerUtil.ENV_KEY_PORT);

        // not found from 'JPAAS_HTTP_PORT', then try to find from 'JPAAS_HOST_PORT_8080'
        if (StringUtil.isBlank(DockerUtil.DOCKER_PORT)) {
            DockerUtil.DOCKER_PORT = System.getenv(DockerUtil.ENV_KEY_PORT_ORIGINAL);
        }

        boolean hasEnvHost = StringUtil.isNotBlank(DockerUtil.DOCKER_HOST);
        boolean hasEnvPort = StringUtil.isNotBlank(DockerUtil.DOCKER_PORT);

        // docker can find both host & port from environment
        if (hasEnvHost && hasEnvPort) {
            DockerUtil.IS_DOCKER = true;

            // found nothing means not a docker, maybe an actual machine
        }
        else if (!hasEnvHost && !hasEnvPort) {
            DockerUtil.IS_DOCKER = false;

        }
        else {
            DockerUtil.LOGGER
                    .error("Missing host or port from env for Docker. host:{}, port:{}", DockerUtil.DOCKER_HOST, DockerUtil.DOCKER_PORT);
            throw new RuntimeException(
                    "Missing host or port from env for Docker. host:" + DockerUtil.DOCKER_HOST + ", port:" + DockerUtil.DOCKER_PORT);
        }
    }

}
