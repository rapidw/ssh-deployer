/**
 * Copyright 2020 Rapidw
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
package io.rapidw.utils.sshdeployer;

import io.rapidw.utils.sshdeployer.task.CommandTask;
import io.rapidw.utils.sshdeployer.task.ScpByteArrayUploadTask;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

public class SshDeployerTest {

    @Test
    public void test() throws Exception {
        SshDeployerOptions options = SshDeployerOptions.builder()
                .host("192.168.1.254")
                .port(32768)
                .username("root")
                .password("root")
                .build();
        SshDeployer deployer = new SshDeployer(options);
        deployer.task(new ScpByteArrayUploadTask(IOUtils.resourceToByteArray("/logback-test.xml"), "/root/2.xml", Utils.permission777()))
                .task(new CommandTask("ls -l"));
        deployer.run();
    }
}
