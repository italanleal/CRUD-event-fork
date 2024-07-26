package org.upe.persistence;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class UserUtility {
    protected static String CSV_FILE_PATH = "DB/user.csv";

    public static ArrayList<User> getAllUsers() {
        ArrayList<User> usersArray = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH));
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] newUserLine = line.split(",", -1);
                User user = new User(
                        newUserLine[0],
                        newUserLine[1],
                        newUserLine[2],
                        newUserLine[3] == null ? "" : newUserLine[3],
                        newUserLine[4] == null ? "" : newUserLine[4],
                        newUserLine[5] == null ? "" : newUserLine[5]
                );
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
            BufferedWriter write = new BufferedWriter(new FileWriter(CSV_FILE_PATH));
            write.write("name,email,cpf,attendeeOn,ownerOf\n");
            for (User user : newData) {
                String line = String.format("%s,%s,%s,%s,%s\n", user.getName(), user.getEmail(), user.CPF, user.attendeeOn,
                        user.ownerOf);
                write.write(line);
            }
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static UserInterface findByCPF(String CPF) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH));
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] newUserLine = line.split(",", -1);
                User user = new User(newUserLine[0],
                        newUserLine[1], newUserLine[2],
                        newUserLine[3] == null ? "" : newUserLine[3],
                        newUserLine[4] == null ? "" : newUserLine[4],
                        newUserLine[5] == null ? "" : newUserLine[5]);

                if (user.getCPF().equals(CPF)) {
                    reader.close();
                    return user;
                }
            }
            reader.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static UserInterface createUser(String name,  String email, String CPF) {

        if(findByCPF(CPF) != null) {
            return null;
        }
        try {
            String newLine = String.format("%s,%s,%s,,,", name, email,CPF);
            FileWriter writer = new FileWriter(CSV_FILE_PATH, true);
            writer.append(System.lineSeparator());
            writer.append(newLine);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new User(name, CPF, email, "", "","");
    }

    public static void updateUserEmail(String CPF, String newEmail) {
        ArrayList<User> users = UserUtility.getAllUsers();

        for (User user : users) {
            if (user.getCPF().equals(CPF)) {
                user.email = newEmail;
            }
        }

        updateFileData(users);
    }

    public static void deleteUser(String CPF) {
        ArrayList<User> users = UserUtility.getAllUsers();

        for (User user : users) {
            if (user.getCPF().equals(CPF)) {
                users.remove(user);
                break;
            }
        }

        updateFileData(users);
    }

    public static void addAttendeeOnEvent(String CPF, String eventID) {
        ArrayList<User> users = UserUtility.getAllUsers();

        for (User user : users) {
            if (user.getCPF().equals(CPF)) {
                user.attendeeOn += user.attendeeOn.isEmpty() ? eventID : "#" + eventID;
                break;
            }
        }
        UserUtility.updateFileData(users);
    }

    public static void addOwnerOnEvent(String CPF, String eventID) {
        ArrayList<User> users = UserUtility.getAllUsers();

        for (User user : users) {
            if (user.getCPF().equals(CPF)) {
                user.ownerOf += user.ownerOf.isEmpty() ? eventID : "#" + eventID;
                break;
            }
        }
        UserUtility.updateFileData(users);
    }

    public static boolean deleteAllAttendeesFromEvent(String eventID) {
        ArrayList<User> users = UserUtility.getAllUsers();

        for (User user : users) {
            if (user.attendeeOn.contains(eventID)) {
                String newString = "";
                for (int i = 0; i < user.getAttendeeOn().length; i++) {
                    String id = user.getAttendeeOn()[i];
                    if (!id.equals(eventID)) {
                        if (!newString.isEmpty()) {
                            newString += "#";
                        }
                        newString += id;
                    }
                }
                user.attendeeOn = newString;
            }
        }
        updateFileData(users);
        return true;
    }

    public static boolean deleteAttendeeEvent(String CPF, String eventID) {
        ArrayList<User> users = UserUtility.getAllUsers();

        for (User user : users) {
            if (user.getCPF().equals(CPF)) {
                String newString = "";
                for (int i = 0; i < user.getAttendeeOn().length; i++) {
                    String id = user.getAttendeeOn()[i];
                    if (!id.equals(eventID)) {
                        if (!newString.isEmpty()) {
                            newString += "#";
                        }
                        newString += id;
                    }
                }
                user.attendeeOn = newString;
                break;
            }
        }
        updateFileData(users);
        return true;
    }

    public static void deleteOwnerOf(String CPF, String eventID) {
        ArrayList<User> users = UserUtility.getAllUsers();

        for (User user : users) {
            if (user.getCPF().equals(CPF)) {
                String newString = "";
                for (int i = 0; i < user.getOwnerOf().length; i++) {
                    String id = user.getOwnerOf()[i];
                    if (!id.equals(eventID)) {
                        if (!newString.isEmpty()) {
                            newString += "#";
                        }
                        newString += id;
                    }
                }
                user.ownerOf = newString;
                break;
            }
        }
        updateFileData(users);
    }

    public static void addUserArticle(String CPF, String articleID) {
        ArrayList<User> users = UserUtility.getAllUsers();

        for (User user : users) {
            if (user.getCPF().equals(CPF)) {
                user.articleID += user.articleID.isEmpty() ? articleID : "#" + articleID;
                break;
            }
        }

        UserUtility.updateFileData(users);
    }
}
