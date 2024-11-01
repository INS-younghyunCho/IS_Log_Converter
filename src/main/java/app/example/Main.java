package app.example;

import app.example.core.UIOperator;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Application started.");
        SwingUtilities.invokeLater(() -> {
            UIOperator frame = new UIOperator();
            frame.setVisible(true);
        });
    }
}