package io.rapidw.sshdeployer;

import java.nio.file.attribute.PosixFilePermission;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Utils {

    public static Collection<PosixFilePermission> permission777() {
        List<PosixFilePermission> permissions = new LinkedList<>();
        permissions.add(PosixFilePermission.GROUP_WRITE);
        permissions.add(PosixFilePermission.GROUP_READ);
        permissions.add(PosixFilePermission.GROUP_EXECUTE);
        permissions.add(PosixFilePermission.OWNER_WRITE);
        permissions.add(PosixFilePermission.OWNER_READ);
        permissions.add(PosixFilePermission.OWNER_EXECUTE);
        permissions.add(PosixFilePermission.OTHERS_WRITE);
        permissions.add(PosixFilePermission.OTHERS_READ);
        permissions.add(PosixFilePermission.OTHERS_EXECUTE);
        return permissions;
    }
}
