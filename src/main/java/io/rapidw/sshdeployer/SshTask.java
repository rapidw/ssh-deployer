package io.rapidw.sshdeployer;

import org.apache.sshd.client.session.ClientSession;

import java.io.ByteArrayOutputStream;

abstract class SshTask {

    abstract void execute(ClientSession session, SshDeployerOptions options, ByteArrayOutputStream out, ByteArrayOutputStream err);
}
