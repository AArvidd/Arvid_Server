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
                pw.println("Input mathematical computation write bye to exit: ");

                //client answer
                String msgFromClient = br.readLine().replace(" ", "");

                if(msgFromClient.equalsIgnoreCase("bye")){
                    pw.println("goodbye");
                    server.close();
                    client.close();
                    break;
                }

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

                int num1;
                int num2;

                try {
                    num1 = Integer.parseInt(msgFromClient.substring(0, operatorP));
                    num2 = Integer.parseInt(msgFromClient.substring(operatorP + 1));
                }catch(Exception e){
                    pw.println("Not a mathematical computation");
                    continue;
                }

                if(operation == 3 && num2 == 0){
                    pw.println("Can not divide by 0");
                    continue;
                }

                int sum = 0;

                switch(operation){
                    case 0 -> sum = num1 + num2;
                    case 1 -> sum = num1 - num2;
                    case 2 -> sum = num1 * num2;
                    case 3 -> sum = num1 / num2;
                }

                String msg = num1 + " " + operators[operation] + " " + num2 + " = " + sum;
                pw.println(msg);

            } catch (Exception e){
                System.out.println("an error occurred");
                System.out.println(e.getMessage());
            }
        }
    }
}