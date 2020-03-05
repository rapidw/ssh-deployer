package io.rapidw.sshdeployer;

import lombok.Getter;
import org.apache.sshd.client.scp.ScpClient;
import org.apache.sshd.client.scp.ScpClientCreator;
import org.apache.sshd.client.session.ClientSession;

import java.io.ByteArrayOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Getter
public class ScpClasspathFileUploadTask extends SshTask {

    private String localPath;
    private String remotePath;

    public ScpClasspathFileUploadTask(String localPath, String remotePath) {
        super(SshTaskType.SCP_CLASSPATH_FILE_UPLOAD);
        this.localPath = localPath;
        this.remotePath = remotePath;
    }

    @Override
    void execute(ClientSession session, SshDeployerOptions options, ByteArrayOutputStream out, ByteArrayOutputStream err) {
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
}
