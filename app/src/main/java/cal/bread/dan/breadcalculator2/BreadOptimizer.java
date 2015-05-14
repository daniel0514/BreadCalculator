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
 *      An optimizer that search through all possible combinations of Bread Lists (TrainingList)
 *      and returns the optimal (lowest cost) bread lists
 *          listPQ      :    a priority queue for the Lowest Cost First Search Algorithm
 *          breadHM: HashMap containing the initial amount of available breads
 *                  String : The name of the bread
 *                  Integer: The amount of available bread
 *           train      : a list containing the maximum amount of training required to advance a level for heroes
 *           trainRequired: the actual amount of training required for the hero
 *           startStar  : the initial level of the hero
 *           endStar   : the goal level of the hero
 *           startTrain:    the amount of initial training for the hero at initial level
 *           endTrain:     the goal amount of training for the hero at the goal level
 */
public class BreadOptimizer {
    PriorityQueue<TrainingList> listPQ;
    LinkedHashMap<String, Integer> breadHM;
    List<Integer> train = Arrays.asList(0, 0, 100, 800, 2900, 7900, 18500);
    List<Integer> trainRequired = new ArrayList<>(Collections.nCopies(7, 0));
    int startStar, endStar, startTrain, endTrain;

    /**
     *  The contructor of the optimizer
     * @param breadHM  :  HashMap containing the initial amount of available breads
     *                                    String : The name of the bread
     *                                    Integer: The amount of available bread
     * @param goal          : an ArrayList containing the objectives of the optimizer
     *                                      Index 0 : Initial Level
     *                                               1 : Goal Level
     *                                               2 : Initial Training
     *                                               3 : Goal Training
     */
    public BreadOptimizer(LinkedHashMap<String, Integer> breadHM, ArrayList<Integer> goal) {
        this.breadHM = breadHM;
        listPQ = new PriorityQueue<>(100, new TrainingListComparator());
        startStar = goal.get(0);
        endStar = goal.get(1);
        startTrain = goal.get(2);
        endTrain = goal.get(3);
        //Calculate the amount of training required based on the objectives
        calculateTrainRequired();
    }

    /**
     *      Select different optimization patterns
     * @return TrainingList : the optimal combination of bread lists
     */
    public TrainingList optimize() {
        return optimize1();
    }

    /**
     *  Find the optimal combination of bread lists based on available breads
     * @return TrainingList: the optiaml combination of bread lists
     */
    private TrainingList optimize1(){
        /**
         * Putting initial TraingLists into the queue for each of the available breads
         */
        for(String bread: breadHM.keySet()){
            if(breadHM.get(bread) > 0) {
                LinkedHashMap<String, Integer> newHM = new LinkedHashMap<>(breadHM);
                //Decrement the number of available bread
                newHM.put(bread, newHM.get(bread) - 1);
                TrainingList newTrainList = new TrainingList(startStar, newHM, new BreadList(startStar, bread));
                listPQ.add(newTrainList);
            }
        }

        TrainingList tList;
        while(!listPQ.isEmpty()){
            /**
             *   incStar: True when hero gets enough traning for current level and is ready for calculation for the next level
             *               False otherwise
             */
            boolean incStar = false;
            tList = listPQ.poll();
            // if the amount of Training for the TrainingList is greater than required training for current level
            if(tList.getTrainLevel().get(tList.getCurStar()) >= trainRequired.get(tList.getCurStar())) {
                //If the current level is the goal level
                if(tList.getCurStar()== endStar){
                    return tList;
                }
                //the TrainingList can advance in level
                incStar = true;
            }
            //For every bread
            for(String bread: tList.getAvailableBread().keySet()){
                //If the bread is available ( > 0 )
                if(tList.getAvailableBread().get(bread) > 0) {
                    // Create a new HashMap containing the copy of available breads
                    LinkedHashMap<String, Integer> newHM = new LinkedHashMap<>(tList.getAvailableBread());
                    int breadCount = newHM.get(bread);
                    //Decrement the number of breads available for that bread
                    newHM.put(bread, breadCount - 1);
                    //Create a new empty list of BreadList
                    LinkedList<BreadList> newBLists = new LinkedList<>();
                    //Do a deep copy
                    for(BreadList bl: tList.getLists()){
                        newBLists.add(new BreadList(bl));
                    }
                    //If the hero have enough training for current level, create a list for the next level
                    if(incStar){
                        newBLists.add(new BreadList(tList.getCurStar() + 1, bread));
                    } else {
                        // Else add the bread to the last BreadList of the newly created list
                        try {
                            newBLists.getLast().addBread(bread);
                        } catch (ListFullException e) {
                            //If the last BreadList of the list is full, create a new one
                            newBLists.add(new BreadList(tList.getCurStar(), bread));
                        }
                    }
                    //Create a new TrainingList
                    TrainingList newTrainList = new TrainingList(tList.getCurStar(), newHM, newBLists);
                    if(incStar){
                        newTrainList.setCurStar(newTrainList.getCurStar() + 1);
                    }
                    listPQ.add(newTrainList);
                }
            }
        }
        //Ran out of TrainingLists, no possible combination will result in enough training, return NULL
        return null;
    }

    /**
     * Calculate the amount of training required for each level
     * and save it to trainRequired
     */
    public void calculateTrainRequired(){
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

/**
 *  The comparator for the priority queue
 */
class TrainingListComparator implements Comparator<TrainingList> {
    @Override
    public int compare(TrainingList lhs, TrainingList rhs) {
        int diff = lhs.getCost() - rhs.getCost();
        // if the difference in cost between the two TrainingLists is 0
        // look at the amount of training the TrainingList has.
        if(diff == 0){
            int i = 0;
            // Go through the BreadLists of TrainingList
            // The BreadList that has lower amount of training is better
            while( i < Math.min(rhs.getLists().size(), lhs.getLists().size())){
                BreadList lhsList = lhs.getLists().get(i);
                BreadList rhsList = rhs.getLists().get(i);
                if(lhsList.getTrain() != rhsList.getTrain()) {
                    return lhsList.getTrain() - rhsList.getTrain();
                }
                i++;
            }
            //BreadLists at every index has equal amount of training
            //Return 0 since the two TrainingList is equal
            return 0;
        } else {
            //The TrainingList that has a lower cost is better
            return diff;
        }
    }
}

