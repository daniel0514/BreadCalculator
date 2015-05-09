package cal.bread.dan.breadcalculator2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;


/**
 * Created by Dan on 05/05/2015.
 */
public class BreadOptimizer {
    PriorityQueue<TrainingList> listPQ;
    LinkedHashMap<String, Integer> breadHM;
    List<Integer> train = Arrays.asList(0, 0, 100, 800, 2900, 7900, 18500);
    List<Integer> trainRequired = new ArrayList<>(Collections.nCopies(7, 0));
    int startStar, endStar, startTrain, endTrain;

    public BreadOptimizer(LinkedHashMap<String, Integer> breadHM, ArrayList<Integer> goal) {
        this.breadHM = breadHM;
        listPQ = new PriorityQueue<>(100, new TrainingListComparator());
        startStar = goal.get(0);
        endStar = goal.get(1);
        startTrain = goal.get(2);
        endTrain = goal.get(3);
        calculateTrainRequired(startStar, endStar, startTrain, endTrain);
    }
    public TrainingList optimize() {
        return optimize1(startStar, endStar);
    }

    private TrainingList optimize1(Integer startStar, Integer endStar){
        for(String bread: breadHM.keySet()){
            if(breadHM.get(bread) > 0) {
                LinkedHashMap<String, Integer> newHM = new LinkedHashMap<>(breadHM);
                newHM.put(bread, newHM.get(bread) - 1);
                TrainingList newTrainList = new TrainingList(startStar, newHM, new BreadList(startStar, bread));
                listPQ.add(newTrainList);
            }
        }

        TrainingList tList;
        while(!listPQ.isEmpty()){
            boolean incStar = false;
            tList = listPQ.poll();
            if(tList.getTrainLevel().get(tList.getCurStar()) >= trainRequired.get(tList.getCurStar())) {
                if(tList.getCurStar()== endStar){
                    return tList;
                }
                incStar = true;
            }
            for(String bread: tList.getAvailableBread().keySet()){
                if(tList.getAvailableBread().get(bread) > 0) {
                    LinkedHashMap<String, Integer> newHM = new LinkedHashMap<>(tList.getAvailableBread());
                    int breadCount = newHM.get(bread);
                    newHM.put(bread, breadCount - 1);
                    LinkedList<BreadList> newBLists = new LinkedList<>();
                    for(BreadList bl: tList.getLists()){
                        newBLists.add(new BreadList(bl));
                    }
                    if(incStar){
                        newBLists.add(new BreadList(tList.getCurStar() + 1, bread));
                    } else {
                        try {
                            newBLists.getLast().addBread(bread);
                        } catch (ListFullException e) {
                            newBLists.add(new BreadList(tList.getCurStar(), bread));
                        }
                    }
                    TrainingList newTrainList = new TrainingList(tList.getCurStar(), newHM, newBLists);
                    if(incStar){
                        newTrainList.setCurStar(newTrainList.getCurStar() + 1);
                    }
                    listPQ.add(newTrainList);
                }
            }
        }
        return null;
    }

    public void calculateTrainRequired(Integer startStar, Integer endStar, Integer startTrain, Integer endTrain){
        for (int i = startStar; i <= endStar; i++) {
            if (i == startStar) {
                trainRequired.set(i, train.get(i) - startTrain);
            }
            if (i == endStar) {
                trainRequired.set(i,  endTrain);
            }
            if( i != startStar && i != endStar){
                trainRequired.set(i, train.get(i));
            }
        }
    }


}

class TrainingListComparator implements Comparator<TrainingList> {
    @Override
    public int compare(TrainingList lhs, TrainingList rhs) {
        int diff = lhs.getCost() - rhs.getCost();
        if(diff == 0){
            int i = 0;
            while( i < Math.min(rhs.getLists().size(), lhs.getLists().size())){
                BreadList lhsList = lhs.getLists().get(i);
                BreadList rhsList = rhs.getLists().get(i);
                if(lhsList.getTrain() != rhsList.getTrain()) {
                    return lhsList.getTrain() - rhsList.getTrain();
                }
                i++;
            }
            return 0;
        } else {
            return diff;
        }
    }
}

