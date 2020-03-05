package io.rapidw.sshdeployer;

import org.apache.sshd.client.session.ClientSession;

import java.io.ByteArrayOutputStream;

public abstract class SshTask {
    private SshTaskType type;

    protected SshTask(SshTaskType type) {
        this.type = type;
    }

    public SshTaskType getType() {
        return type;
    }

    abstract void execute(ClientSession session, SshDeployerOptions options, ByteArrayOutputStream out, ByteArrayOutputStream err);
}
