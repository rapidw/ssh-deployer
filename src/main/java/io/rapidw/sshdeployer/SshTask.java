package io.rapidw.sshdeployer;

import org.apache.sshd.client.session.ClientSession;

import java.io.ByteArrayOutputStream;

public interface SshTask {

    void execute(ClientSession session, SshDeployerOptions options, ByteArrayOutputStream out, ByteArrayOutputStream err);
}
