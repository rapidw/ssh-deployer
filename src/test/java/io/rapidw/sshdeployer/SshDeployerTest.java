package io.rapidw.sshdeployer;

import org.junit.jupiter.api.Test;

public class SshDeployerTest {

    @Test
    public void test() {
        SshDeployer deployer = SshDeployer.builder()
                .host("192.168.1.254")
                .port(32768)
                .username("root")
                .password("root")
                .task(new ScpTask("d:/1.html", "/root/1.html"))
                .task(new CommandTask("ls -l"))
                .build();
        deployer.run();
    }
}
