package com.example.pricepilot;

import android.os.AsyncTask;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import java.util.Collections;
import java.util.List;
import kotlin.collections.AbstractMutableList;

public class MainActivity extends AppCompatActivity {
  private static final String BASE_URL = "http://77.221.154.82/";
  private static final int INPUT_FINISH_DELAY = 3000;
  private Handler eTHandler = new Handler();
  private NetworkModule networkModule;
  private Runnable eTrunnable;
  private EditText searchBarView = null;
  private RecyclerView productRecycler = null;
  private MaterialButton favoritesButton = null;
  private ProductAdapter adapter = null;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_activity);
    setUpViews();

    getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

    favoritesButton.setIconResource(R.drawable.filled_heart_icon);

    networkModule = new NetworkModule(BASE_URL);
  }

  private void setUpViews() {
    searchBarView = findViewById(R.id.search_bar);
    productRecycler = findViewById(R.id.products_recycler_view);
    favoritesButton = findViewById(R.id.favorites_main_button);

    adapter = new ProductAdapter(Collections.emptyList(),this);
    productRecycler.setAdapter(adapter);

    searchBarView.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (eTrunnable != null) {
          eTHandler.removeCallbacks(eTrunnable);
        }
      }

      @Override
      public void afterTextChanged(Editable s) {
        eTrunnable = new Runnable() {
          @Override
          public void run() {
            onInputFinished(s.toString());
          }
        };
        eTHandler.postDelayed(eTrunnable, INPUT_FINISH_DELAY);
      }
    });
  }


  private void onInputFinished(String request) {
    networkModule.fetchData(request, new DataCallback<List<Product>>() {
      @Override
      public void onSuccess(List<Product> data) {
        List<Product> filtredData = Collections.emptyList();
        for (int i = 0; i < data.size(); i++) {
          if (data.get(i).getProductName() == null) {
            filtredData.add(data.get(i));
          }
        }
        adapter.changeData(filtredData);
      }

      @Override
      public void onFailure(Throwable t) {
        Toast.makeText(MainActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();
      }
    });
  }


  @Override
  protected void onDestroy() {
    super.onDestroy();
  }
}