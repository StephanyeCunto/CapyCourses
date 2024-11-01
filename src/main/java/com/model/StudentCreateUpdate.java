package com.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class StudentCreateUpdate {
    public StudentCreateUpdate(String name){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src\\main\\resources\\com\\bd_student.csv", true))) {
            writer.write(name+","+" "+","+" ");
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }
}
