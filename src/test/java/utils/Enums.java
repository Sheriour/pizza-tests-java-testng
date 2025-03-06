package utils;

public class Enums {

    public enum PizzaAppTab {
        NEW_PIZZA,
        PIZZA_ARCHIVE,
        PIZZA_GENERATOR,
    }

    public enum DietType {
        ANY("Any"),
        VEGETARIAN("Vegetarian"),
        VEGAN("Vegan");

        private String value;

        public String getValue() {
            return value;
        }

        DietType(String value) {
            this.value = value;
        }
    }


}

