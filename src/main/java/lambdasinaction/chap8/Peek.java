package lambdasinaction.chap8;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;


public class Peek {

    public static void main(String[] args) {

        List<Integer> result = Stream.of(2, 3, 4, 5)
                .peek(x -> System.out.println("taking from stream: " + x)).map(x -> x + 17)//输出来自数据源的当前元素值
                .peek(x -> System.out.println("after map: " + x)).filter(x -> x % 2 == 0)//输出map操作结果
                .peek(x -> System.out.println("after filter: " + x)).limit(3)//输出经过filter操作之后，剩下的元素个数
                .peek(x -> System.out.println("after limit: " + x)).collect(toList());//输出经过limit操作之后，剩下的元素个数
    }
}
