package com.example.pricepilot;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface ProductDAO {
  @Insert
  public void insertAll(List<Product> productList);

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  public void insert(Product product);

  @Update
  public void update(Product product);

  @Query("SELECT * FROM Products ORDER BY ProductPrice ASC")
  public List<Product> getProducts();

  @Delete
  public void delete(Product product);

  @Query("DELETE FROM Products WHERE _id == :id")
  public void deleteById(Long id);

  @Query("SELECT * FROM Products")
  public List<Product> getAll();
}
