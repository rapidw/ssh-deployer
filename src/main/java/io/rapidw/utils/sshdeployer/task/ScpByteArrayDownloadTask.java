package io.rapidw.utils.sshdeployer.task;

import io.rapidw.utils.sshdeployer.SshDeployerOptions;
import io.rapidw.utils.sshdeployer.SshTask;
import org.apache.sshd.client.scp.ScpClient;
import org.apache.sshd.client.scp.ScpClientCreator;
import org.apache.sshd.client.session.ClientSession;

import java.io.ByteArrayOutputStream;

public class ScpByteArrayDownloadTask implements SshTask {
    private byte[] data;
    private String remotePath;

    public ScpByteArrayDownloadTask(String remotePath) {
        this.remotePath = remotePath;
    }

    @Override
    public void execute(ClientSession session, SshDeployerOptions options, ByteArrayOutputStream out, ByteArrayOutputStream err) throws Exception {
        ScpClientCreator creator = ScpClientCreator.instance();
        ScpClient client = creator.createScpClient(session);
        data = client.downloadBytes(remotePath);
    }

    public byte[] getData() {
        return data;
    }
}
