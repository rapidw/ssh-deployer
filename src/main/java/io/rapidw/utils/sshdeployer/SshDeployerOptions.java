/**
 * Copyright 2020 Rapidw
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.rapidw.utils.sshdeployer;

public class SshDeployerOptions {

    private String host;
    private int port;
    private String username;
    private String password;

    private int connectTimeout;
    private int commandTimeout;
    private int scpTimeout;

    public SshDeployerOptions(String host, int port, String username, String password, int connectTimeout, int commandTimeout, int scpTimeout) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.connectTimeout = (connectTimeout == 0 ? 3000 : connectTimeout);
        this.commandTimeout = (commandTimeout == 0 ? 3000 : commandTimeout);
        this.scpTimeout = (scpTimeout == 0 ? 3000 : scpTimeout);
    }

    public static SshDeployerOptionsBuilder builder() {
        return new SshDeployerOptionsBuilder();
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public int getConnectTimeout() {
        return this.connectTimeout;
    }

    public int getCommandTimeout() {
        return this.commandTimeout;
    }

    public int getScpTimeout() {
        return this.scpTimeout;
    }

    public static class SshDeployerOptionsBuilder {
        private String host;
        private int port;
        private String username;
        private String password;
        private int connectTimeout;
        private int commandTimeout;
        private int scpTimeout;

        SshDeployerOptionsBuilder() {
        }

        public SshDeployerOptions.SshDeployerOptionsBuilder host(String host) {
            this.host = host;
            return this;
        }

        public SshDeployerOptions.SshDeployerOptionsBuilder port(int port) {
            this.port = port;
            return this;
        }

        public SshDeployerOptions.SshDeployerOptionsBuilder username(String username) {
            this.username = username;
            return this;
        }

        public SshDeployerOptions.SshDeployerOptionsBuilder password(String password) {
            this.password = password;
            return this;
        }

        public SshDeployerOptions.SshDeployerOptionsBuilder connectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        public SshDeployerOptions.SshDeployerOptionsBuilder commandTimeout(int commandTimeout) {
            this.commandTimeout = commandTimeout;
            return this;
        }

        public SshDeployerOptions.SshDeployerOptionsBuilder scpTimeout(int scpTimeout) {
            this.scpTimeout = scpTimeout;
            return this;
        }

        public SshDeployerOptions build() {
            return new SshDeployerOptions(host, port, username, password, connectTimeout, commandTimeout, scpTimeout);
        }

        public String toString() {
            return "SshDeployerOptions.SshDeployerOptionsBuilder(host=" + this.host + ", port=" + this.port + ", username=" + this.username + ", password=" + this.password + ", connectTimeout=" + this.connectTimeout + ", commandTimeout=" + this.commandTimeout + ", scpTimeout=" + this.scpTimeout + ")";
        }
    }
}
