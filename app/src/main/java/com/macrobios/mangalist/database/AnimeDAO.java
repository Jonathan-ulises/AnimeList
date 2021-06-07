package com.macrobios.mangalist.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.macrobios.mangalist.model.Anime;

import java.util.List;

@Dao
public interface AnimeDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Anime> animeList);

    @Query("SELECT * FROM anime_list")
    LiveData<List<Anime>> getAnime();
}
