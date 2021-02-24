//==========================================================================
//Sample tlsserver using sslsockets

import java.io.*;
import java.net.*;
import java.security.*;
import javax.net.ssl.*;

public class TLSServer {
    private static final int PORT = 8043;

    public static void main(String[] args) throws Exception {
        SSLContext context;
        KeyManagerFactory kmf;
        KeyStore ks;

        char[] passphrase = "12345678".toCharArray();
        ks = KeyStore.getInstance("JKS");
        ks.load(new FileInputStream("src/serverKeyStore.jks"), passphrase);

        kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(ks, passphrase);

        context = SSLContext.getInstance("TLSv1.3");
        KeyManager[] keyManagers = kmf.getKeyManagers();
        context.init(keyManagers, null, null);

        SSLServerSocketFactory ssf = context.getServerSocketFactory();
        ServerSocket ss = ssf.createServerSocket(PORT);

        SSLSocket s = (SSLSocket) ss.accept();

        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

        String line;
        while (((line = in.readLine()) != null)) {
            System.out.println(line);
        }
        in.close();
        s.close();
    }
}
