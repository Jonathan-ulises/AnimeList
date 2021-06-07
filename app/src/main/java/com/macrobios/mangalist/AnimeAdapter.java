package com.macrobios.mangalist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.macrobios.mangalist.databinding.MangaItemListBinding;
import com.macrobios.mangalist.model.Anime;
import com.squareup.picasso.Picasso;

public class AnimeAdapter extends ListAdapter<Anime, AnimeAdapter.AnimeAdapterViewHolder> {

    protected AnimeAdapter(){
        super(DIFF_CALLBACK);
    }

    public static final DiffUtil.ItemCallback<Anime> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Anime>() {
                //Compara si los items de la lista son iguales
                @Override
                public boolean areItemsTheSame(
                        @NonNull Anime oldAnime, @NonNull  Anime newAnime) {
                    return oldAnime.getId() == newAnime.getId();
                }

                //Compara si el contenido de un item es el mismo que otro
                @Override
                public boolean areContentsTheSame(
                        @NonNull Anime oldAnime, @NonNull Anime newAnime) {
                    return oldAnime.equals(newAnime);
                }
            };


    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onAnimeItemClick(Anime anime);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public AnimeAdapter.AnimeAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MangaItemListBinding binding = MangaItemListBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new AnimeAdapterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeAdapter.AnimeAdapterViewHolder holder, int position) {
        Anime anime = getItem(position);
        holder.bind(anime);
    }

    class AnimeAdapterViewHolder extends RecyclerView.ViewHolder {

        private MangaItemListBinding binding;

        public AnimeAdapterViewHolder(@NonNull MangaItemListBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

        public void bind (Anime anime){
            binding.txtAnimeName.setText(anime.getTitle());
            binding.txtAnimeEps.setText(anime.getEpisodes());
            binding.txtTypeA.setText(anime.getType());
            binding.txtScoreA.setText(String.valueOf(anime.getScore()));
            Picasso.get().load(anime.getImageURL()).into(binding.imgCover);

            binding.getRoot().setOnClickListener(v -> {
                onItemClickListener.onAnimeItemClick(anime);
            });

            binding.executePendingBindings();
        }
    }
}
