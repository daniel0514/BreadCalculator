package cal.bread.dan.breadcalculator2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Dan on 05/05/2015.
 */
public class TrainingList {
    int totalCost = 0;
    int totalTrain = 0;
    List<Integer> trainLevel = Arrays.asList(0, 0, 0, 0, 0);
    int lastPos = -1;
    int curStar;
    LinkedList<BreadList> breadLists = new LinkedList<>();
    HashMap<String, Integer> availableBread;

    public TrainingList(Integer startStar, HashMap<String, Integer> availableBread){
        this.curStar = startStar;
        availableBread = new HashMap<>(availableBread);
    }

    public TrainingList(Integer startStar, HashMap<String, Integer> availableBread, BreadList breadList){
        availableBread = new HashMap<>(availableBread);
        breadLists.add(breadList);
        this.curStar = startStar;
        totalCost += breadList.getCost();
        totalTrain += breadList.getTrain();
        lastPos++;
    }

    public void addList(BreadList breadList){
        breadLists.add(breadList);
        totalCost += breadList.getCost();
        totalTrain += breadList.getTrain();
        lastPos++;
    }

    public void addBreadLastList(Bread bread){
        BreadList breadList = breadLists.get(lastPos);
        try {
            breadList.addBread(bread);
        }catch (ListFullException lfe){
            BreadList newList = new BreadList(curStar);
            try {
                newList.addBread(bread);
            } catch (ListFullException nlfe){

            }
            breadLists.add(newList);
        }
        recalculate();
    }

    public void removeBreadLastList(Bread bread){
        BreadList breadList = breadLists.get(lastPos);
        breadList.removeBread(bread);
        recalculate();
    }

    private void recalculate(){
        totalCost = 0;
        for(BreadList list : breadLists){
            int trainInLevel = trainLevel.get(list.getStar());
            trainLevel.set(list.getStar(), trainInLevel + list.getTrain());
            totalCost += list.getCost();
        }
    }

    public int getCost(){
        return totalCost;
    }

    public HashMap<String, Integer> getAvailableBread(){
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
