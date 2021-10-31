package controller.utils;

public interface IManager<T>{
  T provide(String suffix);
}
