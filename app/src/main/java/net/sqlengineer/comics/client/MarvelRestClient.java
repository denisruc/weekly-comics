package net.sqlengineer.comics.client;

import net.sqlengineer.comics.IMarvelWebService;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by druckebusch on 5/30/17.
 */

public class MarvelRestClient {

    private static IMarvelWebService instance = null;
    // TODO Marvel URL
    private static final String BASE_URL = "https://gateway.marvel.com:443";

    public static IMarvelWebService getClient() {

        if (null == instance) {

            AuthenticationInterceptor interceptor = new AuthenticationInterceptor();
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            Retrofit retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            instance = retrofit.create(IMarvelWebService.class);
        }

        return instance;
    }

}
