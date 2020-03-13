package io.rapidw.sshdeployer.task;

import io.rapidw.sshdeployer.SshDeployerException;
import io.rapidw.sshdeployer.SshDeployerOptions;
import io.rapidw.sshdeployer.SshTask;
import org.apache.sshd.client.channel.ClientChannel;
import org.apache.sshd.client.channel.ClientChannelEvent;
import org.apache.sshd.client.session.ClientSession;
import org.slf4j.Logger;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.Objects;

public class CommandTask implements SshTask {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(CommandTask.class);
    private String command;

    public CommandTask(String command) {
        this.command = Objects.requireNonNull(command);
    }

    @Override
    public void execute(ClientSession session, SshDeployerOptions options, ByteArrayOutputStream out, ByteArrayOutputStream err) {
        log.debug("do command: {}", command);
        try (ClientChannel channel = session.createExecChannel(command)) {

            channel.setOut(out);
            channel.setErr(err);

            if (!channel.open().verify(options.getCommandTimeout()).isOpened()) {
                throw new SshDeployerException("open exec channel failed");
            }

            channel.waitFor(Collections.singletonList(ClientChannelEvent.CLOSED), Long.MAX_VALUE);

        } catch (Exception e) {
            throw new SshDeployerException(e);
        }
    }
}
