package nph.scheduler.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.transport.verification.HostKeyVerifier;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;

public class ScpUtil {

    public static void run(String[] args) throws IOException {
        String password = args[0];
        String source = args[1];
        String destination = args[2];
        if (source.contains("@")) {
            download(password, source, destination);
        } else {
            upload(password, source, destination);
        }
    }

    public static void download(String password, String source, String destination) throws IOException {
        SSHClient client = connectSSH(source, password);
        SFTPClient sftpClient = client.newSFTPClient();
        sftpClient.get(getPath(source), destination);
        sftpClient.close();
        client.disconnect();
    }

    public static void upload(String password, String source, String destination) throws IOException {
        SSHClient client = connectSSH(destination, password);
        SFTPClient sftpClient = client.newSFTPClient();
        sftpClient.put(source, getPath(destination));
        sftpClient.close();
        client.disconnect();
    }

    public static SSHClient connectSSH(String url, String password) throws IOException {
        SSHClient client = new SSHClient();
        client.addHostKeyVerifier((HostKeyVerifier)new PromiscuousVerifier());
        client.connect(getHost(url));
        client.authPassword(getUser(url), password);
        return client;
    }

    public static String getUser(String url) {
        return url.split("@")[0];
    }

    public static String getHost(String url) {
        return url.split(":")[0].split("@")[1];
    }

    public static String getPath(String url) {
        return url.split(":")[1];
    }
}