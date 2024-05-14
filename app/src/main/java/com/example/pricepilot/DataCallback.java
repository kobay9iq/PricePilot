package com.example.pricepilot;

public interface DataCallback<T> {
  void onSuccess(T data);
  void onFailure(Throwable t);
}
