package com.example.pricepilot;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Collections;

public class FavoritesActivity extends AppCompatActivity implements StartBrowserIntentListener, ChangeDbCallback {
  private RecyclerView recyclerView;
  private ImageView arrowBack;
  private ProductAdapter adapter = null;
  private AppDatabase appDatabase;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    setContentView(R.layout.favorites_activity);

    appDatabase = AppDatabase.create(this);
    setUpViews();
  }

  private void setUpViews() {
    recyclerView = findViewById(R.id.favorites_products_recycler_view);
    arrowBack = findViewById(R.id.arrowBack);

    adapter = new ProductAdapter(Collections.emptyList(),this, this, this);
    recyclerView.setAdapter(adapter);
    adapter.changeData(appDatabase.productDAO().getAll());

    arrowBack.setOnClickListener(v -> {
      finish();
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
    } else {
      appDatabase.productDAO().deleteById(product.getId());
      adapter.changeData(appDatabase.productDAO().getAll());
    }
  }


}
