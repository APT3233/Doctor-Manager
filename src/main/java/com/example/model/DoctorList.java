package com.example.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DoctorList {
    private ArrayList<Doctor> DoctorList;
    private static final String URL = "jdbc:mysql://localhost:3306/user_db";
    private static final String USER = "";    // replace user and password
    private static final String PASSWORD = "";

    public DoctorList(){
        this.DoctorList = new ArrayList<>();
        //readFromFile("inputData.txt");
        getDoctors();   // read data from database
    }
    

    public void setList(ArrayList<Doctor> list){
        this.DoctorList = list;
    }

    public ArrayList<Doctor> geList(){
        return DoctorList;
    }

    public boolean addNew(Doctor newD) {
        if (DoctorList.contains(newD) || newD.getAvai().isEmpty()  || 
            newD.getCode().isEmpty() || newD.getName().isEmpty() || 
            newD.getSpecialty().isEmpty() || newD.getEmail().isEmpty()) 
            return false;
        
        for(Doctor obj : DoctorList)
            if(obj.getCode().equals(newD.getCode()))
                return false;
        

        DoctorList.add(newD);
        return true;
    }

    public void readFromFile(String filename) {
        String currentDir = System.getProperty("user.dir");
        Path filePath = Paths.get(currentDir, "src", "main", "resources", filename);

        try (BufferedReader file = new BufferedReader(new FileReader(filePath.toFile()))) {
            String line = "";
            while ((line = file.readLine()) != null) {
                String[] parts = line.split(",");
                addNew(new Doctor(parts[0], parts[1], parts[2], parts[3], parts[4]));
            }
        } catch (Exception e) {
            System.out.println("Error to read file\n" + e.getMessage());
        }
    }

    public void saveToFile(String filename){
        try {
            BufferedWriter file = new BufferedWriter(new FileWriter(filename));
            for(Doctor obj : DoctorList){
                file.write(obj.toString());
                file.newLine();
            }
            file.close();
        } catch (Exception e) {
            System.out.println("Error to write file");
        }
    }

    public boolean update(String code, String name, String spec, String avai, String email){
        for(Doctor obj : DoctorList){
            if(obj.getCode().equals(code)){
                obj.setName(name);
                obj.setSpecialty(spec);
                obj.setAvai(avai);
                obj.setEmail(email);
                return true;
            }
        }
        return false;
    }

    public ArrayList<Doctor> search(Predicate<Doctor> condition){
        return DoctorList.stream()
                .filter(condition)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public boolean remove(Predicate<Doctor> condition){
        return DoctorList.removeIf(condition);
    }

    public ArrayList<Doctor> sort(Comparator<Doctor> comparator){
        return DoctorList.stream()
        .sorted(comparator)
        .collect(Collectors.toCollection(ArrayList::new));
    }

    public static boolean sendMessage(String botToken, String chatId, String message) {
        try {
            String apiUrlWithToken = String.format("https://api.telegram.org/bot%s/sendMessage", botToken);
            URL url = new URL(apiUrlWithToken);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            String jsonInputString = String.format("{\"chat_id\":\"%s\",\"text\":\"%s\"}", chatId, message);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int code = conn.getResponseCode();
            System.out.println("Response Code : " + code);
            if (code == HttpURLConnection.HTTP_OK)  {
                System.out.println("Message sent successfully.");
                return true;
            }
            else   System.out.println("Failed to send message.");
            
            

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    // <------ DATABASE METHOD ------>
    public ArrayList<Doctor> getDoctors() {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM doctors";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String code = resultSet.getString("code");
                String name = resultSet.getString("name");
                String specialty = resultSet.getString("specialty");
                String availability = resultSet.getString("availability");
                String email = resultSet.getString("email");

                Doctor doctor = new Doctor(code, name, specialty, availability, email);
                DoctorList.add(doctor);
            }
            System.out.println("\033[32m [+] Read data from database successfully !!\033[0m");

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DoctorList;
    }
    public ArrayList<Doctor> readDoctor() {
        try {
            ArrayList<Doctor> newList = new ArrayList<>();
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM doctors";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String code = resultSet.getString("code");
                String name = resultSet.getString("name");
                String specialty = resultSet.getString("specialty");
                String availability = resultSet.getString("availability");
                String email = resultSet.getString("email");
                
                Doctor doctor = new Doctor(code, name, specialty, availability, email);
                newList.add(doctor);
                
            }
            System.out.println("\033[32m [+] Read Doctor from database successfully !!\033[0m");
            connection.close();
            return newList;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean addDoctor(String code, String name, String specialty, String availability, String email) {
        
        try {
            // check
            ArrayList<Doctor> checkDatabase;
            for(Doctor obj :  checkDatabase = readDoctor())
                if(obj.getCode().equals(code))
                    return false;

            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String insertSQL = "INSERT INTO doctors (code, name, specialty, availability, email) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);

            preparedStatement.setString(1, code);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, specialty);
            preparedStatement.setString(4, availability);
            preparedStatement.setString(5, email);
            preparedStatement.executeUpdate();
            connection.close();

            System.out.println("\033[32m [+] ADD new Doctor to DATABASE\033[0m");
            return true;
            
        } catch (Exception e) {
            System.out.println("Error to write database");
            e.printStackTrace();
            return false;
        }
    }
    public ArrayList<Doctor> searchDoctorByCode(String code) {
        String query = "SELECT * FROM doctors WHERE code = ?";
        ArrayList<Doctor> newList = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, code);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String specialty = resultSet.getString("specialty");
                String availability = resultSet.getString("availability");
                String email = resultSet.getString("email");

                Doctor doctor = new Doctor(code, name, specialty, availability, email);
                newList.add(doctor);
                connection.close();
                System.out.printf("\033[32m [+] Found %s in DATABASE\033[0m\n", doctor.getCode());
                return newList;
            } else {
                connection.close();
                return null; 
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public boolean updateDoctor(String code, String newName, String newSpecialty, String newAvailability, String newEmail) {
        String updateSQL = "UPDATE doctors SET name = ?, specialty = ?, availability = ?, email = ? WHERE code = ?";
        
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);    
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
    
            preparedStatement.setString(1, newName);
            preparedStatement.setString(2, newSpecialty);
            preparedStatement.setString(3, newAvailability);
            preparedStatement.setString(4, newEmail);
            preparedStatement.setString(5, code);
    
            String formattedQuery = String.format("UPDATE doctors SET name = '%s', specialty = '%s', availability = '%s', email = '%s' WHERE code = '%s'", 
                                                    newName, newSpecialty, newAvailability, newEmail, code);
            System.out.println("\n\n\n\n" + formattedQuery + "\n\n\n\n");
    
            int rowsAffected = preparedStatement.executeUpdate();
            preparedStatement.clearParameters();
            
            connection.close();
            System.out.println("\033[32m [+] Update 1 commit to DATABASE\033[0m");
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    public boolean deleteDoctorsByCodes(List<String> codes) {
        if (codes == null || codes.isEmpty()) 
            return false;
        
    
        StringBuilder deleteSQL = new StringBuilder("DELETE FROM doctors WHERE code IN (");
        for (int i = 0; i < codes.size(); i++) {
            deleteSQL.append("?");
            if (i < codes.size() - 1) 
                deleteSQL.append(",");
            
        }
        deleteSQL.append(")");
    
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL.toString());
    
            for (int i = 0; i < codes.size(); i++) 
                preparedStatement.setString(i + 1, codes.get(i));
            
    
            System.out.println("\n\n\n\n" + preparedStatement.toString() + "\n\n\n\n");
            int rowsAffected = preparedStatement.executeUpdate();
            connection.close();
            System.out.println("\033[32m [+] Delete " + rowsAffected + " Doctors from DATABASE\033[0m");
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println("Delete doctors in database failed");
            e.printStackTrace();
            return false;
        }
    }
    



}
