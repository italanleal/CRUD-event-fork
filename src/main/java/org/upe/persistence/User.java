package org.upe.persistence;

import java.io.*;
import java.util.ArrayList;

public class User implements UserInterface {
    private static final String dbPath = "DB/user.csv";
    private String name;
    private String CPF;
    private String email;
    private String attendeeOn;
    private String ownerOf;

    public User(String name, String email, String CPF, String attendeeOn, String ownerOf) {
        this.CPF = CPF;
        this.email = email;
        this.name = name;
        this.attendeeOn = attendeeOn;
        this.ownerOf = ownerOf;
    }
    //Esse método é responsável por ler o arquivo CSV e retornar um ArrayList de usuários
    public static ArrayList<User> getAllUsers() {
        ArrayList<User> usersArray = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(dbPath));
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] newUserLine = line.split(",", -1);
                User user = new User(newUserLine[0],
                        newUserLine[1], newUserLine[2],
                        newUserLine[3] == null ? "" : newUserLine[3],
                        newUserLine[4] == null ? "" : newUserLine[4]);
                usersArray.add(user);
            }
            reader.close();
        } catch(IOException e) {
            e.printStackTrace();
        }

        return usersArray;
    }

    private static void updateFileData(ArrayList<User> newData) {
        try {
            BufferedWriter write = new BufferedWriter(new FileWriter(dbPath));
            write.write("name,email,cpf,attendeeOn,ownerOf\n");
            for (User user : newData) {
                String line = String.format("%s,%s,%s,%s,%s\n", user.getName(), user.getEmail(),user.CPF, user.attendeeOn, user.ownerOf);
                write.write(line);
            }
            write.close();
        } catch (IOException e) {
         e.printStackTrace();
        }
    }

    public static String findByCPF() {
        return "";
    }

    public static void deleteUser(String CPF) {
        ArrayList<User> users = User.getAllUsers();

        for (User user : users) {
            if (user.getCPF().equals(CPF)) {
                int index = users.indexOf(user);
                users.remove(index);
                break;
            }
        }

        updateFileData(users);
    }

    public static void updateUserEmail(String CPF, String newEmail) {
        ArrayList<User> users = User.getAllUsers();

        for (User user : users) {
            if (user.getCPF().equals(CPF)) {
                user.email = newEmail;
            }
        }

        updateFileData(users);
    }

    public static void createUser(String name, String CPF, String email) {
        String newLine = String.format("%s,%s,%s,,", name, email,CPF);
        try {
            FileWriter writer = new FileWriter(dbPath, true);
            writer.append(System.lineSeparator());
            writer.append(newLine);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getCPF() {
        return this.CPF;
    }

    public String getEmail() {
        return this.email;
    }

    public String getName() {
        return this.name;
    }
//    //adicionar formatação
//    public String[] getAttendeeOn() {
//        return attendeeOn;
//    }
//    //adicionar formatação
//    public String[] getOwnerOf() {
//        return ownerOf;
//    }
}

