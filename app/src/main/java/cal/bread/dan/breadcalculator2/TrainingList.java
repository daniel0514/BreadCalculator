package cal.bread.dan.breadcalculator2;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Collections;

/**
 * Created by Dan on 05/05/2015.
 *   A Training List : Combination of Bread Lists
 *          totalCost   : The total cost of the bread lists
 *          trainLevel: An ArrayList containing the amount of training the TrainingList has for each level
 *          curStar     : Current level of the hero using the TrainingList
 *          breadLists : a LinkedList containing the bread lists
 *          availableBread: A HashMap containing the number of available breads left
 */
public class TrainingList {
    int totalCost = 0;
    List<Integer> trainLevel = new ArrayList<>(Collections.nCopies(7, 0));
    int curStar;
    LinkedList<BreadList> breadLists = new LinkedList<>();
    LinkedHashMap<String, Integer> availableBread;

    //Constructor
    public TrainingList(Integer startStar, LinkedHashMap<String, Integer> availableBread, BreadList breadList){
        this.availableBread = new LinkedHashMap<>(availableBread);
        breadLists.add(breadList);
        this.curStar = startStar;
        totalCost += breadList.getCost();
        trainLevel.set(breadList.getStar(), trainLevel.get(breadList.getStar()) + breadList.getTrain());
    }
    //Constructor
    public TrainingList(Integer startStar, LinkedHashMap<String, Integer> availableBread, LinkedList<BreadList> lists){
        this.availableBread = new LinkedHashMap<>(availableBread);
        breadLists = lists;
        this.curStar = startStar;
        for(BreadList list : lists){
            totalCost += list.getCost();
            trainLevel.set(list.getStar(), trainLevel.get(list.getStar()) + list.getTrain());
        }
    }
    public LinkedList<BreadList> getLists(){
        return breadLists;
    }

    public int getCost(){
        return totalCost;
    }

    public LinkedHashMap<String, Integer> getAvailableBread(){
        return availableBread;
    }

    public void setCurStar(int star){
        curStar = star;
    }

    public int getCurStar(){
        return curStar;
    }

    public List<Integer> getTrainLevel(){
        return trainLevel;
    }

}
