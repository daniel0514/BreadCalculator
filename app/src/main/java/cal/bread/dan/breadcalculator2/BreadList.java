package cal.bread.dan.breadcalculator2;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Dan on 04/05/2015.
 */
public class BreadList {
    LinkedList<Bread> breadList;
    int star;
    int size = 0;
    int cost = 0;
    int totalPercentage = 0;
    int totalTrain = 0;
    List<Integer> levelCost = Arrays.asList(0, 0, 600, 1300, 1900, 3200, 4900);

    public BreadList(int star){
        this.star = star;
        breadList = new LinkedList<Bread>();
    }
    public BreadList(int star, Bread bread){
        this.star = star;
        breadList = new LinkedList<>();
        breadList.add(bread);
        totalPercentage += bread.getPercentage();
        totalTrain += bread.getTrain();
        cost += levelCost.get(star);
        size = 1;
    }
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
    public void addBread(Bread bread) throws ListFullException{
        if(size < 6) {
            breadList.add(bread);
            totalPercentage += bread.getPercentage();
            cost += levelCost.get(star);
            totalTrain += bread.getTrain();
            size++;
        } else {
            throw new ListFullException();
        }
    }
    public void removeBreadIndex(int index) {
        if(size >= 1) {
            Bread removed = breadList.remove(index);
            cost -= levelCost.get(star);
            totalPercentage -= removed.getPercentage();
            totalTrain -= removed.getTrain();
            size--;
        }
    }
    public void removeBreadName(String name) {
        if(size >= 1) {
            for (int i = 0; i < size; i++) {
                Bread bread = breadList.get(i);
                if (name.equals(bread.getName())) {
                    Bread removed = breadList.remove(i);
                    cost -= levelCost.get(star);
                    totalPercentage -= removed.getPercentage();
                    totalTrain -= removed.getTrain();
                    size--;
                    break;
                }
            }
        }
    }
    public void removeBread(Bread removeBread) {
        String removeName = removeBread.getName();
        if(size >= 1) {
            for (int i = 0; i < size; i++) {
                Bread bread = breadList.get(i);
                if (removeName.equals(bread.getName())) {
                    Bread removed = breadList.remove(i);
                    cost -= levelCost.get(star);
                    totalPercentage -= removed.getPercentage();
                    totalTrain -= removed.getTrain();
                    size--;
                    break;
                }
            }
        }
    }
    public Bread getBread(int index){
        return breadList.get(index);
    }

    public int getTrain(){
        if(totalPercentage < 100){
            return totalTrain;
        } else {
            return (int) (totalTrain * 1.5);
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

}
