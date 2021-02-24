//==========================================================================
//Sample tlsclient using sslsockets

import java.io.*;
import java.net.*;
import java.security.*;
import javax.net.ssl.*;

public class TLSClient {
    private static final String HOST = "localhost";
    private static final int PORT = 8043;

    public static void main(String[] args) throws Exception {
        char[] passphrase_ts = "12345678".toCharArray();
        KeyStore ts = KeyStore.getInstance("JKS");
        ts.load(new FileInputStream("src/clientKeyStore.jks"), passphrase_ts);
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        tmf.init(ts);

        SSLContext context = SSLContext.getInstance("TLSv1.3");
        TrustManager[] trustManagers = tmf.getTrustManagers();
        KeyManager[] keyManagers = null;

        context.init(keyManagers, trustManagers, new SecureRandom());
        SSLSocketFactory sf = context.getSocketFactory();
        Socket s = sf.createSocket(HOST, PORT);

        OutputStream toServer = s.getOutputStream();


        toServer.write("\nConnection established.\n\n".getBytes());
        System.out.print("\nConnection established.\n\n");

        int inCharacter = 0;
        inCharacter = System.in.read();
        while (inCharacter != '~') {
            toServer.write(inCharacter);
            toServer.flush();
            inCharacter = System.in.read();
        }
        toServer.close();
        s.close();
    }
}
