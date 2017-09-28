package lambdasinaction.chap11.v1;

import static lambdasinaction.chap11.Util.delay;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class Shop {

    private final String name;
    private final Random random;

    public Shop(String name) {
        this.name = name;
        random = new Random(name.charAt(0) * name.charAt(1) * name.charAt(2));
    }

    public double getPrice(String product) {
        return calculatePrice(product);
    }

    private double calculatePrice(String product) {
        delay();
        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }
    //异步方法的实现，后续异步客户端的调用
    public Future<Double> getPriceAsync(String product) {//将上面的getPrice同步方法变成异步方法
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();//创建CompletableFuture对象，它会包含计算的结果
        new Thread( () -> {//新的线程中
                    double price = calculatePrice(product);//在另一个线程中以异步的形式执行计算
                    futurePrice.complete(price);//需要长时间计算的任务结束并得出结果时，设置Future返回值
        }).start();
        return futurePrice;//无需等待还没结束的计算，直接返回Future对象
    }

    public String getName() {
        return name;
    }

}
