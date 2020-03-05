package io.rapidw.sshdeployer;

import lombok.Getter;
import org.apache.sshd.client.scp.ScpClient;
import org.apache.sshd.client.scp.ScpClientCreator;
import org.apache.sshd.client.session.ClientSession;

import java.io.ByteArrayOutputStream;

@Getter
public class ScpLocalFileUploadTask extends SshTask {

    private String localPath;
    private String remotePath;

    public ScpLocalFileUploadTask(String localPath, String remotePath) {
        super(SshTaskType.SCP_LOCAL_FILE_UPLOAD);
        this.localPath = localPath;
        this.remotePath = remotePath;
    }

    @Override
    void execute(ClientSession session, SshDeployerOptions options, ByteArrayOutputStream out, ByteArrayOutputStream err) {
        ScpClientCreator creator = ScpClientCreator.instance();
        ScpClient client = creator.createScpClient(session);
        try {
            client.upload(localPath, remotePath, ScpClient.Option.PreserveAttributes);
        } catch (Exception e) {
            throw new SshDeployerException(e);
        }
    }
}
