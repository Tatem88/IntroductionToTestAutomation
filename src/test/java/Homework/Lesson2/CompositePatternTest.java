package Homework.Lesson2;
import org.Homework.Lesson2.Component;
import org.Homework.Lesson2.Composite;
import org.Homework.Lesson2.Leaf;
import org.testng.annotations.Test;

import static org.junit.Assert.assertEquals;
public class CompositePatternTest {
    @Test
    public static void main(String[] args) {
        Component leaf1 = new Leaf();
        Component leaf2 = new Leaf();
        Composite composite = new Composite();
        composite.addComponent(leaf1);
        composite.addComponent(leaf2);
        composite.operation();
    }
}
