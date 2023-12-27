import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;

public class Project1 extends JFrame implements ActionListener {
    private JButton button1, button2, button3;
    private JLabel label1,label2;
    private File selectedFile;

    public Project1() {
        super("Secure Delete");
        // setDefaultCloseOperation(EXIT_ON_CLOSE);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(470, 400);

        // Set the content pane to a custom panel with a background image
        try {
            Image image = ImageIO.read(new File("bac.png"));
            ImagePanel imagePanel = new ImagePanel(image);
            setContentPane(imagePanel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(0, 0, 15, 0);
        label1 = new JLabel("Select a file to securely delete...");
        label1.setFont(new Font("", Font.BOLD, 15));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.CENTER;
        add(label1, constraints);

        button1 = new JButton("Choose File");
        button1.addActionListener(this);
        button1.setFont(new Font("", Font.BOLD, 13));
        constraints.gridy = 1;
        constraints.insets = new Insets(18, 0, 0, 0);
        button1.setFocusPainted(false);
        add(button1, constraints);

        label2 = new JLabel();
        label2.setFont(new Font("", Font.BOLD, 13));
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.anchor = GridBagConstraints.CENTER;
        add(label2, constraints);

        button2 = new JButton("Delete (Gutmann)");
        button2.addActionListener(this);
        button2.setFont(new Font("", Font.BOLD, 13));
        constraints.gridy = 3;
        constraints.insets = new Insets(20, 0, 0, 0);
        button2.setFocusPainted(false);
        add(button2, constraints);

        button3 = new JButton("Delete (DoD)");
        button3.addActionListener(this);
        button3.setFont(new Font("", Font.BOLD, 13));
        constraints.gridy = 2;
        constraints.insets = new Insets(20, 0, 0, 0);
        button3.setFocusPainted(false);
        add(button3, constraints);

      setResizable(false);
      setLocationRelativeTo(null);
      setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == button1) {
          JFileChooser fileChooser = new JFileChooser();
          int result = fileChooser.showOpenDialog(this);
          if (result == JFileChooser.APPROVE_OPTION) {
              selectedFile = fileChooser.getSelectedFile();
              label2.setText("File selected: " + selectedFile.getName());
              label1.setText("Choose a method to securely delete the file:");
          }
      } else if (e.getSource() == button2 || e.getSource() == button3) {
          if (selectedFile != null) {
              if (e.getSource() == button2) {
                  wipeFileGutmann(selectedFile);
              } else {
                  wipeFileDoD(selectedFile);
              }
              label2.setText("File securely deleted!");
              label1.setText("Select another file to securely delete:");
          } else {
              label2.setText("No file selected.");
          }
      }
    }

    public static void wipeFileGutmann(File file) {
      try {
          if (file.exists()) {
              SecureRandom random = new SecureRandom();
FileOutputStream fos = new FileOutputStream(file);
              long length = file.length();
              byte[] data = new byte[(int) length];
              int[] gutmann = new int[]{
                      0x55, 0x55, 0x55, 0x55, 0x55, 0x55, 0x55, 0x55,
                      0xAA, 0xAA, 0xAA, 0xAA, 0xAA, 0xAA, 0xAA, 0xAA,
                      0x92, 0x49, 0x24, 0x92, 0x49, 0x24, 0x92, 0x49,
                      // ...
                      // The full Gutmann method includes a total of
                      // thirty-five passes with different patterns.
                      // ...
              };
              for (int i = 1; i <=4; i++) {
                  for (int j = 0; j < data.length; j++) {
                      byte[] rand = new byte[1];
                      random.nextBytes(rand);
                      data[j] = rand[0];
                  }
                  fos.write(data);
                  fos.flush();
              }
              for (int pass : gutmann) {
                  for (int i = 0; i < data.length; i++) {
                      data[i] = (byte) pass;
                  }
                  fos.write(data);
                  fos.flush();
              }
              for (int i = 1; i <=4; i++) {
                  for (int j = 0; j < data.length; j++) {
                      byte[] rand = new byte[1];
                      random.nextBytes(rand);
                      data[j] = rand[0];
                  }
                  fos.write(data);
                  fos.flush();
              }
              fos.close();
              file.delete();
          }
      } catch (IOException e) {
          e.printStackTrace();
      }
    }

    public static void wipeFileDoD(File file) {
      try {
          if (file.exists()) {
              SecureRandom random = new SecureRandom();
              FileOutputStream fos = new FileOutputStream(file);
              byte[] zeros = new byte[1];
              byte[] ones = new byte[1];
              ones[0] = (byte) 0xFF;
              long length = file.length();
              for (int i = 0; i < length; i++) {
                  fos.write(zeros);
              }
              fos.flush();
              for (int i = 0; i < length; i++) {
                  fos.write(ones);
              }
              fos.flush();
              for (int i = 0; i < length; i++) {
                  byte[] rand = new byte[1];
                  random.nextBytes(rand);
                  fos.write(rand);
              }
              fos.flush();
              fos.close();
              file.delete();
          }
      } catch (IOException e) {
          e.printStackTrace();
      }
    }

    public static void main(String[] args) {
        new Project1();
    }

    // Custom panel that draws a background image
    class ImagePanel extends JPanel {
        private Image image;

        public ImagePanel(Image image) {
            this.image = image;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this);
        }
    }
}
