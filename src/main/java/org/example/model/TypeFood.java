package org.example.model;

public enum TypeFood {
    IRANIAN,
    FASTFOOD,
    PIZZA,
    SALAD,
    SEA,
    FRIED,
    SANDWICH,
    INTERNATIONAL;

    public String getTypeFood(){
        switch (this){
            case SEA -> {
                return "غذای دریایی";
            }
            case FRIED -> {
                return "سوخاری";
            }
            case PIZZA -> {
                return "پیتزا";
            }
            case SALAD -> {
                return "سالاد";
            }
            case IRANIAN -> {
                return "ایرانی";
            }
            case FASTFOOD -> {
                return "فست فود";
            }
            case SANDWICH -> {
                return "ساندویچ";
            }
            case INTERNATIONAL -> {
                return "بین الملل";
            }
            default -> {
                return "نوع غذا یافت نشد.";
            }
        }
    }

}
