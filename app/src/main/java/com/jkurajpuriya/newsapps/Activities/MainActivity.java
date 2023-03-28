package com.jkurajpuriya.newsapps.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Toast;

import com.jkurajpuriya.newsapps.Adapters.CategoryAdapter;
import com.jkurajpuriya.newsapps.Adapters.NewsAdapter;
import com.jkurajpuriya.newsapps.Model.Articles;
import com.jkurajpuriya.newsapps.Model.CategoryModel;
import com.jkurajpuriya.newsapps.Model.NewsModel;
import com.jkurajpuriya.newsapps.R;
import com.jkurajpuriya.newsapps.RetrofitApi;
import com.jkurajpuriya.newsapps.databinding.ActivityMainBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CategoryAdapter.CategoryClickInterface {
    ActivityMainBinding binding;
    String ApiKey ="f6bfce49d7b34e7aa805acb776daf879";
    private Dialog dialog;
    private ArrayList<Articles> articlesArrayList;
    private ArrayList<CategoryModel> categoryModelArrayList;
    private NewsAdapter newsAdapter;
    private CategoryAdapter categoryAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        dialog=new Dialog(this);
        dialog.setContentView(R.layout.dialog_box);
        dialog.setCancelable(false);
        if (dialog.getWindow()!=null){
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        }
        dialog.show();

        articlesArrayList=new ArrayList<>();
        categoryModelArrayList=new ArrayList<>();

        newsAdapter = new NewsAdapter(articlesArrayList,this);
        categoryAdapter= new CategoryAdapter(categoryModelArrayList,this,this::onCategoryClick);

        binding.newsRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.newsRecycler.setAdapter(newsAdapter);
        binding.categoryRecycler.setAdapter(categoryAdapter);
        getCategories();
        getNews("All");
        newsAdapter.notifyDataSetChanged();

    }

    private void getCategories(){
        categoryModelArrayList.add(new CategoryModel("business","https://shorensteincenter.org/wp-content/uploads/2018/09/Webp.net-resizeimage.jpg"));
        categoryModelArrayList.add(new CategoryModel("entertainment","https://images.unsplash.com/photo-1603190287605-e6ade32fa852?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80"));
        categoryModelArrayList.add(new CategoryModel("general","https://media.istockphoto.com/id/1360932844/photo/cloud-pattern.jpg?s=1024x1024&w=is&k=20&c=EImTWc1Oq6CQWe_e55hqylCw1Z5FoYvn-aK2vtVIbdY="));
        categoryModelArrayList.add(new CategoryModel("health","https://media.istockphoto.com/id/1319031310/photo/doctor-writing-a-medical-prescription.jpg?s=1024x1024&w=is&k=20&c=nChrkueWF_kh09Gsm_cjWYAY1BnyQ3XMt9ITykBiIYQ="));
        categoryModelArrayList.add(new CategoryModel("science","https://media.istockphoto.com/id/1150397417/photo/abstract-luminous-dna-molecule-doctor-using-tablet-and-check-with-analysis-chromosome-dna.jpg?s=1024x1024&w=is&k=20&c=W63XzFDVgSVNx2Tcwf6G_SfxoKBZGeYjlecQIB4UQjY="));
        categoryModelArrayList.add(new CategoryModel("sports","https://media.istockphoto.com/id/949190756/photo/various-sport-equipments-on-grass.jpg?s=1024x1024&w=is&k=20&c=sMZL6tZpKAZihvQMPn5YnJaTMGh5bhrxsNJ_rJA0bWs="));
        categoryModelArrayList.add(new CategoryModel("technology","https://media.istockphoto.com/id/1311598658/photo/businessman-trading-online-stock-market-on-teblet-screen-digital-investment-concept.jpg?s=1024x1024&w=is&k=20&c=JZprgGDQ8xqa6iu0fyKJfKOlAvae0w9U-AdHeCT2kg4="));
        categoryAdapter.notifyDataSetChanged();
    }

    private void getNews(String category){
        articlesArrayList.clear();
        String categoryUrl="https://newsapi.org/v2/top-headlines?country=in&category="+category+"&apiKey="+ApiKey;
        String  BASE_URL="https://newsapi.org/v2/";
        String url="https://newsapi.org/v2/top-headlines?country=in&excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apiKey="+ApiKey;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
        Call<NewsModel> call;
        if (category.equals("All")){
            call=retrofitApi.getAllNews(url);
        }else {
            call=retrofitApi.getNewsByCategory(categoryUrl);
        }

        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                NewsModel newsModel=response.body();
                ArrayList<Articles>articles=newsModel.getArticles();
                for (int i=0; i<articles.size(); i++){
                    articlesArrayList.add(new Articles(articles.get(i).getTitle(),articles.get(i).getDescription(),articles.get(i).getUrlToImage(),
                            articles.get(i).getUrl(),articles.get(i).getContent()));
                    dialog.dismiss();
                }
                newsAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "FAILED....", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }
    @Override
    public void onCategoryClick(int position) {
        dialog.show();
        String category = categoryModelArrayList.get(position).getCategory();
        getNews(category);
        dialog.dismiss();
    }
}