package main.java;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.UUID;

/**
 * serverList заменён на HashMap, где 1ый элемент = ServerSomthing, а второй = UUID
 */

public class Server {

    public static final int PORT = 8080;

    //PRECONDITIONS
    //Создадим список для действий:
    public static List<Integer> actions = new ArrayList<>();
    public static List<Integer> roles = new ArrayList<>();
    //добавляем hashMap
    public static HashMap<String,ServerSomthing> serverMap = new HashMap<>();
    //public static LinkedList<ServerSomthing> serverList = new LinkedList<>(); // список всех нитей - экземпляров


    public static void main(String[] args) throws IOException {

        //Заполняем actions даннымию 1 - shot. 2 - мимо
        actions.addAll(Arrays.asList(1,1,1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,2,2));
        //Перемешиваем листок:
        Collections.shuffle(actions);

        //Заполняем actions даннымию 1 - shot. 2 - мимо
        roles.addAll(Arrays.asList(1,2,3,2));
        //Перемешиваем листок:
        Collections.shuffle(roles);

        ServerSocket server = new ServerSocket(PORT);
     //   story = new Story();
        System.out.println("Server Started");
        try {
                while (true) {
                // Блокируется до возникновения нового соединения:
                Socket socket = server.accept();
                try {
                    //Создаём ID подключения
                    UUID uuid = UUID.randomUUID();
                    String randomUUIDString = uuid.toString();
                    //добавляем в serverMap новое подключение и его ID
                    serverMap.put(randomUUIDString,new ServerSomthing(socket));

                    //serverList.add(new ServerSomthing(socket)); // добавить новое соединенние в список
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
                System.out.println("Пришёл никнейм "+word);
                // если такие есть, и очистки потока для дьнейших нужд
           } catch (IOException ignored) {}
            try {
                while (true) {
                    word = in.readLine();
                    System.out.println("Echoing: " + word);
                    String[] strSpli = word.split(",");
                    try {
                        firstParam = Integer.parseInt(strSpli[0]);

                        switch (firstParam) {
                            case 0:
                                //Высылаем код, что программа запущена и одновременно присылается ID игрока
                                for(Map.Entry<String, ServerSomthing> entry : Server.serverMap.entrySet()) {
                                    String keyID = entry.getKey();
                                    ServerSomthing value = entry.getValue();
                                    System.out.println("0,"+keyID);
                                    value.send("0,"+keyID+"\n");
                                }

                                //Высылаем карточки действия и роли
                                for(Map.Entry<String, ServerSomthing> entry : Server.serverMap.entrySet()) {
                                    String keyID = entry.getKey();
                                    ServerSomthing value = entry.getValue();

                                    String actionsPlusRole = "1,";
                                    int si = 0;
                                    int ro = 0;
                                    //int count = 2;
                                    for (int i = 0; i<6; i++)
                                    {
                                        si = Server.actions.size()-1;
                                        actionsPlusRole = actionsPlusRole.concat(""+(Server.actions.get(si))+",");
                                        Server.actions.remove(si);
                                    }
                                    ro = Server.roles.size()-1;
                                    actionsPlusRole = actionsPlusRole.concat(""+Server.roles.get(ro));
                                    Server.roles.remove(ro);
                                    System.out.println("выслали код 1 + карты действий + роль"+ actionsPlusRole);
                                    value.send(actionsPlusRole + "\n"); // рассылаем код 1
                                }

                                for(Map.Entry<String, ServerSomthing> entry : Server.serverMap.entrySet()) {
                                    String keyID = entry.getKey();
                                    ServerSomthing value = entry.getValue();
                                    System.out.println("выслали соde 2 и client's id (who starts)");
                                    value.send("2,"+ Server.serverMap.keySet().toArray()[0]+ "\n");
                                }
                                break;
                            case 1:
                              /*  for (ServerSomthing vr : Server.serverList) {
                                    vr.send("0,0,0"+ "\n"); // отослать принятое сообщение с привязанного клиента всем остальным влючая его
                                }*/
                                System.out.println("Высылаем ответ на 0 с клиента в виде: 0,0,0");
                                break;
                            default:
                               /* for (ServerSomthing vr : Server.serverList) {
                                    vr.send("Команда не узнана"); // отослать принятое сообщение с привязанного клиента всем остальным влючая его
                                }*/
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


         catch (IOException e) {
          /*  this.downService();*/
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
    /*
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
    }*/
}


