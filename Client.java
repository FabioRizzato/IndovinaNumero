import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

/**

 @author rizzato.fabio
 */

public class Client {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        boolean indovinato = false;
        int tentativo;
        String serverAddress = "127.0.0.1"; //indirizzo IP del server
        Socket s = new Socket(serverAddress, 9090); //creo il socket che si interfaccia con il server utilizzando la porta 9090
        BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));//stream server->client
        PrintWriter output = new PrintWriter(s.getOutputStream(), true);//stream client->server
        int tentativiDisponibili = Integer.parseInt(input.readLine());
        System.out.println("*BENVENUTO*");
        System.out.println("Indovina il numero pensato dal server.");
        System.out.println("I numeri sono compresi tra 0 e 99, hai solo " + tentativiDisponibili + " tentativi");
        for (int numTentativi = 1; !indovinato && numTentativi <= tentativiDisponibili; numTentativi++) {
            try {
                System.out.print("Tentativo n." + numTentativi + " : ");
                tentativo = sc.nextInt();
                output.println(tentativo); //manda al server il tentativo
                System.out.println("mandato");
                String answer = input.readLine(); //sento la risposta del server, giusto o sbagliato? maggiore o minore?
                System.out.println(answer);
                indovinato = Boolean.parseBoolean(new String(input.readLine())); //boolean per capire se continuare a provare o meno
                } 
            catch (InputMismatchException e) {
                System.out.println("Devi inserire un numero");
                s.close();
                break;
            }
        }
    }
}
