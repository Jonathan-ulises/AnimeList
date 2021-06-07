package com.macrobios.mangalist.api;

import com.macrobios.mangalist.Commons;
import com.macrobios.mangalist.model.Anime;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.http.GET;

public class AnimeClient {

    private static final AnimeClient ourInstace = new AnimeClient();
    private AnimeService service;

    public static AnimeClient getInstance(){
        return ourInstace;
    }

    public interface AnimeService {
        @GET("anime/1/airing")
        Call<AnimeJSONResponse> getAnimeService();
    }

    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Commons.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build();

    public AnimeService getService(){
        if(service == null){
            service = retrofit.create(AnimeService.class);
        }
        return service;
    }
}
