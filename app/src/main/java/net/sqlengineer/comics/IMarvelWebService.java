package net.sqlengineer.comics;

import net.sqlengineer.comics.data.ComicDataWrapper;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by druckebusch on 5/21/17.
 */

public interface IMarvelWebService {


    @GET("/v1/public/comics")
    Call<ComicDataWrapper> getComics(@Query("dateDescriptor") String dateDescriptior,
                                     @Query("offset") int offset);

}
