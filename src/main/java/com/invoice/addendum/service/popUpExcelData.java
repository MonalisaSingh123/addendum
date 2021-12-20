package com.invoice.addendum.service;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class popUpExcelData {

    public static void main(String[] args) {

        String[] optionsToChoose1 = {"ENC/07413","ENC/04541","ENC/05980","ENC/08427","ENC/04795","ENC/06150"};
        String[] optionsToChoose2 = {"Sony ID"};
        String[] optionsToChoose3 = {"Name"};
        String[] optionsToChoose4 = {"Amount"};
        String[] optionsToChoose5 = {"Particulars"};
        String[] optionsToChoose6 = {"Month"};
        String[] optionsToChoose7 = {"Inv Date"};

        JFrame jFrame = new JFrame();

        JRadioButton btn1 = new JRadioButton("ENC ID");
        JRadioButton btn2 = new JRadioButton("Sony ID");
        btn1.setBounds(20, 40, 100, 20);
        btn2.setBounds(140, 40, 100, 20);
        ButtonGroup bg = new ButtonGroup();
        bg.add(btn1);
        bg.add(btn2);

        JComboBox<String> jComboBox1 = new JComboBox<>(optionsToChoose1);
        JComboBox<String> jComboBox2 = new JComboBox<>(optionsToChoose2);
        JComboBox<String> jComboBox3 = new JComboBox<>(optionsToChoose3);
        JComboBox<String> jComboBox4 = new JComboBox<>(optionsToChoose4);
        JComboBox<String> jComboBox5 = new JComboBox<>(optionsToChoose5);
        JComboBox<String> jComboBox6 = new JComboBox<>(optionsToChoose6);
        JComboBox<String> jComboBox7 = new JComboBox<>(optionsToChoose7);

        jComboBox1.setBounds(40, 70, 100, 20);
        jComboBox2.setBounds(160, 70, 100, 20);
        jComboBox3.setBounds(280, 70, 100, 20);
        jComboBox4.setBounds(400, 70, 100, 20);
        jComboBox5.setBounds(520, 70, 100, 20);
        jComboBox6.setBounds(640, 70, 100, 20);
        jComboBox7.setBounds(760, 70, 100, 20);

        JButton jButton = new JButton("Done");
        jButton.setBounds(30, 200, 90, 20);

        JLabel jLabel = new JLabel();
        jLabel.setBounds(90, 100, 400, 100);

        jFrame.add(jButton);
        jFrame.add(jComboBox1);
        jFrame.add(jLabel);
        jFrame.add(btn1);
        jFrame.add(btn2);


        JButton jButton2 = new JButton("Done");
        jButton2.setBounds(150, 200, 90, 20);

        JLabel jLabel2 = new JLabel();
        jLabel2.setBounds(90, 100, 400, 100);

        jFrame.add(jButton2);
        jFrame.add(jComboBox2);
        jFrame.add(jLabel2);



        JLabel jLabel3 = new JLabel();
        jLabel3.setBounds(90, 100, 400, 100);



        //jFrame.add(jButton3);
        jFrame.add(jComboBox3);
        jFrame.add(jLabel3);
        jFrame.add(jComboBox4);
        jFrame.add(jComboBox5);
        jFrame.add(jComboBox6);
        jFrame.add(jComboBox7);

        jFrame.setLayout(null);
        jFrame.setSize(950, 550);
        jFrame.setVisible(true);


        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedFruit1 = "You selected " + jComboBox1.getItemAt(jComboBox1.getSelectedIndex());
                String selectedFruit2 = "You selected " + jComboBox2.getItemAt(jComboBox2.getSelectedIndex());
                String selectedFruit3 = "You selected " + jComboBox3.getItemAt(jComboBox3.getSelectedIndex());
                String selectedFruit4 = "You selected " + jComboBox4.getItemAt(jComboBox4.getSelectedIndex());
                jLabel.setText(selectedFruit1);
                jLabel2.setText(selectedFruit2);
                jLabel.setText(selectedFruit3);
            }
        });

    }
}
