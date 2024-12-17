import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ProductScraperGUI {

    public static void main(String[] args) {
        // Create a simple GUI window
        JFrame frame = new JFrame("E-commerce Product Scraper");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // URL input field
        JLabel urlLabel = new JLabel("Enter URL:");
        urlLabel.setBounds(20, 20, 80, 25);
        frame.add(urlLabel);

        JTextField urlField = new JTextField();
        urlField.setBounds(100, 20, 250, 25);
        frame.add(urlField);

        // Start button
        JButton scrapeButton = new JButton("Scrape & Save");
        scrapeButton.setBounds(120, 70, 150, 30);
        frame.add(scrapeButton);

        // Confirmation Label
        JLabel confirmationLabel = new JLabel("");
        confirmationLabel.setBounds(50, 120, 300, 25);
        frame.add(confirmationLabel);

        // Action Listener for button click
        scrapeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = urlField.getText();
                if (url.isEmpty()) {
                    confirmationLabel.setText("Please enter a valid URL.");
                } else {
                    try {
                        confirmationLabel.setText("Scraping started...");
                        scrapeProducts(url);
                        confirmationLabel.setText("Data saved to products.csv!");
                    } catch (Exception ex) {
                        confirmationLabel.setText("Error: " + ex.getMessage());
                    }
                }
            }
        });

        // Display the frame
        frame.setVisible(true);
    }

    // Function to scrape product information and save to CSV
    public static void scrapeProducts(String url) throws IOException {
        // Connect to the website and parse the document
        Document document = Jsoup.connect(url).get();

        // Extract product data - modify these selectors as per the website's HTML
        Elements productNames = document.select(".product-name"); // Replace with actual selector
        Elements productPrices = document.select(".product-price"); // Replace with actual selector
        Elements productRatings = document.select(".product-rating"); // Replace with actual selector

        // Create a CSV file to store the extracted data
        PrintWriter writer = new PrintWriter(new FileWriter("products.csv"));
        writer.println("Product Name,Price,Rating");

        // Iterate through and write data to the CSV file
        int numProducts = Math.min(productNames.size(), Math.min(productPrices.size(), productRatings.size()));
        for (int i = 0; i < numProducts; i++) {
            String name = productNames.get(i).text();
            String price = productPrices.get(i).text();
            String rating = productRatings.get(i).text();
            writer.println(name + "," + price + "," + rating);
        }

        // Close the writer
        writer.close();
    }
}
