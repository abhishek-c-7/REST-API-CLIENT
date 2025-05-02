import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

/*
  *Internship Task 2: Rest API Client
  * Student Name: Abhishek Choudhary
  * Description:
  * This program connects to the OpenWeatherMap API(public Rest API) to fetch and display
    real-time weather details(temperature and description of specified city)
  *It uses HttpURLConnection (built in java library)for API communication and org.json library for parsing JSON.
  *It displays data in structured format
 */

class RestApiClient {
    public static void main(String[] args) {
        String apiKey = "e8134ec9d0a05bf37abfa663c43d6fd4";/*I used actual OpenWeatherMap API key
                                                            which i taken from its website*/
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter city name:");//it takes city as input from user
        String city=sc.nextLine();

        //API endpoint with city name,API key,and metric units for Celcius
        String urlString = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric";

        try {
            //Establishing connection to API
            URL url = new URL(urlString);//establishing connection to API
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            //checks if response is successful(HTTP 200 OK)
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                //read API response line by line
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Parse JSON
                //i added org.json library as classpath in this project

                JSONObject json = new JSONObject(response.toString());//it converts json string into json object

                //extract required data from JSON response
                String weather = json.getJSONArray("weather").getJSONObject(0).getString("main");
                String description = json.getJSONArray("weather").getJSONObject(0).getString("description");
                double temp = json.getJSONObject("main").getDouble("temp");
                double feelsLike = json.getJSONObject("main").getDouble("feels_like");

                // Display output
                System.out.println("City: " + city);
                System.out.println("Weather: " + weather + " (" + description + ")");
                System.out.println("Temperature: " + temp + "°C");
                System.out.println("Feels Like: " + feelsLike + "°C");
            } else {//in case of API errror
                System.out.println("API Request Failed. HTTP code: " + responseCode);
            }
        } catch
        (java.net.UnknownHostException e) {
            System.out.println("No Internet Connection.Please check your internet and try again.");
        }catch(Exception e) {
            System.out.println("An error occured :" + e.getMessage());
            e.printStackTrace();
        }
        }
    }
/*How to run:
* click on run
* it will compile and run the program
* you will see weather data
*/
