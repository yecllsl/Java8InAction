package lambdasinaction.chap11;

import static lambdasinaction.chap11.Util.delay;
import static lambdasinaction.chap11.Util.format;

public class Discount {
    //枚举定义的折扣代码
    public enum Code {
        NONE(0), SILVER(5), GOLD(10), PLATINUM(15), DIAMOND(20);

        private final int percentage;

        Code(int percentage) {
            this.percentage = percentage;
        }
    }

    public static String applyDiscount(Quote quote) {
        //将折扣代码应用于商品最初的原始价格
        return quote.getShopName() + " price is " + Discount.apply(quote.getPrice(), quote.getDiscountCode());
    }
    private static double apply(double price, Code code) {
        delay();//模拟服务延迟
        return format(price * (100 - code.percentage) / 100);
    }
}
