package com.jkurajpuriya.newsapps.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.jkurajpuriya.newsapps.databinding.ActivityNewsBinding;
import com.squareup.picasso.Picasso;

public class NewsActivity extends AppCompatActivity {
    ActivityNewsBinding binding;
    String title, desc, content, imageUrl, url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityNewsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        title=getIntent().getStringExtra("title");
        desc=getIntent().getStringExtra("desc");
        content=getIntent().getStringExtra("content");
        imageUrl=getIntent().getStringExtra("image");
        url=getIntent().getStringExtra("url");

        Picasso.get().load(imageUrl).into(binding.image);
        binding.title.setText(title);
        binding.subDescription.setText(desc);
        binding.content.setText(content);

        binding.readFullNews.setOnClickListener(v -> {
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setData(Uri.parse(url));
//            startActivity(intent);
            Intent intent = new Intent(NewsActivity.this,NewsDetailsActivity.class);
            intent.putExtra("wb",url);
            startActivity(intent);
        });

    }
}