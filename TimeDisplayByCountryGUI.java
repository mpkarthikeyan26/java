import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class TimeDisplayByCountryGUI {

    private static final Map<String, String> countryToTimeZoneMap = new HashMap<>();

    static {
        countryToTimeZoneMap.put("USA", "America/New_York");
        countryToTimeZoneMap.put("UK", "Europe/London");
        countryToTimeZoneMap.put("Japan", "Asia/Tokyo");
        countryToTimeZoneMap.put("Australia", "Australia/Sydney");
        countryToTimeZoneMap.put("India", "Asia/Kolkata");
        countryToTimeZoneMap.put("China", "Asia/Shanghai");
        countryToTimeZoneMap.put("Brazil", "America/Sao_Paulo");
        countryToTimeZoneMap.put("South Africa", "Africa/Johannesburg");
        countryToTimeZoneMap.put("France", "Europe/Paris");
        countryToTimeZoneMap.put("Germany", "Europe/Berlin");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TimeDisplayByCountryGUI::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Time Converter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new GridLayout(6, 2, 20, 20));

        Font font = new Font("Arial", Font.PLAIN, 16);
        JLabel inputCountryLabel = new JLabel("Select your country:");
        inputCountryLabel.setFont(font);
        JComboBox<String> inputCountryComboBox = new JComboBox<>(countryToTimeZoneMap.keySet().toArray(new String[0]));
        inputCountryComboBox.setFont(font);

        JLabel inputTimeLabel = new JLabel("Enter the time (HH:mm):");
        inputTimeLabel.setFont(font);
        JTextField inputTimeField = new JTextField();
        inputTimeField.setFont(font);

        JLabel outputCountryLabel = new JLabel("Select the target country:");
        outputCountryLabel.setFont(font);
        JComboBox<String> outputCountryComboBox = new JComboBox<>(countryToTimeZoneMap.keySet().toArray(new String[0]));
        outputCountryComboBox.setFont(font);

        JButton convertButton = new JButton("Convert Time");
        convertButton.setFont(font);

        frame.add(inputCountryLabel);
        frame.add(inputCountryComboBox);
        frame.add(inputTimeLabel);
        frame.add(inputTimeField);
        frame.add(outputCountryLabel);
        frame.add(outputCountryComboBox);
        frame.add(new JLabel());
        frame.add(convertButton);

        convertButton.addActionListener(new ActionListener() {
                      
            public void actionPerformed(ActionEvent e) {
                try {
                    String inputCountry = (String) inputCountryComboBox.getSelectedItem();
                    String outputCountry = (String) outputCountryComboBox.getSelectedItem();

                    String inputTimeZone = countryToTimeZoneMap.get(inputCountry);
                    String outputTimeZone = countryToTimeZoneMap.get(outputCountry);

                    LocalTime localTime = LocalTime.parse(inputTimeField.getText());
                    ZoneId inputZoneId = ZoneId.of(inputTimeZone);
                    ZonedDateTime inputZonedDateTime = ZonedDateTime.now(inputZoneId).with(localTime);

                    ZoneId outputZoneId = ZoneId.of(outputTimeZone);
                    ZonedDateTime outputZonedDateTime = inputZonedDateTime.withZoneSameInstant(outputZoneId);

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm z");
                    String outputTimeString = outputZonedDateTime.format(formatter);

                    JOptionPane.showMessageDialog(frame, "Time in " + outputCountry + " (" + outputTimeZone + "): " + outputTimeString);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid input. Please check the time format or selected countries.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.setVisible(true);
    }
}
