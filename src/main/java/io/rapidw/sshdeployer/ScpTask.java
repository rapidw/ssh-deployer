package io.rapidw.sshdeployer;

import lombok.Getter;

@Getter
public class ScpTask extends SshTask {

    private String sourcePath;
    private String targetPath;
    public ScpTask(String sourcePath, String targetPath) {
        super(SshTaskType.SCP);
        this.sourcePath = sourcePath;
        this.targetPath = targetPath;
    }

}
