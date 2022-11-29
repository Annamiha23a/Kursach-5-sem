package view;

import model.*;
import org.jfree.data.category.DefaultCategoryDataset;
import tableModel.ClientTableModel;
import tableModel.MovieTableModel;
import tableModel.TicketTableModel;
import tableModel.addRecordTableModel;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;

public class UserFrame  extends JFrame{
    private JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JButton closeFrameButton;
    private JTextField mySurnameField;
    private JTextField myNameField;
    private JTextField myLastnameField;
    private JTextField myPhoneField;
    private JButton editMyPersonalDataButton;
    private JTextField myWorkPhoneField;
    private JTextField myLoginField;
    private JPasswordField myPasswordField1;
    private JPasswordField myPasswordField2;
    private JButton editMyAuthorizationDataButton;
    private JTabbedPane tabbedPane2;
    private JTable workClientsTable;
    private JTable recordClientsTable;
    private JTable recordDoctorsTable;
    private JTable addPlaceTable;
    private JTextField commentTextField;
    private JButton addRecordButton;
    private JTable recordsTable;
    private JButton addClientButton;
    private JButton clearNewClientFormButton;
    private JTextField newClientPhoneField;
    private JTextField newClientAgeField;
    private JTextField newClientSurnameField;
    private JTextField newClientNameField;
    private JTextField newClientLastnameField;

    private JTextField newClientBirthDateField;
    private JTextField newClientAddressField;
    private JButton editClientButton;
    private JCheckBox deleteClientCheckBox;
    private JButton deleteClientButton;
    private JTextField editClientPhoneField;
    private JTextField editClientAgeField;
    private JTextField editClientSurnameField;
    private JTextField editClientNameField;
    private JTextField editClientLastnameField;


    private JScrollPane workClientsTab;
    private JButton printCheckButton;
    private JButton statsButton;
    private ArrayList<Admin> admins = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Movie> movies = new ArrayList<>();
    private ArrayList<Movie> currentmovies = new ArrayList<>();
    private ArrayList<Ticket> tickets = new ArrayList<>();
    private ArrayList<Places> currentplaces = new ArrayList<>();
    private ArrayList<Client> clients = new ArrayList<>();
    private ObjectOutputStream output = MainFrame.output;
    private ObjectInputStream input = MainFrame.input;
    private int USER_ID;


    //-------------------------------ИНИЦИАЛИЗАЦИЯ ФРЕЙМА-------------------------------


    @SuppressWarnings("unchecked")
    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Работник кинотеатра");
        setContentPane(mainPanel);
        setResizable(false);
        readData();
        TableModel clientsModel = new ClientTableModel(clients);
        workClientsTable.setModel(clientsModel);
        workClientsTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        recordClientsTable.setModel(clientsModel);
        recordClientsTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        TableModel doctorsModel = new MovieTableModel(new ArrayList<>());
        recordDoctorsTable.setModel(doctorsModel);
        recordDoctorsTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        addPlaceTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        TableModel visitsModel = new TicketTableModel(tickets, clients);
        recordsTable.setModel(visitsModel);
        recordsTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        pack();
        setLocationRelativeTo(null);
    }


    //-------------------------------КОНСТРУКТОР ФРЕЙМА-------------------------------


    public UserFrame(int user_id){
        this.USER_ID = user_id;
        initComponents();
        closeFrameButton.addActionListener(e -> closeFrameActionPerformed());
        editMyPersonalDataButton.addActionListener(e -> editMyPersonalDataActionPerformed());
        editMyAuthorizationDataButton.addActionListener(e -> editMyAuthorizationDataActionPerformed());
        clearNewClientFormButton.addActionListener(e -> clearNewClientFormActionPerformed());
        addClientButton.addActionListener(e -> addClientActionPerformed());
        workClientsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                workClientsTableActionPerformed();
            }
        });
        editClientButton.addActionListener(e -> editClientActionPerformed());
        recordClientsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                recordClientsTableActionPerformed();
            }
        });
        recordDoctorsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                recordDoctorsTableActionPerformed();
            }
        });
        addRecordButton.addActionListener(e -> addRecordButtonActionPerformed());
        deleteClientButton.addActionListener(e -> deleteClientActionPerformed());
        printCheckButton.addActionListener(e -> printCheckButtonActionPerformed());
        statsButton.addActionListener(e -> statsButtonActionPerformed());
    }


    //-------------------------------ВСПОМОГАТЕЛЬНЫЕ ФУНКЦИИ-------------------------------


    public void readData(){
        try{
            output.writeObject("getAllUsers");
            this.users = (ArrayList<User>) input.readObject();
            for(int i = 0; i < users.size(); i++){
                if(USER_ID == users.get(i).getId()){
                    User user = users.get(i);
                    mySurnameField.setText(user.getSurname());
                    myNameField.setText(user.getName());
                    myLastnameField.setText(user.getLastname());
                    myPhoneField.setText(user.getPhone());
                    myWorkPhoneField.setText(user.getWork_phone());
                    myLoginField.setText(user.getLogin());
                    myPasswordField1.setText(user.getPassword());
                    myPasswordField2.setText(user.getPassword());
                }
            }
            output.writeObject("getAllClients");
            this.clients = (ArrayList<Client>)input.readObject();
            output.writeObject("getAllAdmins");
            this.admins = (ArrayList<Admin>) input.readObject();
            output.writeObject("getAllDoctors");
            this.movies = (ArrayList<Movie>) input.readObject();
            output.writeObject("getAllVisits");
            this.tickets = (ArrayList<Ticket>) input.readObject();
        }
        catch (Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void refreshData(){
        admins.clear();
        users.clear();
        movies.clear();
        clients.clear();
        readData();
        TableModel clientsModel = new ClientTableModel(clients);
        workClientsTable.setModel(clientsModel);
        recordClientsTable.setModel(clientsModel);
        currentplaces.clear();
        TableModel scheduleModel = new addRecordTableModel(currentplaces);
        addPlaceTable.setModel(scheduleModel);
        currentmovies.clear();
        TableModel doctorsModel = new MovieTableModel(currentmovies);
        recordDoctorsTable.setModel(doctorsModel);
        TableModel visitsModel = new TicketTableModel(tickets, clients);
        recordsTable.setModel(visitsModel);
        recordsTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    }

    private Boolean checkLogin(String login) {
        if (login.equals("")) {
            JOptionPane.showMessageDialog(null, "Вы не ввели логин!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (login.length() <= 4 || login.length() >= 15) {
            JOptionPane.showMessageDialog(null, "Логин должен быть больше 4 и меньше 15 символов!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return false;
        } else {
            for (int i = 0; i < admins.size(); i++) {
                if (login.equals(admins.get(i).getLogin())) {
                    JOptionPane.showMessageDialog(null, "Данный логин уже есть в системе!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
            for (int i = 0; i < users.size(); i++) {
                if (login.equals(users.get(i).getLogin())) {
                    JOptionPane.showMessageDialog(null, "Данный логин уже есть в системе!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
            return true;
        }
    }

    private Boolean checkPassword(String password, String provePassword) {
        if(password.equals("") || provePassword.equals("")) {
            JOptionPane.showMessageDialog(null, "Вы не ввели пароль!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if(password.length() <= 4 || password.length() >= 15) {
            JOptionPane.showMessageDialog(null, "Пароль должен быть больше 4 и меньше 15 символов!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if(!password.equals(provePassword)){
            JOptionPane.showMessageDialog(null, "Пароль и его подтверждение не совпадают!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else return true;
    }

    private Boolean checkPassportAndDistrict(String passportNumber, String district){
        if(passportNumber.equals("")){
            JOptionPane.showMessageDialog(null, "Вы не ввели номер телефона!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(district.equals("")){
            JOptionPane.showMessageDialog(null, "Вы не ввели возраст!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private Boolean checkPassport(String passportNumber){
        for (int i = 0; i < clients.size(); i++) {
            if (passportNumber.equals(clients.get(i).getPhone())) {
                JOptionPane.showMessageDialog(null, "Данный номер телефона уже есть в системе!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    private void clearEditAndPasswordForm(){
        editClientPhoneField.setText("");
        editClientAgeField.setText("");
        editClientSurnameField.setText("");
        editClientNameField.setText("");
        editClientLastnameField.setText("");

    }

    //-------------------------------ФУНКЦИИ-СЛУШАТЕЛИ-------------------------------


    private void closeFrameActionPerformed(){
        new MainFrame().setVisible(true);
        dispose();
    }

    private void editMyPersonalDataActionPerformed(){
        if(mySurnameField.isEditable()) {
            try {
                User user = new User();
                user.setId(USER_ID);
                user.setSurname(mySurnameField.getText());
                user.setName(myNameField.getText());
                user.setLastname(myLastnameField.getText());
                user.setPhone(myPhoneField.getText());
                output.writeObject("updatePerson");
                output.writeObject(user);
                String result = (String) input.readObject();
                JOptionPane.showMessageDialog(null, result, "Результат", JOptionPane.INFORMATION_MESSAGE);
                if (result.equals("Успешно сохранено!")) {
                    for (int i = 0; i < users.size(); i++) {
                        if (USER_ID == users.get(i).getId()) {
                            User updateUser = users.get(i);
                            updateUser.setSurname(user.getSurname());
                            updateUser.setName(user.getName());
                            updateUser.setLastname(user.getLastname());
                            updateUser.setPhone(user.getPhone());
                            users.set(i, updateUser);
                        }
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
            mySurnameField.setEditable(false);
            myNameField.setEditable(false);
            myLastnameField.setEditable(false);
            myPhoneField.setEditable(false);
            editMyPersonalDataButton.setText("Редактировать личные данные");
        }
        else{
            mySurnameField.setEditable(true);
            myNameField.setEditable(true);
            myLastnameField.setEditable(true);
            myPhoneField.setEditable(true);
            editMyPersonalDataButton.setText("Сохранить");
        }
    }

    private void editMyAuthorizationDataActionPerformed(){
        if(myLoginField.isEditable()){
            if(!checkLogin(myLoginField.getText())) return;
            if(!checkPassword(myPasswordField1.getText(), myPasswordField2.getText())) return;
            try{
                ObjectOutputStream output = MainFrame.output;
                ObjectInputStream input = MainFrame.input;
                User user = new User();
                user.setId(USER_ID);
                user.setLogin(myLoginField.getText());
                user.setPassword(myPasswordField1.getText());
                user.setWork_phone(myWorkPhoneField.getText());
                output.writeObject("updateMyUserData");
                output.writeObject(user);
                String result = (String) input.readObject();
                JOptionPane.showMessageDialog(null, result, "Результат", JOptionPane.INFORMATION_MESSAGE);
                if (result.equals("Успешно сохранено!")) {
                    for (int i = 0; i < users.size(); i++) {
                        if (USER_ID == users.get(i).getId()) {
                            User updateUser = users.get(i);
                            updateUser.setLogin(user.getLogin());
                            updateUser.setWork_phone(user.getWork_phone());
                            users.set(i, updateUser);
                        }
                    }
                }
            }
            catch (Exception ex){
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
            myLoginField.setEditable(false);
            myPasswordField1.setEditable(false);
            myPasswordField2.setEditable(false);
            myWorkPhoneField.setEditable(false);
            editMyAuthorizationDataButton.setText("Редактировать данные авторизации");
        }
        else{
            myLoginField.setEditable(true);
            myPasswordField1.setEditable(true);
            myPasswordField2.setEditable(true);
            myWorkPhoneField.setEditable(true);
            editMyAuthorizationDataButton.setText("Сохранить");
        }
    }

    private void clearNewClientFormActionPerformed(){
        newClientPhoneField.setText("");
        newClientAgeField.setText("");
        newClientSurnameField.setText("");
        newClientNameField.setText("");
        newClientLastnameField.setText("");
        newClientPhoneField.setText("");
        newClientBirthDateField.setText("");
        newClientAddressField.setText("");
    }

    private void addClientActionPerformed(){
        if(!checkPassportAndDistrict(newClientPhoneField.getText(), newClientAgeField.getText())) return;
        if(!checkPassport(newClientPhoneField.getText())) return;
        try{
            Client client = new Client();
            client.setPhone(newClientPhoneField.getText());
            client.setAge(newClientAgeField.getText());
            client.setSurname(newClientSurnameField.getText());
            client.setName(newClientNameField.getText());
            client.setLastname(newClientLastnameField.getText());
            output.writeObject("insertClient");
            output.writeObject(client);
            String result = (String) input.readObject();
            JOptionPane.showMessageDialog(null, result, "Результат", JOptionPane.INFORMATION_MESSAGE);
            refreshData();
        }
        catch (Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void workClientsTableActionPerformed(){
        Client client = clients.get(workClientsTable.getSelectedRow());
        editClientPhoneField.setText(client.getPhone());
        editClientAgeField.setText(client.getAge());
        editClientSurnameField.setText(client.getSurname());
        editClientNameField.setText(client.getName());
        editClientLastnameField.setText(client.getLastname());

    }

    private void editClientActionPerformed(){
        try{
            Client client;
            try{
                client = clients.get(workClientsTable.getSelectedRow());
                if(!checkPassportAndDistrict(editClientPhoneField.getText(), editClientAgeField.getText())) return;
                if(!editClientPhoneField.getText().equals(client.getPhone())){
                    if(!checkPassport(editClientPhoneField.getText())) return;
                }
                client.setPhone(editClientPhoneField.getText());
                client.setAge(editClientAgeField.getText());
                client.setSurname(editClientSurnameField.getText());
                client.setName(editClientNameField.getText());
                client.setLastname(editClientLastnameField.getText());
            }
            catch (Exception ex){
                JOptionPane.showMessageDialog(null, "Нужно выбрать клиента из списка!" , "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }
            output.writeObject("updateClient");
            output.writeObject(client);
            JOptionPane.showMessageDialog(null, input.readObject(), "Результат", JOptionPane.INFORMATION_MESSAGE);
            refreshData();
            clearEditAndPasswordForm();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void recordClientsTableActionPerformed(){
        Client client = clients.get(recordClientsTable.getSelectedRow());
        currentmovies.clear();
        for(int i = 0; i < movies.size(); i++){
            if(client.getAge().equals(movies.get(i).getAgeLimit())){
                currentmovies.add(movies.get(i));
            }
        }
        TableModel moviesModel = new MovieTableModel(currentmovies);
        recordDoctorsTable.setModel(moviesModel);
        currentplaces.clear();
        TableModel scheduleModel = new addRecordTableModel(currentplaces);
        addPlaceTable.setModel(scheduleModel);
    }

    private void recordDoctorsTableActionPerformed(){
        Movie movie = currentmovies.get(recordDoctorsTable.getSelectedRow());
        try{
            output.writeObject("getRecordsPlaces");
            output.writeObject(movie);
            currentplaces.clear();
            currentplaces = (ArrayList<Places>) input.readObject();
            TableModel placeModel = new addRecordTableModel(currentplaces);
            addPlaceTable.setModel(placeModel);
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addRecordButtonActionPerformed(){
        Client client = clients.get(recordClientsTable.getSelectedRow());
        Movie movie = currentmovies.get(recordDoctorsTable.getSelectedRow());
        Places places = currentplaces.get(addPlaceTable.getSelectedRow());
        Ticket ticket = new Ticket();
        try{
            if(places.getPhoneNumber() == null || places.getPhoneNumber().equals("")){
                ticket.setDate(places.getDate());
                ticket.setTime(places.getTime());
                ticket.setComment(commentTextField.getText());
                ticket.setMovie_id(movie.getId());
                ticket.setClient_id(client.getId());
                output.writeObject("insertTicket");
                output.writeObject(ticket);
                String result = (String) input.readObject();
                JOptionPane.showMessageDialog(null, result, "Результат", JOptionPane.INFORMATION_MESSAGE);
                refreshData();
            }
            else{
                JOptionPane.showMessageDialog(null, "На данное время уже есть запись!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }catch (Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteClientActionPerformed(){
        try{
            if(deleteClientCheckBox.isSelected()){
                try{
                    Client client = clients.get(workClientsTable.getSelectedRow());
                    output.writeObject("deleteClient");
                    output.writeObject(client);
                    String result = (String) input.readObject();
                    JOptionPane.showMessageDialog(null, result, "Результат", JOptionPane.INFORMATION_MESSAGE);
                }
                catch (Exception e){
                    JOptionPane.showMessageDialog(null, "Вы не выбрали клиента из списка!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
                refreshData();
                clearEditAndPasswordForm();
            }
        }
        catch (Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
        deleteClientCheckBox.setSelected(false);
    }

    private void printCheckButtonActionPerformed(){
        try{
            try{
                Ticket ticket = tickets.get(recordsTable.getSelectedRow());
                output.writeObject("getCheck");
                output.writeObject(ticket);

            }
            catch (Exception e){
                JOptionPane.showMessageDialog(null, "Вы не выбрали клиента из списка!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String result = (String) input.readObject();
            String[] message = result.split("#");
            for(int i = 0; i<message.length;i++){
                if(message[i] == null) message[i] = "";
            }
            String filePath = "С:\\\\Work\\" + message[2] + ".txt";
            File file= new File(filePath);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write("------Билет на сеанс------\r\n");
            writer.write("Дата:" + message[0] + "\r\n");
            writer.write("Время:" + message[1] + "\r\n");
            writer.write("Место:" + message[3] + "\r\n");
            writer.write("Номер телефона:" + message[2] + "\r\n");
            writer.write("--------------------------\r\n");
            writer.close();
            JOptionPane.showMessageDialog(null, "Результат распечатан в файл на диск С!", "Результат", JOptionPane.INFORMATION_MESSAGE);
            refreshData();
            clearEditAndPasswordForm();
        }
        catch (Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
        deleteClientCheckBox.setSelected(false);
    }

    private void statsButtonActionPerformed(){
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
        dataSet.setValue(clients.size(), "", "Количество клиентов");
        dataSet.setValue(tickets.size(), "", "Количество записей на будущее время");
        MainFrame.createGraph(dataSet, "Статистика активности клиентов");
    }
}
