package main.java;

import java.io.*;
import java.net.*;
import java.util.LinkedList;

public class Server {

    public static final int PORT = 8080;
    public static LinkedList<ServerSomthing> serverList = new LinkedList<>(); // список всех нитей - экземпляров
    // сервера, слушающих каждый своего клиента
 //   public static Story story; // история переписки

    /**
     * @param args
     * @throws IOException
     */

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(PORT);
     //   story = new Story();
        System.out.println("Server Started");
        try {
                while (true) {
                // Блокируется до возникновения нового соединения:
                Socket socket = server.accept();
                try {
                    serverList.add(new ServerSomthing(socket)); // добавить новое соединенние в список
                } catch (IOException e) {
                    // Если завершится неудачей, закрывается сокет,
                    // в противном случае, нить закроет его:
                    socket.close();
                }
            }
        } finally {
            server.close();
        }
    }
}

class ServerSomthing extends Thread {

    private Socket socket; // сокет, через который сервер общается с клиентом,
    // кроме него - клиент и сервер никак не связаны
    private BufferedReader in; // поток чтения из сокета
    private BufferedWriter out; // поток завписи в сокет

    /**
     * для общения с клиентом необходим сокет (адресные данные)
     * @param socket
     * @throws IOException
     */

    public ServerSomthing(Socket socket) throws IOException {
        this.socket = socket;
        // если потоку ввода/вывода приведут к генерированию искдючения, оно проброситься дальше
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        start(); // вызываем run()
    }
    @Override
    public void run() {
        String word;
        int firstParam = 300;
        try {
            // первое сообщение отправленное сюда - это никнейм
            word = in.readLine();
          //  try {
                //out.write(word + "\n");
               // out.flush(); // flush() нужен для выталкивания оставшихся данных
                System.out.println("Пришёл никнейм"+word);
                // если такие есть, и очистки потока для дьнейших нужд
          //  } catch (IOException ignored) {}
            try {
                while (true) {
                    word = in.readLine();
                    System.out.println("Echoing: " + word);
                    String[] strSpli = word.split(",");
                    try {
                        firstParam = Integer.parseInt(strSpli[0]);

                        switch (firstParam) {
                            case 0:
                                for (ServerSomthing vr : Server.serverList) {
                                    vr.send("1,1,1" + firstParam); // отослать принятое сообщение с привязанного клиента всем остальным влючая его
                                }
                                System.out.println("Высылаем ответ на 0 с клиента в виде: 1,1,1");
                                break;
                            case 1:
                                for (ServerSomthing vr : Server.serverList) {
                                    vr.send("0,0,0" + "\n"); // отослать принятое сообщение с привязанного клиента всем остальным влючая его
                                }
                                System.out.println("Высылаем ответ на 0 с клиента в виде: 0,0,0");
                                break;
                            default:
                                for (ServerSomthing vr : Server.serverList) {
                                    vr.send("Команда не узнана"); // отослать принятое сообщение с привязанного клиента всем остальным влючая его
                                }
                                break;
                        }
                    }catch (NumberFormatException e){e.getStackTrace();}
                 /*   if(word.equals("stop")) {
                        this.downService(); // харакири
                        break; // если пришла пустая строка - выходим из цикла прослушки
                    }*/
                   // System.out.println("Echoing: " + word);
          //          Server.story.addStoryEl(word);
                /*    for (ServerSomthing vr : Server.serverList) {
                        vr.send(word); // отослать принятое сообщение с привязанного клиента всем остальным влючая его
                    }*/
                }
            } catch (NullPointerException ignored) {}


        } catch (IOException e) {
            this.downService();
        }
    }

    /**
     * отсылка одного сообщения клиенту по указанному потоку
     * @param msg
     */
    private void send(String msg) {
        try {
            out.write(msg);
            out.flush();
        } catch (IOException ignored) {}

    }

    /**
     * закрытие сервера
     * прерывание себя как нити и удаление из списка нитей
     */
    private void downService() {
        try {
            if(!socket.isClosed()) {
                socket.close();
                in.close();
                out.close();
                for (ServerSomthing vr : Server.serverList) {
                    if(vr.equals(this)) vr.interrupt();
                    Server.serverList.remove(this);
                }
            }
        } catch (IOException ignored) {}
    }
}


