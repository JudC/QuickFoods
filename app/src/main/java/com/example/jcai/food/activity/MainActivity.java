package com.example.jcai.food.activity;

import android.content.Context;
import android.app.SearchManager;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.jcai.food.api.models.Recipes;
import com.example.jcai.food.api.models.SearchResponse;
import com.example.jcai.food.recyclerview.RecipesAdapter;
import com.example.jcai.test.R;
import com.example.jcai.food.events.GetSearchResponseEvent;
import com.example.jcai.food.events.SendSearchResponseEvent;
import com.example.jcai.food.manager.RecipesSearchManager;
import com.example.jcai.food.api.remote.RetrofitClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecipesAdapter recipesAdapter;
    private SearchView searchView;
    private Toolbar toolbar;
    private RecipesSearchManager mSearchManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //Get handle for toolbar
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        //Register Search Manager
        mSearchManager = new RecipesSearchManager(this);

        //Check connection
        checkNetworkConnection();

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        if (!EventBus.getDefault().isRegistered(mSearchManager)) {
            EventBus.getDefault().register(mSearchManager);
        }

        // Get the intent from search query
        getSearchIntent(getIntent());

        Log.d("MainActivity", "end of oncreate");
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.d("MainActivity", "onStart");
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        if (!EventBus.getDefault().isRegistered(mSearchManager)) {
            EventBus.getDefault().register(mSearchManager);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("MainActivity", "onStop");
        EventBus.getDefault().unregister(mSearchManager);
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        Log.d("MainActivity", "configure search widget");

        return true;
    }

    @Subscribe
    public void onSendSearchResponseEvent(SendSearchResponseEvent searchResponse) {
        Response<SearchResponse> response = searchResponse.getResponse();
        ArrayList<Recipes> recipes = response.body().getRecipes();
        RequestManager glide = Glide.with(this);

        Log.d("MainActivity", "loadrecipes");

        // Get a handle to the RecyclerView.
        mRecyclerView = findViewById(R.id.cardList);
        mRecyclerView.setHasFixedSize(true);

        // Create an adapter and supply the data to be displayed.
        recipesAdapter = new RecipesAdapter(this, recipes, glide);
        // Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(recipesAdapter);
        // Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void searchViewListener() {
        Log.d("MainActivity", "Start searchviewlistener");

        searchView = findViewById(R.id.search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                return true;
            }
        });

    }

    private void checkNetworkConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService
                (Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = cm.getActiveNetworkInfo();

        Toast toast;

        if (activeInfo != null && activeInfo.isConnected()) {
            toast = Toast.makeText(getApplicationContext(), "Connected",
                    Toast.LENGTH_LONG);
        } else {
            toast = Toast.makeText(getApplicationContext(), "Not Connected",
                    Toast.LENGTH_LONG);
        }

        Log.d("MainActivity", "Check network");

        toast.show();
    }

    private void search(String query) {
        Log.d("MainActivity", "search");

        //Initiate post
        GetSearchResponseEvent stickyEvent = EventBus.getDefault().getStickyEvent
                (GetSearchResponseEvent.class);

        if (stickyEvent == null){
            Log.d("MainActivity", "Searchresponse is null");
            EventBus.getDefault().post(new GetSearchResponseEvent(RetrofitClient.api_key,
                    query, null, 1, true));
        }

    }

    protected void onNewIntent(Intent intent){
        setIntent(intent);
        getSearchIntent(intent);
    }

    private void getSearchIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            search(query);
        }
    }
}

   /* private void setCallResponse(){

        //Call method with query to get recipes
        mSearchManager = new RecipesSearchManager(this);
        EventBus.getDefault().register(mSearchManager);

        Log.d("MainActivity", "setCallResponse");


      /*  Call<SearchResponse> getSearchResponse = mf2FApiService.getSearchResponse
                ("5ab2b60fffab514bf00ba73304e57af1", null, null, 1);

        getSearchResponse.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {

                if (response.isSuccessful()) {
                    Log.d("MainActivity", "Success");

                    loadRecipes(response.body().getRecipes());

                } else {
                    int statusCode = response.code();
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                Log.d("MainActivity", "Error loading api");
            }
        });*/


