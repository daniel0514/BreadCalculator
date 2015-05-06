package cal.bread.dan.breadcalculator2;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Set;


public class MainActivity extends ActionBarActivity {
    HashMap<String, Integer> breadHM;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        breadHM = setupHashMap();
        updateBreadHM(breadHM);

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

    private void updateBreadHM(HashMap<String, Integer> breadHM){
        Set<String> breads = breadHM.keySet();
        for(String name : breads) {
            breadHM.put(name, sharedPref.getInt(name,0));
        }
    }
    private HashMap<String, Integer> setupHashMap(){
        HashMap<String, Integer> breadHM = new HashMap<String, Integer>();
        breadHM.put("Donut", 0);
        breadHM.put("Sausage Bread", 0);
        breadHM.put("Morning Bread", 0);
        breadHM.put("Choco Donut", 0);
        breadHM.put("Hot Dog", 0);
        breadHM.put("Bread", 0);
        breadHM.put("Jelly Roll", 0);
        breadHM.put("Snake Wrap", 0);
        breadHM.put("Croissant", 0);
        breadHM.put("Rice Donut", 0);
        breadHM.put("Sandwich", 0);
        breadHM.put("Cream Bread", 0);
        breadHM.put("Strawberry Donut", 0);
        breadHM.put("Pizza", 0);
        breadHM.put("Strawberry Pie", 0);
        breadHM.put("Special Donut", 0);
        breadHM.put("Hamburger", 0);
        breadHM.put("Macaroon", 0);
        breadHM.put("Christmas Cake", 0);
        breadHM.put("Big Choco Cake", 0);
        breadHM.put("Choco Cup Cake", 0);
        breadHM.put("Chocolate", 0);
        breadHM.put("Shamrock Cup Cake", 0);
        return breadHM;
    }

}
