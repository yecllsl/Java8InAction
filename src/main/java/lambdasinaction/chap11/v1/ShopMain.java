package lambdasinaction.chap11.v1;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ShopMain {

  public static void main(String[] args) {
    Shop shop = new Shop("BestShop");
    long start = System.nanoTime();
    Future<Double> futurePrice = shop.getPriceAsync("my favorite product");//查询商品，试图取得商品的价格
    long invocationTime = ((System.nanoTime() - start) / 1_000_000);
    System.out.println("Invocation returned after " + invocationTime 
                                                    + " msecs");
    // Do some more tasks, like querying other shops
    doSomethingElse();
    // while the price of the product is being calculated
    try {
        double price = futurePrice.get();//从future对象中都区价格，如果价格未知，会发生阻塞
        System.out.printf("Price is %.2f%n", price);
    } catch (ExecutionException | InterruptedException e) {
        throw new RuntimeException(e);
    }
    long retrievalTime = ((System.nanoTime() - start) / 1_000_000);
    System.out.println("Price returned after " + retrievalTime + " msecs");
  }

  private static void doSomethingElse() {
      System.out.println("Doing something else...");
  }

}
