package io.rapidw.sshdeployer;

public class SshDeployerException extends RuntimeException {

    public SshDeployerException(Throwable cause) {
        super(cause);
    }

    public SshDeployerException(String msg) {
        super(msg);
    }
}
