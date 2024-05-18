package com.example.pricepilot;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements StartBrowserIntentListener, ChangeDbCallback {
  private static final String BASE_URL = "http://77.221.154.82/";
  public static final int INPUT_FINISH_DELAY = 3000;
  private NotificationSender notificationSender;
  private AppDatabase appDatabase;
  private Handler eTHandler = new Handler();
  private Runnable eTrunnable;
  private NetworkModule networkModule;
  private EditText searchBarView = null;
  private ProgressBar progressBar = null;
  private RecyclerView productRecycler = null;
  private MaterialButton favoritesButton = null;
  private ProductAdapter adapter = null;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_activity);
    setUpViews();

    getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    EdgeToEdge.enable(this);

    networkModule = new NetworkModule(BASE_URL);
    appDatabase = AppDatabase.create(this);
    notificationSender = new NotificationSender(this, createResultLauncher());

    if (getIntent() != null) {
      String request = getIntent().getStringExtra(StartActivity.REQUEST_KEY);
      progressBar.setVisibility(View.VISIBLE);
      onInputFinished(request);
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }

  private void setUpViews() {
    searchBarView = findViewById(R.id.search_bar);
    productRecycler = findViewById(R.id.products_recycler_view);
    favoritesButton = findViewById(R.id.favorites_main_button);
    progressBar = findViewById(R.id.progressBar);
    favoritesButton.setIconResource(R.drawable.filled_heart_icon);

    adapter = new ProductAdapter(Collections.emptyList(),this, this, this);
    productRecycler.setAdapter(adapter);

    favoritesButton.setOnClickListener(v -> goToFavoritesActivity());

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

  private void goToFavoritesActivity() {
    Intent intent = new Intent(this, FavoritesActivity.class);
    startActivity(intent);
  }


  private void onInputFinished(String request) {
    networkModule.fetchData(
        request,
        new DataCallback<List<Product>>() {
          @Override
          public void onSuccess(List<Product> data) {
            List<Product> filtredData = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
              if (data.get(i).getProductName() != null) {
                filtredData.add(data.get(i));
              }
            }
            progressBar.setVisibility(View.INVISIBLE);
            if (filtredData.isEmpty()) {
              throw new RuntimeException();
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
  public void startBrowserIntent(String url) {
    Intent i = new Intent(Intent.ACTION_VIEW);
    i.setData(Uri.parse(url));
    startActivity(i);
  }

  @Override
  public void productLikeStatusChanged(Product product) {
    if (product.isLiked()) {
      appDatabase.productDAO().insert(product);
      notificationSender.setNotificationAlarm();
    } else {
      appDatabase.productDAO().deleteById(product.getId());
    }
  }

  private ActivityResultLauncher<Intent> createResultLauncher() {
    return registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        result -> notificationSender.setNotificationAlarm()
    );
  }
}