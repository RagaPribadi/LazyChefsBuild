import java.io.*;
import java.net.*;
import java.util.Date;

public class Server {

    public static void main(String[] args) throws Exception
    {
        //default port number is 9090
        ServerSocket listener = new ServerSocket(9090);
        InetAddress IP=InetAddress.getLocalHost();
        System.out.println("IP of my system is := "+IP.getHostAddress());
        try {
            while (true) {
                Socket socket = listener.accept();
                try {
                   System.out.println("accept");
                   ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                   //convert ObjectInputStream object to String
                   String keyword = (String) ois.readObject();
                   System.out.println("Keyword Received: " + keyword);

                  String message = "";
                  int counter = 0;
                  BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("db.txt")));
                  try {
                      String line;
                      while ((line = br.readLine()) != null) {
                          // process line
                        if(line.contains(keyword) && counter < 3)
                        {
                           message += (line + "\n"); //check whether the line read in contains search key word 
                           counter++;
                        }
                      }
                  } finally {
                      br.close();
                  }
                   
                   //create ObjectOutputStream object
                  ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                  //write object to Socket
                  oos.writeObject(message);
                  //close resources
                  ois.close();
                  oos.close();
                } finally {
                    socket.close();
                }
            }
        }
        finally {
            listener.close();
        }
    }
}