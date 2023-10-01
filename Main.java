import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.html.*;

public class Main extends JFrame implements ActionListener, HyperlinkListener {
    private JTextField addressBar;
    private JLabel label;
    private JEditorPane pane;
    
    Main() {
        super("Simple Search Engine");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        label = new JLabel();
        label.setText("Type a URL:");

        addressBar = new JTextField();
        addressBar.setPreferredSize((new Dimension(500, 40)));
        addressBar.addActionListener(this);


        
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(this);

        // Customize font and color for the label and address bar
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(Color.black);
        addressBar.setFont(new Font("Arial", Font.PLAIN, 14));
        addressBar.setForeground(Color.BLACK);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        topPanel.add(label);
        topPanel.add(addressBar);
        topPanel.add(searchButton);

        pane = new JEditorPane();
        pane.setEditable(false);
        pane.addHyperlinkListener(this);
        Color teal = new Color(173, 216, 230);

        // Customize font and color for the JEditorPane
        pane.setFont(new Font("Arial", Font.PLAIN, 14));
        pane.setForeground(Color.BLACK);
        pane.setBackground(teal);

        pane.setPreferredSize(new Dimension(600, 400));
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(pane), BorderLayout.CENTER);

        add(mainPanel);
        setSize(new Dimension(800, 600));
    }

    public void actionPerformed(ActionEvent evt) {
        String url = addressBar.getText();

        try {
            pane.setPage(url);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void hyperlinkUpdate(HyperlinkEvent evt) {
        if (evt.getEventType() != HyperlinkEvent.EventType.ACTIVATED) {
            return;
        }

        if (evt instanceof HTMLFrameHyperlinkEvent) {
            HTMLDocument doc = (HTMLDocument) pane.getDocument();
            doc.processHTMLFrameHyperlinkEvent((HTMLFrameHyperlinkEvent) evt);
        } else {
            String url = evt.getURL().toString();
            addressBar.setText(url);

            try {
                pane.setPage(url);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> {
            Main main = new Main();
            main.setVisible(true);
        });
    }
}
