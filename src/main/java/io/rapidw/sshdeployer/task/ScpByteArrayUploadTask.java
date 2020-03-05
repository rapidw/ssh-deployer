package io.rapidw.sshdeployer.task;

import io.rapidw.sshdeployer.SshDeployerException;
import io.rapidw.sshdeployer.SshDeployerOptions;
import io.rapidw.sshdeployer.SshTask;
import lombok.Builder;
import lombok.Singular;
import lombok.extern.slf4j.Slf4j;
import org.apache.sshd.client.scp.ScpClient;
import org.apache.sshd.client.scp.ScpClientCreator;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.common.scp.ScpTimestamp;

import java.io.ByteArrayOutputStream;
import java.nio.file.attribute.PosixFilePermission;
import java.time.Instant;
import java.util.Collection;

@Slf4j
public class ScpByteArrayUploadTask implements SshTask {

    private byte[] data;
    private Instant timestamp;
    private String remotePath;
    private Collection<PosixFilePermission> permissions;

    @Builder
    public ScpByteArrayUploadTask(byte[] data, String remotePath, @Singular Collection<PosixFilePermission> permissions) {
        this.data = data;
        this.remotePath = remotePath;
        this.timestamp = Instant.now();
        this.permissions = permissions;
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
}
