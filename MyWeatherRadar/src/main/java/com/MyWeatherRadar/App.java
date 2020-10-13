package com.MyWeatherRadar;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.MyWeatherRadar.ipma_client.IpmaCityForecast;
import com.MyWeatherRadar.ipma_client.IpmaService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * demonstrates the use of the IPMA API for weather forecast
 */
public class App {

    /*
    loggers provide a better alternative to System.out.println
    https://rules.sonarsource.com/java/tag/bad-practice/RSPEC-106
     */
    private static final Logger LOGGER = LogManager.getLogger();

    public static void  main(String[] args ) {

        /*
        get a retrofit instance, loaded with the GSon lib to convert JSON into objects
         */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.ipma.pt/open-data/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IpmaService service = retrofit.create(IpmaService.class);
        Integer city = null;
        if(args.length>0)city=Integer.parseInt(args[0]);
        if(city ==  null) {
            LOGGER.info("City ID was not specified.");
            System.exit(1);
        }
        Call<IpmaCityForecast> callSync = service.getForecastForACity(city);

        try {
            Response<IpmaCityForecast> apiResponse = callSync.execute();
            IpmaCityForecast forecast = apiResponse.body();

            if (forecast != null) {
                LOGGER.info("Max temp for today: " + forecast.getData().listIterator().next().getTMax() + "Cº, " + forecast.getCountry());
            } else {
                LOGGER.error("No results!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}