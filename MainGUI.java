import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class MainGUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Main GUI");
                JButton button1 = new JButton("Secure Deletion");
                button1.setFocusPainted(false);
                JButton button2 = new JButton("Metadata Extraction");
                button2.setFocusPainted(false);
                JLabel label = new JLabel("Tool for Secure Deletion and Metadata Extraction...");
                label.setFont(new Font("", Font.BOLD, 15));
                // Set the font and font size
                Font buttonFont = new Font("", Font.BOLD, 14);
                button1.setFont(buttonFont);
                button2.setFont(buttonFont);

                button1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        new Project1();  // This will open Project1
                    }
                });

                button2.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        new Project2();  // This will open ExifToolGUI
                    }
                });

                // Set the size to match Project1
                frame.setSize(520, 480);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setResizable(false);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                try {
                    Image image = ImageIO.read(new File("bac.png"));
                    ImagePanel imagePanel = new ImagePanel(image);
                    frame.setContentPane(imagePanel);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                frame.setLayout(new GridBagLayout());

                // Set layout manager and constraints
                GridBagConstraints constraints = new GridBagConstraints();
                constraints.insets = new Insets(10, 10, 30, 10); // Add some padding
                constraints.gridx = 0;
                constraints.gridy = 0;
                frame.getContentPane().add(label, constraints);
                // Add button1 to the JFrame with custom constraints
                constraints.insets = new Insets(10, 10, 20, 10);
                constraints.gridx = 0;
                constraints.gridy = 1;
                frame.getContentPane().add(button1, constraints);

                // Add button2 to the JFrame with custom constraints
                constraints.gridx = 0;
                constraints.gridy = 2;
                frame.getContentPane().add(button2, constraints);
            
            }
        });
    }
}
class ImagePanel extends JComponent {
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