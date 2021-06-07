package com.macrobios.mangalist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.macrobios.mangalist.api.RequestStatus;
import com.macrobios.mangalist.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private  ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AnimeViewModel animeViewModel = new ViewModelProvider(this,
                                                    new AnimeViewModelFactory(getApplication()))
                                                    .get(AnimeViewModel.class);


        binding.rclAnime.setLayoutManager(new LinearLayoutManager(this));
        AnimeAdapter adapter = new AnimeAdapter();

        adapter.setOnItemClickListener(anime -> {
            Toast.makeText(this, "dato: " + anime.getTitle(), Toast.LENGTH_SHORT).show();
        });

        binding.rclAnime.setAdapter(adapter);

        animeViewModel.getAnimeListRepository().observe(this, aniList -> {
            adapter.submitList(aniList);

            if(aniList.isEmpty()){
                binding.txtEmptyList.setVisibility(View.VISIBLE);
            } else {
                binding.txtEmptyList.setVisibility(View.GONE);
            }
        });

        animeViewModel.getStatusDescriptionMutableLiveData().observe(this, statusDescription -> {
            if(statusDescription.getStatus() == RequestStatus.LOADING){
                binding.prgbLoading.setVisibility(View.VISIBLE);
            } else {
                binding.prgbLoading.setVisibility(View.GONE);
            }

            if(statusDescription.getStatus() == RequestStatus.ERROR){
                Toast.makeText(this, statusDescription.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        animeViewModel.downloadAnimes();
    }
}