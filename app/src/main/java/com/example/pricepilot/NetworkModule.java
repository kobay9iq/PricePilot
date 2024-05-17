package com.example.pricepilot;

import android.util.Log;
import java.util.List;
import java.util.concurrent.TimeUnit;
import kotlinx.serialization.json.Json;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import okhttp3.MediaType;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NetworkModule {
  private Json json;
  private Retrofit retrofit;
  private ApiService apiService;

  public NetworkModule(String baseURL) {
    json = Json.Default;
    MediaType contentType = MediaType.get("application/json");

    OkHttpClient client = new OkHttpClient.Builder()
        .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build();


    retrofit = new Retrofit.Builder()
        .baseUrl(baseURL)
        .client(client)
        .addConverterFactory(RetrofitExtensionsKt.asConverterFactoryWrapper(json, contentType))
        .build();

    apiService = retrofit.create(ApiService.class);
  }

  public void fetchData(String request, DataCallback<List<Product>> callback) {
    Call<List<Product>> call = apiService.getProducts(request);

    call.enqueue(
        new Callback<List<Product>>() {
          @Override
          public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
            if (response.isSuccessful() && response.body() != null) {
              List<Product> products = response.body();
              callback.onSuccess(products);
            } else {
              Log.e("MainActivity", "Response not successful");
            }
          }

          @Override
          public void onFailure(Call<List<Product>> call, Throwable throwable) {
            Log.e("MainActivity", "Error: " + throwable.getMessage());
            callback.onFailure(throwable);
          }
        });
  }
}



