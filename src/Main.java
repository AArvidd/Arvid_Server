import java.net.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        char[] operators = {'+', '-', '*', '/'};

        ServerSocket server = null;
        Socket client;

        int portNumber = 81;
        if(args.length >= 1){
            portNumber = Integer.parseInt(args[0]);
        }

        try{
            server = new ServerSocket(portNumber);
        } catch (IOException ie){
            System.out.println("Cannot open socket." + ie);
            System.exit(1);
        }
        System.out.println("ServerSocket is created " + server);

        while(true){
            try{

                System.out.println("Waiting for connect request...");
                client = server.accept();
                System.out.println("Connect request is accepted...");

                //sets upp output
                OutputStream clientOut = client.getOutputStream();
                PrintWriter pw = new PrintWriter(clientOut, true);

                //sets upp input
                InputStream clientIn = client.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(clientIn));


                //sends message
                pw.println("Input mathematical computation: ");

                //client answer
                String msgFromClient = br.readLine().replace(" ", "");


                int operation = -1;
                int operatorP = -1;

                for(int i = 0; i < operators.length; i++){
                    if((operatorP = msgFromClient.indexOf(operators[i])) != -1){
                        operation = i;
                        break;
                    }
                }

                if(operation == -1){
                    pw.println("Not a mathematical computation");
                    continue;
                }

                //todo add try cat that around num2
                int num1 = Integer.parseInt(msgFromClient.substring(0, operatorP));
                int num2 = Integer.parseInt(msgFromClient.substring(operatorP + 1));





                if(msgFromClient != null && msgFromClient.equalsIgnoreCase("bye")){
                    server.close();
                    client.close();
                    break;
                }

            } catch (Exception e){
                System.out.println("an error occurred");
                System.out.println(e.getMessage());
            }
        }
    }
}