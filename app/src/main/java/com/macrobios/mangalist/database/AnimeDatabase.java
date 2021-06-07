package com.macrobios.mangalist.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.macrobios.mangalist.model.Anime;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Anime.class}, version = 1)
public abstract class AnimeDatabase extends RoomDatabase {

    public abstract AnimeDAO animeDAO();

    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriterExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static volatile AnimeDatabase INSTANCE;

    public static AnimeDatabase getDatabase(final Context ctx){
        if(INSTANCE == null){
            synchronized (AnimeDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(ctx.getApplicationContext(),
                            AnimeDatabase.class,
                            "anime_list_db")
                            .build();
                }
            }
        }

        return INSTANCE;
    }
}
