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
package io.rapidw.utils.sshdeployer.task;

import io.rapidw.utils.sshdeployer.SshDeployerException;
import io.rapidw.utils.sshdeployer.SshDeployerOptions;
import io.rapidw.utils.sshdeployer.SshTask;
import lombok.extern.slf4j.Slf4j;
import org.apache.sshd.client.scp.ScpClient;
import org.apache.sshd.client.scp.ScpClientCreator;
import org.apache.sshd.client.session.ClientSession;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class ScpClasspathFileUploadTask implements SshTask {

    private URL localPath;
    private String remotePath;

    public ScpClasspathFileUploadTask(String localPathString, String remotePath) {
        log.debug(localPathString);
        this.localPath = this.getClass().getResource(localPathString);
        if (this.localPath == null) {
            throw new FileSystemNotFoundException("file not found: " + localPathString);
        }
        this.remotePath = remotePath;
    }

    @Override
    public void execute(ClientSession session, SshDeployerOptions options, ByteArrayOutputStream out, ByteArrayOutputStream err) throws Exception {


        try {
            Path local = Paths.get(localPath.toURI());
            ScpClientCreator creator = ScpClientCreator.instance();
            ScpClient client = creator.createScpClient(session);

            client.upload(local, remotePath, ScpClient.Option.PreserveAttributes);
        } catch (Exception e) {
            throw new SshDeployerException(e);
        }
    }
}
