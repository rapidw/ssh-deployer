package io.rapidw.sshdeployer.task;

import io.rapidw.sshdeployer.SshDeployerException;
import io.rapidw.sshdeployer.SshDeployerOptions;
import io.rapidw.sshdeployer.SshTask;
import org.apache.sshd.client.scp.ScpClient;
import org.apache.sshd.client.scp.ScpClientCreator;
import org.apache.sshd.client.session.ClientSession;

import java.io.ByteArrayOutputStream;

public class ScpLocalFileUploadTask implements SshTask {

    private String localPath;
    private String remotePath;

    public ScpLocalFileUploadTask(String localPath, String remotePath) {
        this.localPath = localPath;
        this.remotePath = remotePath;
    }

    @Override
    public void execute(ClientSession session, SshDeployerOptions options, ByteArrayOutputStream out, ByteArrayOutputStream err) {
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
