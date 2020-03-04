package io.rapidw.sshdeployer;

import lombok.Builder;
import lombok.Singular;
import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ClientChannel;
import org.apache.sshd.client.channel.ClientChannelEvent;
import org.apache.sshd.client.keyverifier.AcceptAllServerKeyVerifier;
import org.apache.sshd.client.scp.ScpClient;
import org.apache.sshd.client.scp.ScpClientCreator;
import org.apache.sshd.client.session.ClientSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class SshDeployer {

    private static final Logger log = LoggerFactory.getLogger(SshDeployer.class);

    private String host;
    private int port;
    private String username;
    private String password;
    private int timeout;
    private List<SshTask> tasks = new LinkedList<>();

    private ByteArrayOutputStream out = new ByteArrayOutputStream();
    private ByteArrayOutputStream err = new ByteArrayOutputStream();

    @Builder
    private SshDeployer(String host, int port, String username, String password, int timeout, @Singular List<SshTask> tasks) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        if (timeout == 0) {
            this.timeout = 3000;
        } else {
            this.timeout = timeout;
        }
        this.tasks = tasks;
    }

    public static SshDeployerBuilder builder() {
        return new SshDeployerBuilder();
    }

    public void run() {


        SshClient client = SshClient.setUpDefaultClient();
        client.setServerKeyVerifier(AcceptAllServerKeyVerifier.INSTANCE);

        client.start();
        try (ClientSession session = client.connect(username, host, port).verify(timeout).getClientSession()) {

            session.addPasswordIdentity(password);
            session.auth().verify();

            for (SshTask task : tasks) {
                switch (task.getType()) {
                    case COMMAND:
                        doCommand(session, ((CommandTask) task).getCommand());
                        break;
                    case SCP:
                        ScpTask task1 = ((ScpTask) task);
                        doScp(session, task1.getSourcePath(), task1.getTargetPath());
                        break;
                }
            }

        } catch (Exception e) {
            throw new SshDeployerException(e);
        }

        log.debug("\n{}", out.toString());
        client.stop();
    }

    private void doCommand(ClientSession session, String command) {
        log.debug("do command: {}", command);
        try (ClientChannel channel = session.createExecChannel(command)) {

            channel.setOut(out);
            channel.setErr(err);

            if (!channel.open().verify(timeout).isOpened()) {
                throw new SshDeployerException("open exec channel failed");
            }

            channel.waitFor(Collections.singletonList(ClientChannelEvent.CLOSED), Long.MAX_VALUE);

        } catch (Exception e) {
            throw new SshDeployerException(e);
        }
    }

    private void doScp(ClientSession session, String sourcePath, String targetPath) {
        ScpClientCreator creator = ScpClientCreator.instance();
        ScpClient client = creator.createScpClient(session);
        try {
            client.upload(Paths.get(sourcePath), targetPath, ScpClient.Option.PreserveAttributes);
        } catch (Exception e) {
            throw new SshDeployerException(e);
        }

    }
}
