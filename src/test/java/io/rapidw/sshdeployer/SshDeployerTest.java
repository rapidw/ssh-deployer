package io.rapidw.sshdeployer;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

public class SshDeployerTest {

    @Test
    public void test() throws Exception {
        SshDeployerOptions options = SshDeployerOptions.builder()
                .host("192.168.1.254")
                .port(32768)
                .username("root")
                .password("root")
                .build();
        SshDeployer deployer = new SshDeployer(options);
        deployer.task(new ScpByteArrayUploadTask(IOUtils.resourceToByteArray("/logback-test.xml"), "/root/2.xml", Utils.permission777()))
                .task(new CommandTask("ls -l"));
        deployer.run();
    }
}
