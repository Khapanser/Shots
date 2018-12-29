package main.java;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Controller{
    public static String uuid;
    public static List<Integer> cards = new ArrayList<>();
    public static String role;
    public static ClientSomthing cls;
    public static String nickname;

    //
    //public static ObservableList<String> items = FXCollections.observableArrayList("RUBY", "APPLE");
    public static ObservableList<String> items = FXCollections.observableArrayList();

    //тестовые картинки
    private final Image IMAGE_RUBY  = new Image("https://upload.wikimedia.org/wikipedia/commons/f/f1/Ruby_logo_64x64.png");
    private final Image IMAGE_APPLE  = new Image("http://antaki.ca/bloom/img/windows_64x64.png");
    //private final Image IMAGE_VISTA  = new Image("http://antaki.ca/bloom/img/windows_64x64.png");
    //private final Image IMAGE_TWITTER = new Image("http://files.softicons.com/download/social-media-icons/fresh-social-media-icons-by-creative-nerds/png/64x64/twitter-bird.png");
    //массив с этими картинками
    public Image[] listOfImages = {IMAGE_RUBY, IMAGE_APPLE};
    //test inisialization
    //ListView<String> listView = new ListView<String>();




   // listView.setItems(items);

    @FXML
    public Button startButton;
    @FXML
    public Label idLabel;
    @FXML
    public Label roleLabel;
    @FXML
    public Label cardsLabel;
    @FXML
    public Label nameLabel;
    @FXML
    public ListView cardList;




//конструктор:
    public Controller(){
       this.cls =  new ClientSomthing("localhost", 8080);
    }
// TODO переписать доступ ко всем статическим полям через сеттеры и геттеры.
    public static void setID(String s){
        uuid = s;
    }

    public void messFromNameCtrl(String s){
        this.nickname = s;
        System.out.println("НИКНЕЙМ ЗАПИСАЛСЯ НА КЛИЕНТЕ: "+nickname);
    }



    public void startButtonAction(ActionEvent actionEvent) {
        System.out.println("Start button clicked");
        try {
            cls.out.write("0,"+nickname+"\n");
            cls.out.flush();
        } catch (Exception io){System.out.println("Ошибка при попытке отправить сообщение на сервер при нажатии Start Button");}

    }

    //add ImageViews into the ListView




//----------------------------------------------------------------------------------------------------------------------
/** Ниже описан клиент, содержащий протокол общения с сервером и выполняющий соответствующие обновления GUI
    с помощью Platform.runLater */

    public class ClientSomthing {

        private Socket socket;
        public BufferedReader in; // поток чтения из сокета
        public BufferedWriter out; // поток чтения в сокет
        public BufferedReader inputUser; // поток чтения с консоли
        private String addr; // ip адрес клиента
        private int port; // порт соединения
        private String nick; // имя клиента
        private String id; //client's ID
        private Date time;
        private String dtime;
        private SimpleDateFormat dt1;



        public ClientSomthing(String addr, int port) {
            System.out.println("ClientSomthing started!");
            this.addr = addr;
            this.port = port;
            try {
                this.socket = new Socket(addr, port);
            } catch (IOException e) {
                System.err.println("Socket failed");
            }
            try {
                // потоки чтения из сокета / записи в сокет, и чтения с консоли
                inputUser = new BufferedReader(new InputStreamReader(System.in));
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                this.pressNickname(); // перед началом необходимо спросит имя
                new ClientSomthing.ReadMsg().start(); // нить читающая сообщения из сокета в бесконечном цикле
                new ClientSomthing.WriteMsg().start(); // нить пишущая сообщения в сокет приходящие с консоли в бесконечном цикле
            } catch (IOException e) {
                // Сокет должен быть закрыт при любой
                // ошибке, кроме ошибки конструктора сокета:
                ClientSomthing.this.downService();
            }
            // В противном случае сокет будет закрыт
            // в методе run() нити.
        }

        /**
         * просьба ввести имя,
         * и отсылка эхо с приветсвием на сервер
         */

        private void pressNickname() {
            System.out.print("Press your nick: ");
            try {

// TODO Здесь надо отправить значение из ModalWindow. nickname = inputUser.readLine();

                out.write("test" + "\n");
                out.flush();
            } catch (IOException ignored) {
            }
        }

        /**
         * закрытие сокета
         */
        private void downService() {
            try {
                if (!socket.isClosed()) {
                    socket.close();
                    in.close();
                    out.close();
                }
            } catch (IOException ignored) {
            }
        }

        // нить чтения сообщений с сервера
        public class ReadMsg extends Thread {
            @Override
            public void run() {

                String str;
                //String cards6 = "";
                //TODO: надо заменить на ArrayList и конвертировать, т.к. массив фиксированный
                List<String> splittedMessage;
                String[] strSplit = {"", "", "", "", "", "", "", ""};
                String[] arrSplitMess;
                try {
                    while (true) {
                        System.out.println("В цикле получения сообщения... ");
                        str = in.readLine(); // ждем сообщения с сервера

                        //if(!str.equals("")){
                        System.out.println("string 111 -->  " + str); // пишем сообщение с сервера на консоль
                        splittedMessage = Arrays.asList(str.split(","));
                        arrSplitMess = (String[])splittedMessage.toArray();
                  /*  if (str.equals("stop")) {
                        ClientSomthing.this.downService(); // харакири
                        break; // выходим из цикла если пришло "stop"
                    }*/
                        try {
                            int firstParam = Integer.parseInt(arrSplitMess[0]);
                            switch (firstParam) {
                                case 0:
                                    System.out.println("Клиент отправил 0 и сервер ответил 0!" + "\n");
                                    id = arrSplitMess[1];
                                    System.out.println("Server set ID for this client: " + id);
                                    Controller.setID(id);

                                    //Controller.idLabel.setText(id);
                                    break;
                                case 1:
                                    System.out.println("Клиент получил 1 + карты + роль!");
                                    for (int t = 1 ;t<7; t++){
                                        System.out.println("t = "+t+", a arrSplitMess[t] = "+ arrSplitMess[t]);
                                        Controller.cards.add(Integer.parseInt(arrSplitMess[t]));
                                        //add to observable
                                        items.add(arrSplitMess[t]);
                                    }
                                    Controller.role = arrSplitMess[7];

                                    // Avoid throwing IllegalStateException by running from a non-JavaFX thread.
                                    Platform.runLater(
                                            () -> {
                                                idLabel.setText("   "+uuid);
                                                roleLabel.setText("   "+role);
                                                cardsLabel.setText(cards.toString());
                                                startButton.setDisable(true);

                                                //show cards
                                                cardList.setItems(items);
                                                cardList.setCellFactory(param -> new ListCell<String>() {
                                                    private ImageView imageView = new ImageView();
                                                    @Override
                                                    public void updateItem(String name, boolean empty){
                                                        super.updateItem(name,empty);
                                                        if(empty){
                                                            setText(null);
                                                            setGraphic(null);
                                                        } else{
                                                            if(name.equals("1"))
                                                                imageView.setImage(listOfImages[0]);
                                                            else if(name.equals("2"))
                                                                imageView.setImage(listOfImages[1]);
                                                            setText(name);
                                                            setGraphic(imageView);
                                                        }
                                                    }
                                                        });
                                            }
                                    );
                                //TODO ответ на код

                                default:
                                    System.out.println("Нет протокола под такой сценарий" + "\n");
                                    break;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("NumberFormatException e");
                        }

                    }
                } catch (IOException e) {
                    ClientSomthing.this.downService();
                }
            }
        }

        // нить отправляющая сообщения приходящие с консоли на сервер
        public class WriteMsg extends Thread {

            @Override
            public void run() {
                while (true) {
                    String userWord;
                    try {
                        time = new Date(); // текущая дата
                        dt1 = new SimpleDateFormat("HH:mm:ss"); // берем только время до секунд
                        dtime = dt1.format(time); // время
                        userWord = inputUser.readLine(); // сообщения с консоли
                        if (userWord.equals("stop")) {
                            out.write("stop" + "\n");
                            ClientSomthing.this.downService(); // харакири
                            break; // выходим из цикла если пришло "stop"
                        } else {
                            out.write(userWord + "\n");
                            // out.write("(" + dtime + ") " + nickname + ": " + userWord + "\n"); // отправляем на сервер
                        }
                        out.flush(); // чистим
                    } catch (IOException e) {
                        ClientSomthing.this.downService(); // в случае исключения тоже харакири

                    }

                }
            }
        }
    }

}
