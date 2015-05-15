package cal.bread.dan.breadcalculator2;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Dan on 04/05/2015.
 *      A list of breads. Maximum number of breads that a list can hold is 6.
 *              breadList       :  an LinkedList containing the breads
 *              star               :  the star level of the hero that is consuming the list
 *              size               : the number of breads in the list
 *              cost                : the cost to consume the list
 *              totalPercentage: the total amount of percentage of the breads
 *              totalTrain        : the total aomount of training the list offers
 *              levelCost        : consist of cost of bread for different hero levels
 */
public class BreadList {
    private LinkedList<String> breadList;
    static LinkedHashMap<String, Integer> trainHM;
    static LinkedHashMap<String, Integer> percentHM;
    static LinkedHashMap<String, Integer> starHM;
    static List<Integer> levelCost = Arrays.asList(0, 0, 600, 1300, 1900, 3200, 4900);
    private int star;
    private int size = 0;
    private int cost = 0;
    private int totalPercentage = 0;
    private int totalTrain = 0;

    //Constructor for an Empty BreadList
    public BreadList(int star){
        setUpHashMaps();
        this.star = star;
        breadList = new LinkedList<String>();
    }
    //Constructor for a BreadList with one bread
    public BreadList(int star, String bread){
        setUpHashMaps();
        this.star = star;
        breadList = new LinkedList<>();
        breadList.add(bread);
        totalPercentage += percentHM.get(bread);
        totalTrain += trainHM.get(bread);
        cost += levelCost.get(star);
        size = 1;
    }
    //Constructor for a BreadList with same Breads in the bList
    public BreadList(BreadList bList){
        setUpHashMaps();
        this.star = bList.getStar();
        this.breadList = new LinkedList<>();
        for(String bread: bList.getBreads()){
            this.breadList.add(bread);
            totalPercentage += percentHM.get(bread);
            totalTrain += trainHM.get(bread);
            cost +=levelCost.get(this.star);
            size++;
        }
    }

    /**
     * Adding bread to the list solely with bread's name
     * @param bread  :   the name of the bread
     * @throws ListFullException    :   Throws an exception if the list is full (6 breads in the list already)
     */
    public void addBread(String bread) throws ListFullException{
        if(size < 6) {
            breadList.add(bread);
            totalPercentage += percentHM.get(bread);
            cost += levelCost.get(star);
            totalTrain += trainHM.get(bread);
            size++;
        } else {
            throw new ListFullException();
        }
    }
    public String getBread(int index){
        return breadList.get(index);
    }

    /**
     *  Get the amount of training that the list offers. If the total percentage of the list is
     *  greater than 100%, then the amount of training that the list offers is increased
     *  by 50%
     * @return
     */
    public int getTrain(){
        if(totalPercentage < 100){
            return totalTrain;
        } else {
            int returnVal = (int) (totalTrain * 1.5);
            return returnVal;
        }
    }
    public int getCost(){
        return cost;
    }
    public int getStar(){
        return star;
    }
    public LinkedList<String> getBreads(){
        return breadList;
    }
    public int getSize(){
        return breadList.size();
    }

    /**
     * Set Up HashMaps for Bread Information
     */
    private void setUpHashMaps(){
        //Set Up HashMap for bread star level if HM is not set up already
        if(starHM == null){
            setUpStarHM();
        }
        //Set Up HashMap for bread train amount if HM is not set up already
        if(trainHM == null){
            setUpTrainHM();
        }
        //Set Up HashMap for bread percentage if HM is not set up already
        if(percentHM == null){
            setUpPercentHM();
        }
    }

    //Setting up the HashMap for Information of Bread Level (Stars)
    private void setUpStarHM(){
        starHM = new LinkedHashMap<>(23);
        starHM.put("Macaroon", 6);
        starHM.put("Hamburger", 6);
        starHM.put("Special Donut", 6);
        starHM.put("Strawberry Pie", 5);
        starHM.put("Chocolate", 4);
        starHM.put("Pizza", 5);
        starHM.put("Choco Cup Cake", 4);
        starHM.put("Shamrock Cup Cake", 4);
        starHM.put("Strawberry Donut", 5);
        starHM.put("Cream Bread", 4);
        starHM.put("Christmas Cake", 4);
        starHM.put("Sandwich", 4);
        starHM.put("Big Choco Cake", 4);
        starHM.put("Rice Donut", 4);
        starHM.put("Croissant", 3);
        starHM.put("Snake Wrap", 3);
        starHM.put("Jelly Roll", 3);
        starHM.put("Bread", 2);
        starHM.put("Hot Dog", 2);
        starHM.put("Morning Bread", 1);
        starHM.put("Choco Donut", 2);
        starHM.put("Sausage Bread", 1);
        starHM.put("Donut", 1);
    }
    //Setting up the HashMap for Information of Bread Training amount
    private void setUpTrainHM(){
        trainHM = new LinkedHashMap<>(23);
        trainHM.put("Macaroon", 600);
        trainHM.put("Hamburger", 480);
        trainHM.put("Special Donut", 360);
        trainHM.put("Strawberry Pie", 330);
        trainHM.put("Chocolate", 280);
        trainHM.put("Pizza", 264);
        trainHM.put("Choco Cup Cake", 240);
        trainHM.put("Shamrock Cup Cake", 200);
        trainHM.put("Strawberry Donut", 198);
        trainHM.put("Cream Bread", 180);
        trainHM.put("Christmas Cake", 150);
        trainHM.put("Sandwich", 144);
        trainHM.put("Big Choco Cake", 140);
        trainHM.put("Rice Donut", 108);
        trainHM.put("Croissant", 100);
        trainHM.put("Snake Wrap", 80);
        trainHM.put("Jelly Roll", 60);
        trainHM.put("Bread", 50);
        trainHM.put("Hot Dog", 40);
        trainHM.put("Morning Bread", 30);
        trainHM.put("Choco Donut", 30);
        trainHM.put("Sausage Bread", 24);
        trainHM.put("Donut", 18);
    }
    //Setting up the HashMap for Information of Bread Percentage
    private void setUpPercentHM(){
        percentHM = new LinkedHashMap<>(23);
        percentHM.put("Macaroon", 0);
        percentHM.put("Hamburger", 20);
        percentHM.put("Special Donut", 40);
        percentHM.put("Strawberry Pie", 0);
        percentHM.put("Chocolate", 0);
        percentHM.put("Pizza", 20);
        percentHM.put("Choco Cup Cake", 15);
        percentHM.put("Shamrock Cup Cake", 30);
        percentHM.put("Strawberry Donut", 40);
        percentHM.put("Cream Bread", 0);
        percentHM.put("Christmas Cake", 25);
        percentHM.put("Sandwich", 20);
        percentHM.put("Big Choco Cake", 50);
        percentHM.put("Rice Donut", 40);
        percentHM.put("Croissant", 0);
        percentHM.put("Snake Wrap", 20);
        percentHM.put("Jelly Roll", 40);
        percentHM.put("Bread", 0);
        percentHM.put("Hot Dog", 20);
        percentHM.put("Morning Bread", 0);
        percentHM.put("Choco Donut", 40);
        percentHM.put("Sausage Bread", 20);
        percentHM.put("Donut", 40);
    }

}
