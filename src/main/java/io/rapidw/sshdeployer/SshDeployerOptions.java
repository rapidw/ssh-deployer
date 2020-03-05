package io.rapidw.sshdeployer;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SshDeployerOptions {

    private String host;
    private int port;
    private String username;
    private String password;

    private int connectTimeout;
    private int commandTimeout;
    private int scpTimeout;

    @Builder
    public SshDeployerOptions(String host, int port, String username, String password, int connectTimeout, int commandTimeout, int scpTimeout) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.connectTimeout = (connectTimeout == 0 ? 3000 : connectTimeout);
        this.commandTimeout = (commandTimeout == 0 ? 3000 : commandTimeout);
        this.scpTimeout = (scpTimeout == 0 ? 3000 : scpTimeout);
    }
}
