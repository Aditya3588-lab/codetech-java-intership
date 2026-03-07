import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;

public class WeatherGUI {

    public static void main(String[] args) {

        try {

            Scanner sc = new Scanner(System.in);

            System.out.print("Enter city name: ");
            String city = sc.nextLine();

            String urlString = "https://wttr.in/" + city + "?format=3";

            URL url = new URL(urlString);

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(url.openStream()));

            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println("Weather: " + line);
            }

            reader.close();

        } catch (Exception e) {

            System.out.println("Error fetching weather.");
            e.printStackTrace();

        }

    }
}