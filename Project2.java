import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Project2 extends JFrame {
    private JTextPane textPane;
    private JButton extractButton;
    private JButton chooseFileButton;

    private File selectedFile;

    public Project2() {
        super("ExifTool Metadata Extractor");
        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(670, 590);

        textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setMargin(new Insets(12, 12, 0, 0));
        textPane.setCaretColor(textPane.getBackground());
        textPane.setFont(new Font("Courier New", Font.BOLD, 16));
        textPane.setText("\n Choose Image to extract metadata...");
        extractButton = new JButton("Extract Metadata");
        extractButton.setFont(new Font(extractButton.getFont().getName(),extractButton.getFont().getStyle(),13));
        extractButton.setFocusPainted(false);
        extractButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                extractMetadata();
            }
        });

        chooseFileButton = new JButton("Choose New Image");
        chooseFileButton.setFont(new Font(chooseFileButton.getFont().getName(),chooseFileButton.getFont().getStyle(),13));
        chooseFileButton.setFocusPainted(false);
        chooseFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseFile();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(chooseFileButton);
        buttonPanel.add(extractButton);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JScrollPane(textPane), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        add(panel);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            String message = "\n Selected File: " + selectedFile.getName() + "\n\n Click 'Extract Metadata' to view metadata...";
            textPane.setText(message);
        }
    }

    private void extractMetadata() {
        if (selectedFile == null) {
            JOptionPane.showMessageDialog(this, "Please choose an image file first.");
            return;
        }

        try {
            String exifToolPath = "\".\\exiftool.exe\"";
            ProcessBuilder processBuilder = new ProcessBuilder(exifToolPath, selectedFile.getAbsolutePath());

            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            StringBuilder output = new StringBuilder();
            int maxKeyLength = 0;

            while ((line = reader.readLine()) != null) {
                int colonIndex = line.indexOf(':');
                if (colonIndex != -1) {
                    maxKeyLength = Math.max(maxKeyLength, colonIndex);
                }
            }

            reader.close();
            process.waitFor();
            process.destroy();

            processBuilder = new ProcessBuilder(exifToolPath, selectedFile.getAbsolutePath());
            processBuilder.redirectErrorStream(true);
            process = processBuilder.start();
            inputStream = process.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            while ((line = reader.readLine()) != null) {
                int colonIndex = line.indexOf(':');
                if (colonIndex != -1) {
                    String key = line.substring(0, colonIndex);
                    String value = line.substring(colonIndex + 1);
                    output.append(String.format("%-" + maxKeyLength + "s: %s\n", key, value));
                } else {
                    output.append(line).append("\n");
                }
            }

            textPane.setText(output.toString());

            process.waitFor();
            process.destroy();
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Project2();
    }

}