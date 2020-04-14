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
import org.apache.sshd.client.scp.ScpClient;
import org.apache.sshd.client.scp.ScpClientCreator;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.common.scp.ScpTimestamp;
import org.slf4j.Logger;

import java.io.ByteArrayOutputStream;
import java.nio.file.attribute.PosixFilePermission;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

public class ScpByteArrayUploadTask implements SshTask {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ScpByteArrayUploadTask.class);
    private byte[] data;
    private Instant timestamp;
    private String remotePath;
    private Collection<PosixFilePermission> permissions;

    public ScpByteArrayUploadTask(byte[] data, String remotePath, Collection<PosixFilePermission> permissions) {
        this.data = data;
        this.remotePath = remotePath;
        this.timestamp = Instant.now();
        this.permissions = permissions;
    }

    public static ScpByteArrayUploadTaskBuilder builder() {
        return new ScpByteArrayUploadTaskBuilder();
    }

    @Override
    public void execute(ClientSession session, SshDeployerOptions options, ByteArrayOutputStream out, ByteArrayOutputStream err) {
        long milli = timestamp.toEpochMilli();

        ScpClientCreator creator = ScpClientCreator.instance();
        ScpClient client = creator.createScpClient(session);
        try {
            client.upload(data, remotePath, permissions, new ScpTimestamp(milli, milli));
        } catch (Exception e) {
            throw new SshDeployerException(e);
        }
    }

    public static class ScpByteArrayUploadTaskBuilder {
        private byte[] data;
        private String remotePath;
        private ArrayList<PosixFilePermission> permissions;

        ScpByteArrayUploadTaskBuilder() {
        }

        public ScpByteArrayUploadTask.ScpByteArrayUploadTaskBuilder data(byte[] data) {
            this.data = data;
            return this;
        }

        public ScpByteArrayUploadTask.ScpByteArrayUploadTaskBuilder remotePath(String remotePath) {
            this.remotePath = remotePath;
            return this;
        }

        public ScpByteArrayUploadTask.ScpByteArrayUploadTaskBuilder permission(PosixFilePermission permission) {
            if (this.permissions == null) this.permissions = new ArrayList<PosixFilePermission>();
            this.permissions.add(permission);
            return this;
        }

        public ScpByteArrayUploadTask.ScpByteArrayUploadTaskBuilder permissions(Collection<? extends PosixFilePermission> permissions) {
            if (this.permissions == null) this.permissions = new ArrayList<PosixFilePermission>();
            this.permissions.addAll(permissions);
            return this;
        }

        public ScpByteArrayUploadTask.ScpByteArrayUploadTaskBuilder clearPermissions() {
            if (this.permissions != null)
                this.permissions.clear();
            return this;
        }

        public ScpByteArrayUploadTask build() {
            Collection<PosixFilePermission> permissions;
            switch (this.permissions == null ? 0 : this.permissions.size()) {
                case 0:
                    permissions = java.util.Collections.emptyList();
                    break;
                case 1:
                    permissions = java.util.Collections.singletonList(this.permissions.get(0));
                    break;
                default:
                    permissions = java.util.Collections.unmodifiableList(new ArrayList<PosixFilePermission>(this.permissions));
            }

            return new ScpByteArrayUploadTask(data, remotePath, permissions);
        }

        public String toString() {
            return "ScpByteArrayUploadTask.ScpByteArrayUploadTaskBuilder(data=" + java.util.Arrays.toString(this.data) + ", remotePath=" + this.remotePath + ", permissions=" + this.permissions + ")";
        }
    }
}
