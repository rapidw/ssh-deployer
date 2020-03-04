package io.rapidw.sshdeployer;

import java.io.ByteArrayOutputStream;

public class Output {

    private ByteArrayOutputStream out;
    private ByteArrayOutputStream err;


    public Output(ByteArrayOutputStream out, ByteArrayOutputStream err) {
        this.out = out;
        this.err = err;
    }

    public ByteArrayOutputStream getOut() {
        return this.out;
    }

    public ByteArrayOutputStream getErr() {
        return this.err;
    }
}
