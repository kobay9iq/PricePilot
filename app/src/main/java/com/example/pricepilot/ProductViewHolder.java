package com.example.pricepilot;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

public class ProductViewHolder extends RecyclerView.ViewHolder {
  private ImageView productImageView = itemView.findViewById(R.id.image_product);
  private ImageView marketLogoView = itemView.findViewById(R.id.image_market_logo);
  private TextView marketNameView = itemView.findViewById(R.id.text_market_name);
  private TextView priceTagView = itemView.findViewById(R.id.text_price_tag);
  private ImageView heartIconView = itemView.findViewById(R.id.button_favorite);
  private TextView productNameView = itemView.findViewById(R.id.text_product_name);
  private TextView displayedPrice = itemView.findViewById(R.id.text_displayed_price);

  public ProductViewHolder(@NonNull View itemView) {
    super(itemView);
  }

  public void bind(Product product) {
    productImageView.setImageDrawable(product.getProductLogo());
    marketLogoView.setImageDrawable(product.getMarketLogo());
    marketNameView.setText(product.getMarketName());
    priceTagView.setText(product.getPrice());
    productNameView.setText(product.getProductName());
    displayedPrice.setText(product.getPrice());
  }
}
