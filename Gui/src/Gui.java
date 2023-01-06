
import javax.swing.BorderFactory;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JPanel;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Gui implements ActionListener {
    private static String s = "";
    private static String url;
    private static String w;
    private static String de;
    private static ArrayList<String> list = new ArrayList<>();

    private static JTextField field1 = new JTextField();
    private static JTextField field2 = new JTextField();
    private static JTextField field3 = new JTextField();

    private static JLabel l1 = new JLabel(" url ");
    private static JLabel l2 = new JLabel(" words you need ");
    private static JLabel l3 = new JLabel(" Depth ");
    private static JLabel l4 = new JLabel("   ");

    private JFrame frame2;
    // private JPanel panel2;
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
        panel.add(l3);
        panel.add(field3);
        panel.add(l4);
        panel.add(button);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Our GUI");
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        new Gui();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // String url = "https://www.netflix.com/ca/";-> testlink
        // int de = 3; -> test
        url = field1.getText();
        w = field2.getText();
        de = field3.getText();

        int d = Integer.parseInt(de);

        System.out.println(url);
        System.out.println(de);
        crawl(d, url, w, new ArrayList<String>());

        frame2();
        // System.out.println(list); ->test

    }

    private JFrame frame2() {

        frame2 = new JFrame("result");

        JTextArea area = new JTextArea(10, 100);
        JScrollPane pane = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        if (s.length() == 0) {
            s = "no result found";
        }
        area.setText(s);
        area.setFont(new Font("Arial", Font.PLAIN, 14));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        frame2.add(pane);
        // frame2.add(area);
        frame2.setSize(500, 500);
        frame2.setVisible(true);
        // frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setLocationRelativeTo(null);

        // panel2.setBorder(BorderFactory.createEmptyBorder(200, 200, 200, 200));

        // for (int i = 0; i < list.size(); i++) {
        // JLabel pp = new JLabel(list.get(i), SwingConstants.CENTER);

        // panel2.add(pp);
        // System.out.println(list.get(i));
        // }

        return frame2;
    }

    public static void crawl(int depth, String url, String word, ArrayList<String> visited) {
        System.out.println(depth);
        if (depth > 0) {

            Document doc = (request(url, visited, word));

            if (doc != null) {

                for (Element link : doc.select("a[href]")) {
                    String nextlink = link.absUrl("href");
                    if (visited.contains(nextlink) == false) {

                        crawl(depth - 1, nextlink, word, visited);
                    }
                }
            }
        } else {
            return;
        }

    }

    public static Document request(String url, ArrayList<String> v, String wordmatch) {
        try {
            Connection con = Jsoup.connect(url);
            Document doc = con.get();
            if (con.response().statusCode() == 200) {
                Elements para = doc.getElementsByTag("p");
                Elements li = doc.getElementsByTag("li");
                for (Element e : para) {
                    if (e.text().contains(wordmatch) && !list.contains(e.text())) {
                        // System.out.println(e);
                        list.add(e.text());
                        s = e.text() + "\n" + "\n" + s;
                    }
                }
                for (Element l : li) {
                    if (l.text().contains(wordmatch) && !list.contains(l.text())) {
                        list.add(l.text());
                        s = s + "\n" + "\n" + l.text();
                    }
                }

                v.add(url);
                return doc;
            } else {
                return null;
            }

        } catch (Exception e) {
            return null;
        }
    }

}
