
import javax.swing.BorderFactory;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui implements ActionListener {
    private JTextField field1 = new JTextField(" control v ");
    private JTextField field2 = new JTextField();
    // private JTextField field3 = new JTextField();

    private JLabel l1 = new JLabel(" url ");
    private JLabel l2 = new JLabel(" words you need ");
    // private JLabel l3 = new JLabel(" language ");
    private JLabel l4 = new JLabel("   ");

    private JFrame frame2;
    private JPanel panel2;
    private JFrame frame;
    private JPanel panel;

    public Gui() {

        frame = new JFrame();
        panel = new JPanel();

        JButton button = new JButton("Search");

        button.addActionListener(this);

        panel.setBorder(BorderFactory.createEmptyBorder(200, 200, 200, 200));
        panel.setLayout(new GridLayout(4, 4));

        panel.add(l1);
        panel.add(field1);
        panel.add(l2);
        panel.add(field2);
        // panel.add(l3);
        // panel.add(field3);
        panel.add(l4);
        panel.add(button);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Our GUI");
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Gui();
        String html = "https://en.wikipedia.org/wiki/Cat";
        try {
            Document doc = Jsoup.connect(html).get();
            String title = doc.title();
            System.out.println(title);

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame2();
    }

    private JFrame frame2() {
        frame2 = new JFrame();
        panel2 = new JPanel();
        panel2.setBorder(BorderFactory.createEmptyBorder(200, 200, 200, 200));

        frame2.add(panel2, BorderLayout.CENTER);
        frame2.pack();
        frame2.setVisible(true);
        return frame2;
    }

}
