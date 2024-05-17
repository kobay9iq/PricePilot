package com.example.pricepilot;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {

  private List<Product> products;
  private LayoutInflater inflater;
  private StartBrowserIntentListener listener;
  private ChangeDbCallback dbCallback;

  public ProductAdapter(List<Product> products, Context context,
      StartBrowserIntentListener listener, ChangeDbCallback dbCallback) {
    this.products = products;
    this.listener = listener;
    this.dbCallback = dbCallback;
    inflater = LayoutInflater.from(context);
  }

  @NonNull
  @Override
  public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new ProductViewHolder(inflater.inflate(R.layout.fragment_product_item, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
    holder.bind(getItem(position), listener, dbCallback);
  }

  @Override
  public int getItemCount() {
    return products.size();
  }

  private Product getItem(int position) {
    return products.get(position);
  }

  public void changeData(List<Product> products) {
    this.products = products;
    notifyDataSetChanged();
  }
}
