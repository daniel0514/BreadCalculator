package cal.bread.dan.breadcalculator2;

import java.util.Arrays;
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
    LinkedList<Bread> breadList;
    int star;
    int size = 0;
    int cost = 0;
    int totalPercentage = 0;
    int totalTrain = 0;
    List<Integer> levelCost = Arrays.asList(0, 0, 600, 1300, 1900, 3200, 4900);

    //Constructor
    public BreadList(int star){
        this.star = star;
        breadList = new LinkedList<Bread>();
    }
    //Constructor
    public BreadList(int star, Bread bread){
        this.star = star;
        breadList = new LinkedList<>();
        breadList.add(bread);
        totalPercentage += bread.getPercentage();
        totalTrain += bread.getTrain();
        cost += levelCost.get(star);
        size = 1;
    }
    //Constructor
    public BreadList(int star, String bread){
        this.star = star;
        Bread b = new Bread(bread);
        breadList = new LinkedList<>();
        breadList.add(b);
        totalPercentage += b.getPercentage();
        totalTrain += b.getTrain();
        cost += levelCost.get(star);
        size = 1;
    }
    //Constructor
    public BreadList(BreadList bList){
        this.star = bList.getStar();
        this.breadList = new LinkedList<>();
        for(Bread b: bList.getBreads()){
            this.breadList.add(b);
            totalPercentage += b.getPercentage();
            totalTrain += b.getTrain();
            cost +=levelCost.get(this.star);
            size++;
        }
    }

    /**
     * Adding bread to the list solely with bread's name
     * @param name  :   the name of the bread
     * @throws ListFullException    :   Throws an exception if the list is full (6 breads in the list already)
     */
    public void addBread(String name) throws ListFullException{
        if(size < 6) {
            Bread newBread = new Bread(name);
            breadList.add(newBread);
            totalPercentage += newBread.getPercentage();
            cost += levelCost.get(star);
            totalTrain += newBread.getTrain();
            size++;
        } else {
            throw new ListFullException();
        }
    }
    public Bread getBread(int index){
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
    public int getPercentage(){
        return totalPercentage;
    }

    public int getCost(){
        return cost;
    }

    public int getStar(){
        return star;
    }

    public LinkedList<Bread> getBreads(){
        return breadList;
    }

    public int getSize(){
        return breadList.size();
    }

}
