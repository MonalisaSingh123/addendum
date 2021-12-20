package com.invoice.addendum.service;

import org.springframework.stereotype.Service;

import javax.swing.*;
import java.awt.*;

@Service
public class ExcelPopUpWindow extends JPanel {

    public ExcelPopUpWindow() {
        setLayout(new BorderLayout());
        Object[][] data = {{"7000018151", "Shift Allowance", "Sep'21", "6300", "ENC/07413", "Biplab Muduli", ""},
                           {"5013505462", "Shift Allowance", "Sep'21", "6300", "ENC/04541", "Harish Reddy", ""},
                           {"7000014422", "Shift Allowance", "Sep'21", "6500", "ENC/05980", "Kalinga Macha", ""},
                           {"7000021486", "Shift Allowance", "Sep'21", "7900", "ENC/08427", "Nagendra S", ""}};

        String[] columns = {"Sony ID","Particulars", "Month", "Amount (INR)", "ENC ID", "Name", "Inv Date"};;

        JTable table = new JTable(data,columns);

        JScrollPane scroll = new JScrollPane(table);
        add(scroll);
    }
    public static void displayUI()
    {
        JFrame f = new JFrame("Select Excel Data Table");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new ExcelPopUpWindow());
        f.setSize(1200, 350);
        f.setLocationByPlatform(true);
        f.setVisible(true);
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                displayUI();
            }
        });
    }
}

