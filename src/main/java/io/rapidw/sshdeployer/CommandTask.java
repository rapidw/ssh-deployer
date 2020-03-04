package io.rapidw.sshdeployer;

import java.util.Objects;

public class CommandTask extends SshTask{
    private String command;

    public CommandTask(String command) {
        super(SshTaskType.COMMAND);
        this.command = Objects.requireNonNull(command);
    }

    public String getCommand() {
        return command;
    }
}
