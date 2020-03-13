package io.rapidw.sshdeployer.task;

import io.rapidw.sshdeployer.SshDeployerException;
import io.rapidw.sshdeployer.SshDeployerOptions;
import io.rapidw.sshdeployer.SshTask;
import org.apache.sshd.client.scp.ScpClient;
import org.apache.sshd.client.scp.ScpClientCreator;
import org.apache.sshd.client.session.ClientSession;

import java.io.ByteArrayOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class ScpClasspathFileUploadTask implements SshTask {

    private String localPath;
    private String remotePath;

    public ScpClasspathFileUploadTask(String localPath, String remotePath) {
        this.localPath = localPath;
        this.remotePath = remotePath;
    }

    @Override
    public void execute(ClientSession session, SshDeployerOptions options, ByteArrayOutputStream out, ByteArrayOutputStream err) {
        try {
            Path local = Paths.get(Objects.requireNonNull(this.getClass().getClassLoader().getResource(localPath)).toURI());
        } catch (Exception e) {
            throw new SshDeployerException(e);
        }

        ScpClientCreator creator = ScpClientCreator.instance();
        ScpClient client = creator.createScpClient(session);
        try {
            client.upload(localPath, remotePath, ScpClient.Option.PreserveAttributes);
        } catch (Exception e) {
            throw new SshDeployerException(e);
        }
    }

    public String getLocalPath() {
        return this.localPath;
    }

    public String getRemotePath() {
        return this.remotePath;
    }
}
