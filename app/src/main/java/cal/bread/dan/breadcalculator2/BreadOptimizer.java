package cal.bread.dan.breadcalculator2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;
import java.util.PriorityQueue;


/**
 * Created by Dan on 05/05/2015.
 */
public class BreadOptimizer {
    PriorityQueue<TrainingList> listPQ;
    HashMap<String, Integer> breadHM;
    List<Integer> train = Arrays.asList(100, 800, 2900, 7900, 18500);
    List<Integer> trainRequired = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0));
    LinkedList<Bread> listOfBreads = new LinkedList<>();
    int sumTrain = 0;
    int totalTrainHas = 0;

    public BreadOptimizer(HashMap<String, Integer> breadHM, List<Integer> goal) {
        this.breadHM = breadHM;
        creatingBread(breadHM);
        listPQ = new PriorityQueue<>(100, new TrainingListComparator());
        calculateTrainRequired(goal.get(0),goal.get(1),goal.get(2),goal.get(3));
    }

    private  void creatingBread(HashMap<String, Integer> breadHM){
        for(String breadName : breadHM.keySet()) {
            for(int i = 0; i < breadHM.get(breadName); i++){
                Bread bread = new Bread(breadName);
                listOfBreads.add(bread);
                totalTrainHas += bread.getTrain();
            }
        }
    }
    public TrainingList optimize(Integer startStar, Integer endStar) {
        if(sumTrain <= totalTrainHas){
            return null;
        } else {
            return optimize1(startStar, endStar);
        }
    }

    private TrainingList optimize1(Integer startStar, Integer endStar){
        for(String bread: breadHM.keySet()){
            HashMap<String, Integer> newHM = new HashMap<>(breadHM);
            newHM.put(bread, newHM.get(bread) - 1);
            TrainingList newTrainList = new TrainingList(startStar, newHM, new BreadList(startStar, bread));
            listPQ.add(newTrainList);
        }

        TrainingList tList;
        while(!listPQ.isEmpty()){
            tList = listPQ.poll();
            if(tList.getTrainLevel().get(tList.getCurStar()) >= trainRequired.get(tList.getCurStar())) {
                if(tList.getCurStar()== endStar){
                    return tList;
                }
                tList.setCurStar(tList.getCurStar() + 1) ;
            }
            for(String bread: tList.getAvailableBread().keySet()){
                HashMap<String, Integer> newHM = new HashMap<>(tList.getAvailableBread());
                newHM.put(bread, newHM.get(bread) - 1);
                TrainingList newTrainList = new TrainingList(startStar, newHM, new BreadList(startStar, bread));
                listPQ.add(newTrainList);
            }
        }
        return null;
    }

    public void calculateTrainRequired(Integer startStar, Integer endStar, Integer startTrain, Integer endTrain){
        for (int i = startStar; i <= endStar; i++) {
            if (i == startStar) {
                trainRequired.set(i, train.get(i) - startTrain);
                sumTrain += train.get(i) - startTrain;
            } else if (i == endStar) {
                trainRequired.set(i,  endTrain);
                sumTrain += endTrain;
            } else {
                trainRequired.set(i, train.get(i));
                sumTrain  = train.get(i);
            }
        }
    }


}

class TrainingListComparator implements Comparator<TrainingList> {
    @Override
    public int compare(TrainingList lhs, TrainingList rhs) {
        return lhs.getCost() - rhs.getCost();
    }
}

