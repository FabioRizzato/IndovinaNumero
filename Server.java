import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Random;


/**

 @author rizzato.fabio
 */
public class Server {
    static int tentativiDisponibili = 1;
    static boolean indovinato = false;
    
    public static int GeneraRandom(int n) {
        Random r = new Random(new Date().getTime());
        int i = r.nextInt(n);
        return i;
    }
    
    public static void AnalizzaTentativo(/*PrintWriter out, */Socket s, int tent, int rand, int tentUtilizz) throws IOException {
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        if (tent == rand) {
            out.println("Hai Azzecato! Bravo!!");
            out.println("true");
            indovinato = true;
        } //se il tentativo è pari al numero random, hai vinto
        else {
            if (tentUtilizz < tentativiDisponibili) {
                if(tent < rand) { out.println("Hai Sbagliato, ritenta. Il numero da indovinare è maggiore");}
                else            { out.println("Hai Sbagliato, ritenta. Il numero da indovinare è minore");  }
                out.println("false");
                indovinato = false;
            } else {
                out.println("---- GAME OVER ----");
                out.println("false");
                indovinato = false;
            }
        }
    }
 
    
    
    
    
    public static void main(String[] args) throws IOException {
        ServerSocket listener = new ServerSocket(9090); //metto il server ad ascoltare sulla porta 9090
        try {
            while (true) {
                Socket socket = listener.accept(); //accetto la connessione con il client
                int n = 100;
                int random = GeneraRandom(n);//genera numero random
                int tentativiUtilizzati = 0;
                System.out.println("Numero Generato: " + random);
                try {
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true); // con autoflush, stream server->client
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); //stream client->server
                    out.println(tentativiDisponibili);
                    indovinato = false;
                    while (!indovinato) {
                        int tentativo = Integer.parseInt(in.readLine()); //arriva il tentativo dal client
                        tentativiUtilizzati++;
                        AnalizzaTentativo(socket, tentativo, random, tentativiUtilizzati);
                    }
                } 
                finally {
                    socket.close();
                }
            }
        } finally {
            listener.close();
        }
    }
}
