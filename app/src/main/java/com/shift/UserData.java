package com.shift;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class UserData implements Serializable {
    private String name;
    private SimpleDate birthdate;

    private double height;
    private double weight;
    private BloodType bloodType;
    private Set<String> allergies;

    public UserData() {
        allergies = new HashSet<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setBirthdate(SimpleDate birthdate) {
        this.birthdate = birthdate;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public void addAllergy(String allergy) {
        allergies.add(allergy);
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

    public void save() {
    // TODO
    }
}
