package com.shift;


import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import java.util.HashSet;
import java.util.Set;

public class UserData {
    private String name;
    private SimpleDate birthdate;

    private float height;
    private float weight;
    private BloodType bloodType;
    private Set<String> allergies;
    private String cc;
    private String sns;

    private UserData() {
        allergies = new HashSet<>();
        birthdate = new SimpleDate(1, 1, 2000);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setBirthdate(int day, int month, int year) {
        this.birthdate.day = day;
        this.birthdate.month = month;
        this.birthdate.year = year;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public void addAllergy(String allergy) {
        allergies.add(allergy);
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public void setSns(String sns) {
        this.sns = sns;
    }

    public String getName() {
        return name;
    }

    public SimpleDate getBirthdate() {
        return birthdate;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public Set<String> getAllergies() {
        return allergies;
    }

    public String getCc() {
        return cc;
    }

    public String getSns() {
        return sns;
    }

    // Cria um objeto UserData baseado nos preferences
    public static UserData save(SharedPreferences prefs) {
        // Obter preferências

        // Criar e definir paramentros do objeto
        UserData userData = new UserData();
        userData.setName(prefs.getString("nameInput", ""));

        int day = prefs.getInt("date_day", 1);
        int month = prefs.getInt("date_month", 5);
        int year = prefs.getInt("date_year", 2025);
        userData.setBirthdate(day, month, year);

        float height = prefs.getFloat("height", 0);
        float weight = prefs.getFloat("weight", 0);
        userData.setHeight(height);
        userData.setWeight(weight);

        // TODO: adicionar as alergias


        int bloodType = prefs.getInt("bloodType", 0);
        int plusMinus = prefs.getInt("plusMinus", 0);
        userData.setBloodType(BloodType.fromComponents(bloodType, plusMinus));

        String ccNumber = prefs.getString("cc", "");
        String snsNumber = prefs.getString("sns", "");
        userData.setCc(ccNumber);
        userData.setSns(snsNumber);

        // TODO: adicionar doenças

        return userData;
    }

    // Transforma-se em bytes para enviar por NFC
    public byte[] serialize() {
        Gson gson = new Gson();
        // Encriptar
        String key = "D6uZAGUTCX9DQdlrls37zR6clbMCB7gu";
        String jsonData = gson.toJson(this);

        byte[] encryptedData;
        try {
            encryptedData = Cryptography.encrypt(jsonData, key);
        } catch (Exception e) {
            encryptedData = new byte[0];
            Log.e("UserData", "Erro na encriptação!");
            e.printStackTrace();
        }

        return encryptedData;
    }
}
