package com.macrobios.mangalist.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.squareup.moshi.Json;

import java.util.Objects;

@Entity(tableName = "anime_list")
public class Anime {

    @PrimaryKey
    @NonNull
    private int id;
    private String title;
    private String imageURL;
    private String type;
    private String episodes;
    private float score;

    public Anime(int id, String title, String imageURL, String type, String episodes, float score) {
        this.id = id;
        this.title = title;
        this.imageURL = imageURL;
        this.type = type;
        this.episodes = episodes;
        this.score = score;
    }

    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getType() {
        return type;
    }

    public String getEpisodes() {
        return episodes;
    }

    public float getScore() {
        return score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Anime anime = (Anime) o;
        return id == anime.id &&
                Float.compare(anime.score, score) == 0 &&
                title.equals(anime.title) &&
                imageURL.equals(anime.imageURL) &&
                type.equals(anime.type) &&
                episodes.equals(anime.episodes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, imageURL, type, episodes, score);
    }
}
