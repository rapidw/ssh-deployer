package io.rapidw.sshdeployer;

import lombok.extern.slf4j.Slf4j;
import org.apache.sshd.client.channel.ClientChannel;
import org.apache.sshd.client.channel.ClientChannelEvent;
import org.apache.sshd.client.session.ClientSession;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.Objects;

@Slf4j
public class CommandTask extends SshTask{
    private String command;

    public CommandTask(String command) {
        super(SshTaskType.COMMAND);
        this.command = Objects.requireNonNull(command);
    }

    @Override
    void execute(ClientSession session, SshDeployerOptions options, ByteArrayOutputStream out, ByteArrayOutputStream err) {
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
