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
package io.rapidw.sshdeployer;

import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.keyverifier.AcceptAllServerKeyVerifier;
import org.apache.sshd.client.session.ClientSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;

public class SshDeployer {

    private static final Logger log = LoggerFactory.getLogger(SshDeployer.class);

    private SshDeployerOptions options;

    private List<SshTask> tasks = new LinkedList<>();
    private ByteArrayOutputStream out = new ByteArrayOutputStream();
    private ByteArrayOutputStream err = new ByteArrayOutputStream();

    public SshDeployer(SshDeployerOptions options) {
        this.options = options;
    }

    public SshDeployer task(SshTask task) {
        this.tasks.add(task);
        return this;
    }

    public void run() {

        SshClient client = SshClient.setUpDefaultClient();
        client.setServerKeyVerifier(AcceptAllServerKeyVerifier.INSTANCE);

        client.start();
        try (ClientSession session = client.connect(options.getUsername(), options.getHost(), options.getPort())
                .verify(options.getConnectTimeout()).getClientSession()) {

            session.addPasswordIdentity(options.getPassword());
            session.auth().verify();

            for (SshTask task : tasks) {
                task.execute(session, options, out, err);
            }
        } catch (Exception e) {
            throw new SshDeployerException(e);
        }

        log.debug("\n{}", out.toString());
        client.stop();
    }
}
