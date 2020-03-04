package io.rapidw.sshdeployer;

public class SshTask {
    private SshTaskType type;

    protected SshTask(SshTaskType type) {
        this.type = type;
    }

    public SshTaskType getType() {
        return type;
    }
}
