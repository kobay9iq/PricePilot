package com.example.pricepilot;

import retrofit2.Call;
import retrofit2.http.GET;
import java.util.List;
import retrofit2.http.Query;


public interface ApiService {
  @GET("parseProducts")
  Call<List<Product>> getProducts(@Query("productsName") String request);
}
