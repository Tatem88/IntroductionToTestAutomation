package org.Homework.Lesson2;
import java.util.ArrayList;
import java.util.List;
public class Composite implements Component {
    private List<Component> components = new ArrayList<>();

    public void addComponent(Component component) {
        components.add(component);
    }

    public void removeComponent(Component component) {
        components.remove(component);
    }

    public void operation() {
        System.out.println("Performing an operation in Composite");
        for (Component component : components) {
            component.operation();
        }
    }
}