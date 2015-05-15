package cal.bread.dan.breadcalculator2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.LinkedHashMap;
import java.util.Set;


public class MainActivity extends ActionBarActivity {
    // A HashMap containing the percentage of each bread
    public LinkedHashMap<String, Integer> percentHM;
    // A HashMap containing the amount of training for breads
    public LinkedHashMap<String, Integer> trainHM;
    // A HashMap containing the level for breads;
    public LinkedHashMap<String, Integer> starHM;
    // A HashMap containing the number of available breads
    private LinkedHashMap<String, Integer> breadHM;
    // SharedPreferences for storing the number of available breads
    private SharedPreferences sharedPref;
    // Editor for the SharedPreference for editing the amount of available breads
    private SharedPreferences.Editor editor;
    // The ImageButtons for Increment Arrow and Decrement Arrow for each bread
    private ImageButton macInc, macDec, hamInc, hamDec, sDonutInc, sDonutDec, sPieInc, sPieDec, pizzaInc, pizzaDec;
    private ImageButton stDonutInc, stDonutDec, creamInc, creamDec, sandInc, sandDec, sCupInc, sCupDec, choInc, choDec;
    private ImageButton cCupInc, cCupDec, bCCInc, bCCDec, cCakeInc, cCakeDec, rDonutInc, rDonutDec, croInc, croDec;
    private ImageButton sWrapInc, sWrapDec, jRollInc, jRollDec, breadInc, breadDec, hDogInc, hDogDec, cDonutInc, cDonutDec;
    private ImageButton mBreadInc, mBreadDec, sBreadInc, sBreadDec, donutInc, donutDec;
    // The TextViews for the count of currently available breads
    private TextView macCount, hamCount, sDonutCount, sPieCount, pizzaCount, cCupCount, bCCCount, cCakeCount, rDonutCount, croCount;
    private TextView stDonutCount, creamCount, sandCount, sCupCount, choCount, sWrapCount, jRollCount, breadCount, hDogCount, cDonutCount;
    private TextView mBreadCount, sBreadCount, donutCount;
    // The TextView for displaying initial amount of training, goal amount of training, initial hero level, goal hero level
    private TextView iniTrain, goalTrain, iniStar, goalStar;
    // Buttons for Reset, Optimize, and Consume
    private Button reset, optimize, consume;
    // Initial values (-1) for initial hero level, goal hero level, initial amount of training, and goal amount of training
    private int startStarInt = -1, endStarInt = -1, startTrainInt = -1, endTrainInt = -1;
    // The optimal TrainingList
    private TrainingList tList;
    // The ImageViews for the six breads in the selected list of the optimal TrainingList
    private ImageView bread1, bread2, bread3, bread4, bread5, bread0, listStar;
    // ImageButtons for selecting through lists in the TrainingList
    private ImageButton lastList, nextList;
    // ArrayList containing ImageViews for the six bread images of the selected list
    private ArrayList<ImageView> breadImages;
    // Index of the selected list of the TrainingList
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        breadHM = setupHashMap();
        // Update the HashMap containing the amount of available breads according to the SharedPreferences
        updateBreadHM();
        //Initialize all the buttons/textviews/imagebuttons etc
        initializeButtons();
        // Set the listeners all the buttons/textviews/imagebuttons etc
        setListeners();
        //Add the ImagineView of six breads of the selected list to the list
        breadImages = new ArrayList<>(6);
        breadImages.add(bread0);
        breadImages.add(bread1);
        breadImages.add(bread2);
        breadImages.add(bread3);
        breadImages.add(bread4);
        breadImages.add(bread5);


    }

    /**
     * Create an alert containing the lists of breads in the optimal TrainingList
     */
    private void printBreadAlert(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        String message = "";
        //if TrainingList exists
        if(tList != null) {
            int cost = 0;
            int train = 0;
            // For every BreadList in the TrainingList
            for (BreadList bList : tList.getLists()) {
                message += bList.getSize();
                for (String bread : bList.getBreads()) {
                    //Print bread name
                    message += "|" + bread + "|";
                }
                cost += bList.getCost();
                train += bList.getTrain();
                message +="|Cost: "+ cost + " |Train: " + train + "\n\n";
            }
        } else {
            // The optimal TrainingLIst does not exist,
            message = "Not Enough Bread to Reach";
        }
        alert.setMessage(message);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        alert.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     *      Update the HashMap containing the number of available breads for each bread according to the stored preference.
     */
    private void updateBreadHM(){
        Set<String> breads = breadHM.keySet();
        for(String name : breads) {
            breadHM.put(name, sharedPref.getInt(name,0));
        }
    }
    /**
     * Setup the HashMap to store the numbers of available breads
     */
    private LinkedHashMap<String, Integer> setupHashMap(){
        LinkedHashMap<String, Integer> breadHM = new LinkedHashMap<>();
        breadHM.put("Macaroon", 0);
        breadHM.put("Hamburger", 0);
        breadHM.put("Special Donut", 0);
        breadHM.put("Strawberry Pie", 0);
        breadHM.put("Chocolate", 0);
        breadHM.put("Pizza", 0);
        breadHM.put("Choco Cup Cake", 0);
        breadHM.put("Shamrock Cup Cake", 0);
        breadHM.put("Strawberry Donut", 0);
        breadHM.put("Cream Bread", 0);
        breadHM.put("Christmas Cake", 0);
        breadHM.put("Sandwich", 0);
        breadHM.put("Big Choco Cake", 0);
        breadHM.put("Rice Donut", 0);
        breadHM.put("Croissant", 0);
        breadHM.put("Snake Wrap", 0);
        breadHM.put("Jelly Roll", 0);
        breadHM.put("Bread", 0);
        breadHM.put("Hot Dog", 0);
        breadHM.put("Morning Bread", 0);
        breadHM.put("Choco Donut", 0);
        breadHM.put("Sausage Bread", 0);
        breadHM.put("Donut", 0);
        return breadHM;
    }

    /**
     * Initialize the Buttons, TextViews, ImageViews, and ImageButtons
     */
    private void initializeButtons(){
        consume = (Button) findViewById(R.id.consume);
        listStar = (ImageView) findViewById(R.id.listStar);
        reset = (Button) findViewById(R.id.resetButton);
        optimize = (Button) findViewById(R.id.optimize);
        nextList = (ImageButton) findViewById(R.id.nextList);
        lastList = (ImageButton) findViewById(R.id.lastList);
        bread0 = (ImageView) findViewById(R.id.bread0);
        bread1 = (ImageView) findViewById(R.id.bread1);
        bread2 = (ImageView) findViewById(R.id.bread2);
        bread3 = (ImageView) findViewById(R.id.bread3);
        bread4 = (ImageView) findViewById(R.id.bread4);
        bread5 = (ImageView) findViewById(R.id.bread5);
        iniStar = (TextView) findViewById(R.id.setIniStar);
        iniTrain = (TextView) findViewById(R.id.setIniTrain);
        goalTrain = (TextView) findViewById(R.id.setGoalTrain);
        goalStar = (TextView) findViewById(R.id.setGoalStar);
        macInc = (ImageButton) findViewById(R.id.macaroonInc);
        macDec = (ImageButton) findViewById(R.id.macaroonDec);
        macCount = (TextView) findViewById(R.id.MacaroonCount);
        macCount.setText(Integer.toString(sharedPref.getInt("Macaroon", 0)));
        hamInc = (ImageButton) findViewById(R.id.hamburgerInc);
        hamDec = (ImageButton) findViewById(R.id.hamburgerDec);
        hamCount = (TextView) findViewById(R.id.HamburgerCount);
        hamCount.setText(Integer.toString(sharedPref.getInt("Hamburger", 0)));
        sDonutInc = (ImageButton) findViewById(R.id.sDonutInc);
        sDonutDec = (ImageButton) findViewById(R.id.sDonutDec);
        sDonutCount = (TextView) findViewById(R.id.SpecialDonutCount);
        sDonutCount.setText(Integer.toString(sharedPref.getInt("Special Donut", 0)));
        sPieInc = (ImageButton) findViewById(R.id.sPieInc);
        sPieDec = (ImageButton) findViewById(R.id.sPieDec);
        sPieCount = (TextView) findViewById(R.id.StrawberryPieCount);
        sPieCount.setText(Integer.toString(sharedPref.getInt("Strawberry Pie", 0)));
        pizzaInc = (ImageButton) findViewById(R.id.pizzaInc);
        pizzaDec = (ImageButton) findViewById(R.id.pizzaDec);
        pizzaCount = (TextView) findViewById(R.id.PizzaCount);
        pizzaCount.setText(Integer.toString(sharedPref.getInt("Pizza", 0)));
        stDonutInc = (ImageButton) findViewById(R.id.stDonutInc);
        stDonutDec = (ImageButton) findViewById(R.id.stDonutDec);
        stDonutCount = (TextView) findViewById(R.id.StrawberryDonutCount);
        stDonutCount.setText(Integer.toString(sharedPref.getInt("Strawberry Donut", 0)));
        creamInc = (ImageButton) findViewById(R.id.cBreadInc);
        creamDec = (ImageButton) findViewById(R.id.cBreadDec);
        creamCount = (TextView) findViewById(R.id.CreamBreadCount);
        creamCount.setText(Integer.toString(sharedPref.getInt("Cream Bread", 0)));
        sandInc = (ImageButton) findViewById(R.id.sandwichInc);
        sandDec = (ImageButton) findViewById(R.id.sandiwchDec);
        sandCount = (TextView) findViewById(R.id.SandwichCount);
        sandCount.setText(Integer.toString(sharedPref.getInt("Sandwich", 0)));
        sCupInc = (ImageButton) findViewById(R.id.sCupCakeInc);
        sCupDec = (ImageButton) findViewById(R.id.sCupCakeDec);
        sCupCount = (TextView) findViewById(R.id.ShamrockCupCakeCount);
        sCupCount.setText(Integer.toString(sharedPref.getInt("Shamrock Cup Cake", 0)));
        choInc = (ImageButton) findViewById(R.id.chocolateInc);
        choDec = (ImageButton) findViewById(R.id.chocolateDec);
        choCount = (TextView) findViewById(R.id.ChocolateCount);
        choCount.setText(Integer.toString(sharedPref.getInt("Chocolate", 0)));
        cCupInc = (ImageButton) findViewById(R.id.cCupCakeInc);
        cCupDec = (ImageButton) findViewById(R.id.cCupCakeDec);
        cCupCount = (TextView) findViewById(R.id.ChocoCupCakeCount);
        cCupCount.setText(Integer.toString(sharedPref.getInt("Choco Cup Cake", 0)));
        bCCInc = (ImageButton) findViewById(R.id.bCCInc);
        bCCDec = (ImageButton) findViewById(R.id.bCCDec);
        bCCCount = (TextView) findViewById(R.id.BigChocecCakeCount);
        bCCCount.setText(Integer.toString(sharedPref.getInt("Big Chocec Cake", 0)));
        cCakeInc = (ImageButton) findViewById(R.id.cCakeInc);
        cCakeDec = (ImageButton) findViewById(R.id.cCakeDec);
        cCakeCount = (TextView) findViewById(R.id.ChristmasCakeCount);
        cCakeCount.setText(Integer.toString(sharedPref.getInt("Christmas Cake", 0)));
        rDonutInc = (ImageButton) findViewById(R.id.rDonutInc);
        rDonutDec = (ImageButton) findViewById(R.id.rDonutDec);
        rDonutCount = (TextView) findViewById(R.id.RiceDonutCount);
        rDonutCount.setText(Integer.toString(sharedPref.getInt("Rice Donut", 0)));
        croInc = (ImageButton) findViewById(R.id.croInc);
        croDec = (ImageButton) findViewById(R.id.croDec);
        croCount = (TextView) findViewById(R.id.CroissantCount);
        croCount.setText(Integer.toString(sharedPref.getInt("Croissant", 0)));
        sWrapInc = (ImageButton) findViewById(R.id.sWrapInc);
        sWrapDec = (ImageButton) findViewById(R.id.sWrapDec);
        sWrapCount = (TextView) findViewById(R.id.SnackWrapCount);
        sWrapCount.setText(Integer.toString(sharedPref.getInt("Snack Wrap", 0)));
        jRollInc = (ImageButton) findViewById(R.id.jRollInc);
        jRollDec = (ImageButton) findViewById(R.id.jRollDec);
        jRollCount = (TextView) findViewById(R.id.JellyRollCount);
        jRollCount.setText(Integer.toString(sharedPref.getInt("Jelly Roll", 0)));
        breadInc = (ImageButton) findViewById(R.id.breadInc);
        breadDec = (ImageButton) findViewById(R.id.breadDec);
        breadCount = (TextView) findViewById(R.id.BreadCount);
        breadCount.setText(Integer.toString(sharedPref.getInt("Bread", 0)));
        hDogInc = (ImageButton) findViewById(R.id.hDogInc);
        hDogDec = (ImageButton) findViewById(R.id.hDogDec);
        hDogCount = (TextView) findViewById(R.id.HotDogCount);
        hDogCount.setText(Integer.toString(sharedPref.getInt("Hot Dog", 0)));
        cDonutInc = (ImageButton) findViewById(R.id.cDonutInc);
        cDonutDec = (ImageButton) findViewById(R.id.cDonutDec);
        cDonutCount = (TextView) findViewById(R.id.ChocoDonutCount);
        cDonutCount.setText(Integer.toString(sharedPref.getInt("Choco Donut", 0)));
        mBreadInc = (ImageButton) findViewById(R.id.mBreadInc);
        mBreadDec = (ImageButton) findViewById(R.id.mBreadDec);
        mBreadCount = (TextView) findViewById(R.id.MorningBreadCount);
        mBreadCount.setText(Integer.toString(sharedPref.getInt("Morning Bread", 0)));
        sBreadInc = (ImageButton) findViewById(R.id.sBreadInc);
        sBreadDec = (ImageButton) findViewById(R.id.sBreadDec);
        sBreadCount = (TextView) findViewById(R.id.SausageBreadCount);
        sBreadCount.setText(Integer.toString(sharedPref.getInt("Sausage Bread", 0)));
        donutInc = (ImageButton) findViewById(R.id.donutInc);
        donutDec = (ImageButton) findViewById(R.id.donutDec);
        donutCount = (TextView) findViewById(R.id.DonutCount);
        donutCount.setText(Integer.toString(sharedPref.getInt("Donut", 0)));

    }

    /**
     *  Display the bread images for breads in the list that is at (index)th position of the TrainingList
     * @param index : The index of the list in the TrainingLis
     */
    private void setBreadImage(int index){
        BreadList bList = tList.getLists().get(index);
        int size = bList.getSize();
        int star = bList.getStar();
        listStar.setImageResource(getResources().getIdentifier("drawable/star"+star, null, getPackageName()));
        for(int i = 0; i<size;i++){
            String bread = bList.getBread(i);
            breadImages.get(i).setImageResource(getResources().getIdentifier("drawable/"+bread.toLowerCase().replace(" ", ""), null,getPackageName()));
        }
        for(int j = size; j < 6; j++){
            breadImages.get(j).setImageResource(getResources().getIdentifier("drawable/empty", null, getPackageName()));
        }
    }

    /**
     * Set Up HashMaps for bread information (trainHM, percentHM, starHM)
     */
    private void breadHM(){
        //Setup trainHM
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
        //Setup percentHM
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

        //Setup percentHM
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

    /**
     * Get the TextView according to the bread name
     * @param name : The Bread Name
     * @return tv       : The corresponding TextView for the input bread name
     */
    private TextView getTextView(String name){
        TextView tv;
        switch(name) {
            case "Macaroon":
                tv = macCount;
                break;
            case "Strawberry Pie":
                tv = sPieCount;
                break;
            case "Cream Bread":
                tv = creamCount;
                break;
            case "Croissant":
                tv = croCount;
                break;
            case "Bread":
                tv = breadCount;
                break;
            case "Morning Bread":
                tv = mBreadCount;
                break;
            case "Chocolate":
                tv = choCount;
                break;
            case "Choco Cup Cake":
                tv = cCupCount;
                break;
            case "Sausage Bread":
                tv = sBreadCount;
                break;
            case "Hot Dog":
                tv = hDogCount;
                break;
            case "Snake Wrap":
                tv = sWrapCount;
                break;
            case "Sandwich":
                tv = sandCount;
                break;
            case "Pizza":
                tv = pizzaCount;
                break;
            case "Hamburger":
                tv = hamCount;
                break;
            case "Christmas Cake":
                tv = cCakeCount;
                break;
            case "Shamrock Cup Cake":
                tv = sCupCount;
                break;
            case "Donut":
                tv = donutCount;
                break;
            case "Choco Donut":
                tv = cDonutCount;
                break;
            case "Jelly Roll":
                tv = jRollCount;
                break;
            case "Rice Donut":
                tv = rDonutCount;
                break;
            case "Strawberry Donut":
                tv = stDonutCount;
                break;
            case "Special Donut":
                tv = sDonutCount;
                break;
            case "Big Chocec Cake":
                tv = bCCCount;
                break;
            default:
                throw new IllegalArgumentException("Invalid bread name: " + name);
        }
        return tv;
    }

    /**
     *      Create an alert with initial amount of training, goal amount of training, initial hero level, goal hero level is not set
     */
    public void optimizeAlert(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Please Enter Required Field");
        alert.setMessage("Press the text to enter Initial Train, Goal Train, Initial Star, and Goal Star for your hero.");
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.show();
    }

    /**
     * Set the listenrs for each Button, and ImageButton
     */
    private void setListeners(){
        consume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tList != null) {
                    LinkedList<BreadList> bLists = tList.getLists();
                    BreadList bList = bLists.get(index);
                    //For each bread in the list, decrement the amount of available for
                    for (String bread : bList.getBreads()) {
                        // Decrement the HashMap for available breads
                        breadHM.put(bread, breadHM.get(bread) - 1);
                        // Decrement the amount of bread in SharedPreferences
                        editor.putInt(bread, breadHM.get(bread));
                        TextView tv = getTextView(bread);
                        // Update the text containing the count of bread
                        tv.setText(Integer.toString(breadHM.get(bread)));
                    }
                    //Commit the changes to the SharedPreferences
                    editor.commit();
                    // Remove the list from the TrainingList
                    bLists.remove(index);
                    //Decrement the index if index != 0
                    if (index != 0) {
                        index--;
                    }
                    //If there still lists in the TrainingList, set the bread images correspond to
                    //breads in the next list.
                    if (tList.getLists().size() != 0) {
                        setBreadImage(index);
                    } else {
                        //The TrainingList is empty
                        //Set the bread images to be empty image holders
                        for (ImageView bImage : breadImages) {
                            bImage.setImageResource(0);
                        }
                        listStar.setImageResource(0);
                        lastList.setImageResource(0);
                        nextList.setImageResource(0);
                    }
                }
            }
        });
        nextList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Display the breads in the next list
                if(tList != null && index < tList.getLists().size() - 1) {
                    index++;
                    setBreadImage(index);
                }
            }
        });
        lastList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Display the breads in the previous list
                if(index > 0 && tList != null){
                    index--;
                    setBreadImage(index);
                }
            }
        });
        optimize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if the initial and goal values are not set, create an alert
                if(startStarInt == -1 || endStarInt == -1 || startTrainInt == -1 || endStarInt == -1) {
                   optimizeAlert();
                } else {
                    //Else start optimizing
                    updateBreadHM();
                    ArrayList<Integer> goal = new ArrayList(4);
                    goal.add(startStarInt);
                    goal.add(endStarInt);
                    goal.add(startTrainInt);
                    goal.add(endTrainInt);
                    BreadOptimizer optimizer = new BreadOptimizer(breadHM, goal);
                    tList = optimizer.optimize();
                    printBreadAlert();
                    index = 0;
                    if (tList != null) {
                        setBreadImage(index);
                    }
                    lastList.setBackgroundResource(getResources().getIdentifier("drawable/bigleftarrow", null, getPackageName()));
                    nextList.setBackgroundResource(getResources().getIdentifier("drawable/bigrightarrow", null, getPackageName()));
                }
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                breadHM = setupHashMap();
                for(String name: breadHM.keySet()){
                    editor.putInt(name, 0);
                }
                editor.commit();
                initializeButtons();
            }
        });
        macInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int macaroonCount = sharedPref.getInt("Macaroon", 0);
                macaroonCount++;
                editor.putInt("Macaroon", macaroonCount);
                editor.commit();
                macCount.setText(Integer.toString(macaroonCount));
            }
        });
        macDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int macaroonCount = sharedPref.getInt("Macaroon", 0);
                if(macaroonCount > 0) {
                    macaroonCount--;
                    editor.putInt("Macaroon", macaroonCount);
                    editor.commit();
                    macCount.setText(Integer.toString(macaroonCount));
                }
            }
        });
        hamInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = sharedPref.getInt("Hamburger", 0);
                count++;
                editor.putInt("Hamburger", count);
                editor.commit();
                hamCount.setText(Integer.toString(count));
            }
        });
        hamDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = sharedPref.getInt("Hamburger", 0);
                if(count > 0) {
                    count--;
                    editor.putInt("Hamburger", count);
                    editor.commit();
                    hamCount.setText(Integer.toString(count));
                }
            }
        });
        sDonutInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = sharedPref.getInt("Special Donut", 0);
                count++;
                editor.putInt("Special Donut", count);
                editor.commit();
                sDonutCount.setText(Integer.toString(count));
            }
        });
        sDonutDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = sharedPref.getInt("Special Donut", 0);
                if(count > 0) {
                    count--;
                    editor.putInt("Special Donut", count);
                    editor.commit();
                    sDonutCount.setText(Integer.toString(count));
                }
            }
        });
        sPieInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = sharedPref.getInt("Strawberry Pie", 0);
                count++;
                editor.putInt("Strawberry Pie", count);
                editor.commit();
                sPieCount.setText(Integer.toString(count));
            }
        });
        sPieDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = sharedPref.getInt("Strawberry Pie", 0);
                if(count > 0) {
                    count--;
                    editor.putInt("Strawberry Pie", count);
                    editor.commit();
                    sPieCount.setText(Integer.toString(count));
                }
            }
        });
        sPieInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = sharedPref.getInt("Strawberry Pie", 0);
                count++;
                editor.putInt("Strawberry Pie", count);
                editor.commit();
                sPieCount.setText(Integer.toString(count));
            }
        });
        sPieDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = sharedPref.getInt("Strawberry Pie", 0);
                if(count > 0) {
                    count--;
                    editor.putInt("Strawberry Pie", count);
                    editor.commit();
                    sPieCount.setText(Integer.toString(count));
                }
            }
        });
        pizzaInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = sharedPref.getInt("Pizza", 0);
                count++;
                editor.putInt("Pizza", count);
                editor.commit();
                pizzaCount.setText(Integer.toString(count));
            }
        });
        pizzaDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = sharedPref.getInt("Pizza", 0);
                if (count > 0) {
                    count--;
                    editor.putInt("Pizza", count);
                    editor.commit();
                    pizzaCount.setText(Integer.toString(count));
                }
            }
        });
        stDonutInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = sharedPref.getInt("Strawberry Donut", 0);
                count++;
                editor.putInt("Strawberry Donut", count);
                editor.commit();
                stDonutCount.setText(Integer.toString(count));
            }
        });
        stDonutDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = sharedPref.getInt("Strawberry Donut", 0);
                if (count > 0) {
                    count--;
                    editor.putInt("Strawberry Donut", count);
                    editor.commit();
                    stDonutCount.setText(Integer.toString(count));
                }
            }
        });
        creamInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = sharedPref.getInt("Cream Bread", 0);
                count++;
                editor.putInt("Cream Bread", count);
                editor.commit();
                creamCount.setText(Integer.toString(count));
            }
        });
        creamDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = sharedPref.getInt("Cream Bread", 0);
                if (count > 0) {
                    count--;
                    editor.putInt("Cream Bread", count);
                    editor.commit();
                    creamCount.setText(Integer.toString(count));
                }
            }
        });
        sandInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = sharedPref.getInt("Sandwich", 0);
                count++;
                editor.putInt("Sandwich", count);
                editor.commit();
                sandCount.setText(Integer.toString(count));
            }
        });
        sandDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = sharedPref.getInt("Sandwich", 0);
                if (count > 0) {
                    count--;
                    editor.putInt("Sandwich", count);
                    editor.commit();
                    sandCount.setText(Integer.toString(count));
                }
            }
        });
        sCupInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = sharedPref.getInt("Shamrock Cup Cake", 0);
                count++;
                editor.putInt("Shamrock Cup Cake", count);
                editor.commit();
                sCupCount.setText(Integer.toString(count));
            }
        });
        sCupDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = sharedPref.getInt("Shamrock Cup Cake", 0);
                if (count > 0) {
                    count--;
                    editor.putInt("Shamrock Cup Cake", count);
                    editor.commit();
                    sCupCount.setText(Integer.toString(count));
                }
            }
        });
        choInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = sharedPref.getInt("Chocolate", 0);
                count++;
                editor.putInt("Chocolate", count);
                editor.commit();
                choCount.setText(Integer.toString(count));
            }
        });
        choDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = sharedPref.getInt("Chocolate", 0);
                if (count > 0) {
                    count--;
                    editor.putInt("Chocolate", count);
                    editor.commit();
                    choCount.setText(Integer.toString(count));
                }
            }
        });
        cCupInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = sharedPref.getInt("Choco Cup Cake", 0);
                count++;
                editor.putInt("Choco Cup Cake", count);
                editor.commit();
                cCupCount.setText(Integer.toString(count));
            }
        });
        cCupDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = sharedPref.getInt("Choco Cup Cake", 0);
                if (count > 0) {
                    count--;
                    editor.putInt("Choco Cup Cake", count);
                    editor.commit();
                    cCupCount.setText(Integer.toString(count));
                }
            }
        });
        bCCInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = sharedPref.getInt("Big Chocec Cake", 0);
                count++;
                editor.putInt("Big Chocec Cake", count);
                editor.commit();
                bCCCount.setText(Integer.toString(count));
            }
        });
        bCCDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = sharedPref.getInt("Big Chocec Cake", 0);
                if (count > 0) {
                    count--;
                    editor.putInt("Big Chocec Cake", count);
                    editor.commit();
                    bCCCount.setText(Integer.toString(count));
                }
            }
        });
        cCakeInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = sharedPref.getInt("Christmas Cake", 0);
                count++;
                editor.putInt("Christmas Cake", count);
                editor.commit();
                cCakeCount.setText(Integer.toString(count));
            }
        });
        cCakeDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = sharedPref.getInt("Christmas Cake", 0);
                if (count > 0) {
                    count--;
                    editor.putInt("Christmas Cake", count);
                    editor.commit();
                    cCakeCount.setText(Integer.toString(count));
                }
            }
        });
        rDonutInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = sharedPref.getInt("Rice Donut", 0);
                count++;
                editor.putInt("Rice Donut", count);
                editor.commit();
                rDonutCount.setText(Integer.toString(count));
            }
        });
        rDonutDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = sharedPref.getInt("Rice Donut", 0);
                if (count > 0) {
                    count--;
                    editor.putInt("Rice Donut", count);
                    editor.commit();
                    rDonutCount.setText(Integer.toString(count));
                }
            }
        });
        croInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = sharedPref.getInt("Croissant", 0);
                count++;
                editor.putInt("Croissant", count);
                editor.commit();
                croCount.setText(Integer.toString(count));
            }
        });
        croDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = sharedPref.getInt("Croissant", 0);
                if (count > 0) {
                    count--;
                    editor.putInt("Croissant", count);
                    editor.commit();
                    croCount.setText(Integer.toString(count));
                }
            }
        });
        sWrapInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = sharedPref.getInt("Snack Wrap", 0);
                count++;
                editor.putInt("Snack Wrap", count);
                editor.commit();
                sWrapCount.setText(Integer.toString(count));
            }
        });
        sWrapDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = sharedPref.getInt("Snack Wrap", 0);
                if (count > 0) {
                    count--;
                    editor.putInt("Snack Wrap", count);
                    editor.commit();
                    sWrapCount.setText(Integer.toString(count));
                }
            }
        });
        jRollInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = sharedPref.getInt("Jelly Roll", 0);
                count++;
                editor.putInt("Jelly Roll", count);
                editor.commit();
                jRollCount.setText(Integer.toString(count));
            }
        });
        jRollDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = sharedPref.getInt("Jelly Roll", 0);
                if (count > 0) {
                    count--;
                    editor.putInt("Jelly Roll", count);
                    editor.commit();
                    jRollCount.setText(Integer.toString(count));
                }
            }
        });
        breadInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = sharedPref.getInt("Bread", 0);
                count++;
                editor.putInt("Bread", count);
                editor.commit();
                breadCount.setText(Integer.toString(count));
            }
        });
        breadDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = sharedPref.getInt("Bread", 0);
                if (count > 0) {
                    count--;
                    editor.putInt("Bread", count);
                    editor.commit();
                    breadCount.setText(Integer.toString(count));
                }
            }
        });
        hDogInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = sharedPref.getInt("Hot Dog", 0);
                count++;
                editor.putInt("Hot Dog", count);
                editor.commit();
                hDogCount.setText(Integer.toString(count));
            }
        });
        hDogDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = sharedPref.getInt("Hot Dog", 0);
                if (count > 0) {
                    count--;
                    editor.putInt("Hot Dog", count);
                    editor.commit();
                    hDogCount.setText(Integer.toString(count));
                }
            }
        });
        cDonutInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = sharedPref.getInt("Choco Donut", 0);
                count++;
                editor.putInt("Choco Donut", count);
                editor.commit();
                cDonutCount.setText(Integer.toString(count));
            }
        });
        cDonutDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = sharedPref.getInt("Choco Donut", 0);
                if (count > 0) {
                    count--;
                    editor.putInt("Choco Donut", count);
                    editor.commit();
                    cDonutCount.setText(Integer.toString(count));
                }
            }
        });
        mBreadInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = sharedPref.getInt("Morning Bread", 0);
                count++;
                editor.putInt("Morning Bread", count);
                editor.commit();
                mBreadCount.setText(Integer.toString(count));
            }
        });
        mBreadDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = sharedPref.getInt("Morning Bread", 0);
                if (count > 0) {
                    count--;
                    editor.putInt("Morning Bread", count);
                    editor.commit();
                    mBreadCount.setText(Integer.toString(count));
                }
            }
        });
        sBreadInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = sharedPref.getInt("Sausage Bread", 0);
                count++;
                editor.putInt("Sausage Bread", count);
                editor.commit();
                sBreadCount.setText(Integer.toString(count));
            }
        });
        sBreadDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = sharedPref.getInt("Sausage Bread", 0);
                if (count > 0) {
                    count--;
                    editor.putInt("Sausage Bread", count);
                    editor.commit();
                    sBreadCount.setText(Integer.toString(count));
                }
            }
        });
        donutInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = sharedPref.getInt("Donut", 0);
                count++;
                editor.putInt("Donut", count);
                editor.commit();
                donutCount.setText(Integer.toString(count));
            }
        });
        donutDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = sharedPref.getInt("Donut", 0);
                if (count > 0) {
                    count--;
                    editor.putInt("Donut", count);
                    editor.commit();
                    donutCount.setText(Integer.toString(count));
                }
            }
        });
    }
    public void setInitialTrain(final View v){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Set Initial Train");
        alert.setMessage("Enter the initial Train for the hero");
        final EditText inputText = new EditText(this);
        inputText.setInputType(0x00000002);
        alert.setView(inputText);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String value = inputText.getText().toString();
                try {
                    startTrainInt = Integer.parseInt(value);
                    TextView tv = (TextView) v;
                    tv.setText("Initial Train : " + Integer.toString(startTrainInt));
                } catch (NumberFormatException nfe) {

                }
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alert.show();
    }
    public void setGoalTrain(final View v){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Set Goal Train");
        alert.setMessage("Enter the Goal Train for the hero");
        final EditText inputText = new EditText(this);
        inputText.setInputType(0x00000002);
        alert.setView(inputText);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            final String value = inputText.getText().toString();
            try {
                endTrainInt = Integer.parseInt(value);
                TextView tv = (TextView) v;
                tv.setText("Goal Train   : " + Integer.toString(endTrainInt));
            } catch (NumberFormatException nfe){

            }
        }
    });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alert.show();
    }
    public void setInitialStar(final View v){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Set Initial Star");
        alert.setMessage("Enter the initial star of the hero");
        final EditText inputText = new EditText(this);
        alert.setView(inputText);
        inputText.setFilters(new InputFilter[]{new InputFilterMinMax(1, 6)});
        inputText.setInputType(0x00000002);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String value = inputText.getText().toString();
                try{
                    startStarInt = Integer.parseInt(value);
                    TextView tv = (TextView) v;
                    tv.setText("Initial Star : " + Integer.toString(startStarInt));
                } catch (NumberFormatException nfe){

                }
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alert.show();

    }
    public void setGoalStar(final View v){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Set Goal Star");
        alert.setMessage("Enter the Goal star of the hero");
        final EditText inputText = new EditText(this);
        alert.setView(inputText);
        inputText.setFilters(new InputFilter[]{new InputFilterMinMax(1, 6)});
        inputText.setInputType(0x00000002);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String value = inputText.getText().toString();
                try {
                    endStarInt = Integer.parseInt(value);
                    TextView tv = (TextView) v;
                    tv.setText("Goal Star   : " + Integer.toString(endStarInt));
                } catch (NumberFormatException nfe) {

                }
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alert.show();
    }
}
