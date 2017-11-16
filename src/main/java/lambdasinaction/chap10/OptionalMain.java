package lambdasinaction.chap10;

import java.util.*;

public class OptionalMain {

    public String getCarInsuranceName(Optional<Person> person) {
        return person.flatMap(Person::getCar)
                     .flatMap(Car::getInsurance)
                     .map(Insurance::getName)
                     .orElse("Unknown");//如果optional的结果值为空，设置默认值
    }
}
