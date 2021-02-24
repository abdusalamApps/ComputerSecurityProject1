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
        char[] TSPassphrase = "12345678".toCharArray();
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(new FileInputStream("src/clientKeyStore.jks"), TSPassphrase);
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        tmf.init(keyStore);

        SSLContext sslContext = SSLContext.getInstance("TLSv1.3");
        TrustManager[] trustManagers = tmf.getTrustManagers();
        KeyManager[] keyManagers = null;

        sslContext.init(keyManagers, trustManagers, new SecureRandom());
        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
        Socket socket = sslSocketFactory.createSocket(HOST, PORT);

        OutputStream server = socket.getOutputStream();


        server.write("\nConnection established\n".getBytes());
        System.out.print("\nConnection established\n");

        int inChar = System.in.read();
        while (inChar != '~') {
            server.write(inChar);
            server.flush();
            inChar = System.in.read();
        }
        server.close();
        socket.close();
    }
}
