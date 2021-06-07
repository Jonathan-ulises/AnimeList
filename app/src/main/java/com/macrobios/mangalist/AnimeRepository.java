package com.macrobios.mangalist;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.macrobios.mangalist.api.AnimeClient;
import com.macrobios.mangalist.api.AnimeJSONResponse;
import com.macrobios.mangalist.api.Top;
import com.macrobios.mangalist.database.AnimeDatabase;
import com.macrobios.mangalist.model.Anime;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimeRepository {

    private AnimeDatabase database;

    private Application application;

    public interface DownloadStatusListener {
        void downloadSuccess();
        void downloadError(String message);
    }

    public AnimeRepository(AnimeDatabase database, Application application) {
        this.database = database;
        this.application = application;

    }

    /**
     * Optiene LiveData List de anime de la base de datos local
     * @return LiveData List de Anime
     */
    public LiveData<List<Anime>> getAnList(){
        return database.animeDAO().getAnime();
    }

    public void downloadAndSaveAnime(DownloadStatusListener downloadStatusListener) {
        AnimeClient.AnimeService animeService = AnimeClient.getInstance().getService();
        animeService.getAnimeService().enqueue(new Callback<AnimeJSONResponse>() {
            @Override
            public void onResponse(Call<AnimeJSONResponse> call, Response<AnimeJSONResponse> response) {
                if(response.code() != 200){
                    downloadStatusListener.downloadError(application.getString(R.string.error_response_code) + response.code());
                } else {
                    List<Anime> animeList = getAnimeListWithMoshi(response.body());


                    downloadStatusListener.downloadSuccess();
                    AnimeDatabase.databaseWriterExecutor.execute(() -> {
                        database.animeDAO().insertAll(animeList);
                    });
                }
            }

            @Override
            public void onFailure(Call<AnimeJSONResponse> call, Throwable t) {
                downloadStatusListener.downloadError(application.getString(R.string.error_internet_connection) + " " + t.getMessage());
            }
        });
    }

    /**
     * Manipula la respuesta para obtener una lista de opjetos
     * @param body Respuesta del servicio
     * @return List de Anime
     */
    private List<Anime> getAnimeListWithMoshi(AnimeJSONResponse body){
        ArrayList<Anime> anL = new ArrayList<>();

        List<Top> tops = body.getTop();
        for (Top tp: tops) {
            int id = tp.getMal_id();
            String title = tp.getTitle();
            String imageURL = tp.getImage_url();
            String episodes = "0";
            if(tp.getEpisodes() == null){
                episodes = "0";
            } else {
                episodes = tp.getEpisodes();
            }
            float score = tp.getScore();
            String type = tp.getType();

            Anime an = new Anime(id, title, imageURL, type, episodes, score);

            anL.add(an);
        }
        return anL;
    }
}
