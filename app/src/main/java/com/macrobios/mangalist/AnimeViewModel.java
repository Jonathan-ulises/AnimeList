package com.macrobios.mangalist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.macrobios.mangalist.api.RequestStatus;
import com.macrobios.mangalist.api.StatusDescription;
import com.macrobios.mangalist.database.AnimeDatabase;
import com.macrobios.mangalist.model.Anime;

import java.util.List;

public class AnimeViewModel extends AndroidViewModel {

    private AnimeRepository repository;

    public AnimeViewModel(@NonNull Application application) {
        super(application);
        AnimeDatabase database = AnimeDatabase.getDatabase(application);
        this.repository = new AnimeRepository(database, application);
    }

    private MutableLiveData<StatusDescription>
            statusDescriptionMutableLiveData = new MutableLiveData<>();

    /**
     * Optiene el status de la descar de los datos del servidor
     * @return LiveData de StatusDescription
     */
    public LiveData<StatusDescription> getStatusDescriptionMutableLiveData() {
        return statusDescriptionMutableLiveData;
    }

    /**
     * Envia la lista de animes de la base de datos local
     * @return LiveData List de Anime
     */
    public LiveData<List<Anime>> getAnimeListRepository() {
        return repository.getAnList();
    }

    public void downloadAnimes() {
        statusDescriptionMutableLiveData
                .setValue(new StatusDescription(RequestStatus.LOADING, ""));

        repository.downloadAndSaveAnime(new AnimeRepository.DownloadStatusListener() {
            @Override
            public void downloadSuccess() {
                statusDescriptionMutableLiveData
                        .setValue(new StatusDescription(RequestStatus.DONE, ""));
            }

            @Override
            public void downloadError(String message) {
                statusDescriptionMutableLiveData
                        .setValue(new StatusDescription(RequestStatus.ERROR, message));
            }
        });
    }
}
