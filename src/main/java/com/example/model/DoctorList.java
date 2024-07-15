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
import java.util.function.Predicate;
import java.util.stream.Collectors;




public class DoctorList {
    private ArrayList<Doctor> DoctorList;


    public DoctorList(){
        this.DoctorList = new ArrayList<>();
        readFromFile("inputData.txt");
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

}
