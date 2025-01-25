package org.example.view;

public class View {

    public interface publicDetail{};
    public interface privateDetail{};
    public interface detailedInfo{};

    public interface detailedForFoods{};

    public interface operationOnRestaurant extends publicDetail, privateDetail{};

    public interface addOrder{};
}
