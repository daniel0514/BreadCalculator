package cal.bread.dan.breadcalculator2;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Collections;

/**
 * Created by Dan on 05/05/2015.
 */
public class TrainingList {
    int totalCost = 0;
    int totalTrain = 0;
    List<Integer> trainLevel = new ArrayList<>(Collections.nCopies(7, 0));
    int lastPos = -1;
    int curStar;
    LinkedList<BreadList> breadLists = new LinkedList<>();
    LinkedHashMap<String, Integer> availableBread;

    public TrainingList(Integer startStar, LinkedHashMap<String, Integer> availableBread){
        this.curStar = startStar;
        availableBread = new LinkedHashMap<>(availableBread);
    }

    public TrainingList(Integer startStar, LinkedHashMap<String, Integer> availableBread, BreadList breadList){
        this.availableBread = new LinkedHashMap<>(availableBread);
        breadLists.add(breadList);
        this.curStar = startStar;
        totalCost += breadList.getCost();
        totalTrain += breadList.getTrain();
        trainLevel.set(breadList.getStar(), trainLevel.get(breadList.getStar()) + breadList.getTrain());
        lastPos++;
    }

    public TrainingList(Integer startStar, LinkedHashMap<String, Integer> availableBread, LinkedList<BreadList> lists){
        this.availableBread = new LinkedHashMap<>(availableBread);
        breadLists = lists;
        this.curStar = startStar;
        for(BreadList list : lists){
            totalCost += list.getCost();
            totalTrain += list.getTrain();
            trainLevel.set(list.getStar(), trainLevel.get(list.getStar()) + list.getTrain());
        }
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
    public int getTotalTrain(){
        return totalTrain;
    }

}
