package cal.bread.dan.breadcalculator2;

/**
 * Created by Dan on 04/05/2015.
 * A Bread consists of the following attributes
 *         Percentage: The chance of Great Success for Bread Consumption
 *         Train        : The amount of training that the bread offers
 *         Star          : The level (star) of the bread
 *         Name       : The name of the bread
 */
public class Bread{
    private int percentage;
    private int train;
    private int star;
    private String name;

    // Constructor of Bread
    public Bread(String name) {
        percentage = percentageIs(name);
        train = trainIs(name);
        star = starIs(name);
        this.name = name;
    }

    public int getPercentage() {
        return percentage;
    }
    public int getTrain() {
        return train;
    }
    public String getName() {
        return name;
    }

    /**
     * Return the amount of training the bread offers
     * @param name           : the name of the bread
     * @return percentage    : the percentage of Great Success by the bread
     */
    private int percentageIs(String name) {
        int percentage;
        switch(name) {
            case "Macaroon":
            case "Strawberry Pie":
            case "Cream Bread":
            case "Croissant":
            case "Bread":
            case "Morning Bread":
            case "Chocolate":
                percentage = 0;
                break;
            case "Choco Cup Cake":
                percentage = 15;
                break;
            case "Sausage Bread":
            case "Hot Dog":
            case "Snake Wrap":
            case "Sandwich":
            case "Pizza":
            case "Hamburger":
                percentage = 20;
                break;
            case "Christmas Cake":
                percentage = 25;
                break;
            case "Shamrock Cup Cake":
                percentage = 30;
                break;
            case "Donut":
            case "Choco Donut":
            case "Jelly Roll":
            case "Rice Donut":
            case "Strawberry Donut":
            case "Special Donut":
                percentage = 40;
                break;
            case "Big Chocec Cake":
                percentage = 50;
                break;
            default:
                throw new IllegalArgumentException("Invalid bread name: " + name);
        }
        return percentage;
    }


    /**
     * Return the amount of training the bread offers
     * @param name  : the name of the bread
     * @return train    : the amount of training the bread offers
     */
    private int trainIs(String name) {
        int train;
        switch(name) {
            case "Macaroon":
                train = 600;
                break;
            case "Strawberry Pie":
                train = 330;
                break;
            case "Cream Bread":
                train = 180;
                break;
            case "Croissant":
                train = 100;
                break;
            case "Bread":
                train = 50;
                break;
            case "Morning Bread":
                train = 30;
                break;
            case "Chocolate":
                train = 280;
                break;
            case "Choco Cup Cake":
                train = 240;
                break;
            case "Sausage Bread":
                train = 24;
                break;
            case "Hot Dog":
                train = 40;
                break;
            case "Snake Wrap":
                train = 80;
                break;
            case "Sandwich":
                train = 144;
                break;
            case "Pizza":
                train = 264;
                break;
            case "Hamburger":
                train = 480;
                break;
            case "Christmas Cake":
                train = 150;
                break;
            case "Shamrock Cup Cake":
                train = 200;
                break;
            case "Donut":
                train = 18;
                break;
            case "Choco Donut":
                train = 30;
                break;
            case "Jelly Roll":
                train = 60;
                break;
            case "Rice Donut":
                train = 108;
                break;
            case "Strawberry Donut":
                train = 198;
                break;
            case "Special Donut":
                train = 360;
                break;
            case "Big Chocec Cake":
                train = 140;
                break;
            default:
                throw new IllegalArgumentException("Invalid bread name: " + name);
        }
        return train;
    }

    /**
     * Return the amount of training the bread offers
     * @param name  : the name of the bread
     * @return train    : the level of the bread
     */
    private int starIs(String name) {
        int star;
        switch(name) {
            case "Macaroon":
            case "Hamburger":
            case "Special Donut":
                star = 6;
                break;
            case "Strawberry Pie":
            case "Pizza":
            case "Strawberry Donut":
                star = 5;
                break;
            case "Cream Bread":
            case "Chocolate":
            case "Choco Cup Cake":
            case "Sandwich":
            case "Christmas Cake":
            case "Shamrock Cup Cake":
            case "Rice Donut":
            case "Big Chocec Cake":
                star = 4;
                break;
            case "Croissant":
            case "Snake Wrap":
            case "Jelly Roll":
                star = 3;
                break;
            case "Bread":
            case "Hot Dog":
            case "Choco Donut":
                star = 2;
                break;
            case "Morning Bread":
            case "Sausage Bread":
            case "Donut":
                star = 1;
                break;
            default:
                throw new IllegalArgumentException("Invalid bread name: " + name);
        }
        return star;
    }
}
