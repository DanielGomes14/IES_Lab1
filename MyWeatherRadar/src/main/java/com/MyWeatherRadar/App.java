package com.MyWeatherRadar;

import com.MyWeatherRadar.ipma_client.City;
import com.MyWeatherRadar.ipma_client.Cities;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.MyWeatherRadar.ipma_client.IpmaCityForecast;
import com.MyWeatherRadar.ipma_client.IpmaService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;

/**
 * demonstrates the use of the IPMA API for weather forecast
 */
public class App {

    /*
    loggers provide a better alternative to System.out.println
    https://rules.sonarsource.com/java/tag/bad-practice/RSPEC-106
     */
    private static final Logger LOGGER = LogManager.getLogger();


    public static void main(String[] args) {
            String city = "Aveiro";


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.ipma.pt/open-data/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            IpmaService service = retrofit.create(IpmaService.class);
            //default city name is "Aveiro", can be changed using arguments
            if (args.length > 0) city = args[0];
            Call<Cities> callSyncCodes = service.getCodes();
            Integer cityCode = null;
            try {
                //Get Api Response and Iterate through all City Objects until we get the code for the city  we want(or the default one)
                Response<Cities> apiResponse = callSyncCodes.execute();
                Cities cities = apiResponse.body();
                Iterator<City> iter = cities.getData().listIterator();
                while (iter.hasNext()) {
                    City cityObj = iter.next();
                    if (cityObj.getLocal().equals(city))
                        cityCode = cityObj.getGlobalIdLocal();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            //In case the city we asked for does not exist
            if (cityCode == null) {
                LOGGER.info("Specified city does not exist!");
                System.exit(1);
            }
            //Same call used on Lab1.1
            Call<IpmaCityForecast> callSync = service.getForecastForACity(cityCode);
            try {
                Response<IpmaCityForecast> apiResponse = callSync.execute();
                IpmaCityForecast forecast = apiResponse.body();
                LOGGER.info("Max temp for today: " + forecast.getData().listIterator().next().getTMax() + "Cº" + ", " + city);
                //System.out.println( "Max temp for today: " + forecast.getData().listIterator().next().getTMax());
            } catch (Exception ex) {
                LOGGER.error("No results!");
            }
            System.exit(1);
        }

}