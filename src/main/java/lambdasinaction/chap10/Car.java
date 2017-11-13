package lambdasinaction.chap10;

import java.util.*;

public class Car {
    //车·可能有保险也可能没有保险，因此这个字段声明为optional
    private Optional<Insurance> insurance;

    public Optional<Insurance> getInsurance() {
        return insurance;
    }
}
