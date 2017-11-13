package lambdasinaction.chap10;

import java.util.*;

public class Person {
    //人可能有车也可能没有车，因此这个字段声明为optional
    private Optional<Car> car;

    public Optional<Car> getCar() {
        return car;
    }
}
