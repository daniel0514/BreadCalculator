package cal.bread.dan.breadcalculator2;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageButton;

import java.util.HashMap;
import java.util.Set;


public class MainActivity extends ActionBarActivity {
    HashMap<String, Integer> breadHM;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    private ImageButton macInc, macDec, hamInc, hamDec, sDonutInc, sDonutDec, sPieInc, sPieDec, pizzaInc, pizzaDec;
    private ImageButton stDonutInc, stDonutDec, creamInc, creamDec, sandInc, sandDec, sCupInc, sCupDec, choInc, choDec;
    private ImageButton cCupInc, cCupDec, bCCInc, bCCDec, cCakeInc, cCakeDec, rDonutInc, rDonutDec, croInc, croDec;
    private ImageButton sWrapInc, sWrapDec, jRollInc, jRollDec, breadInc, breadDec, hDogInc, hDogDec, cDonutInc, cDonutDec;
    private ImageButton mBreadInc, mBreadDec, sBreadInc, sBreadDec, donutInc, donutDec;
    TextView macCount, hamCount, sDonutCount, sPieCount, pizzaCount, cCupCount, bCCCount, cCakeCount, rDonutCount, croCount;
    TextView stDonutCount, creamCount, sandCount, sCupCount, choCount, sWrapCount, jRollCount, breadCount, hDotCount, cDonutCount;
    TextView mBreadCount, sBreadCount, donutCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        breadHM = setupHashMap();
        updateBreadHM(breadHM);
        initializeButtons();
        setListeners();

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
    private void initializeButtons(){
        macInc = (ImageButton) findViewById(R.id.macaroonInc);
        macDec = (ImageButton) findViewById(R.id.macaroonDec);
        macCount = (TextView) findViewById(R.id.macaroonCount);
        hamInc = (ImageButton) findViewById(R.id.hamburgerInc);
        hamDec = (ImageButton) findViewById(R.id.hamburgerDec);
        hamCount = (TextView) findViewById(R.id.hamburgerCount);
        sDonutInc = (ImageButton) findViewById(R.id.sDonutInc);
        sDonutDec = (ImageButton) findViewById(R.id.sDonutDec);
        sDonutCount = (TextView) findViewById(R.id.sDonutCount);
        sPieInc = (ImageButton) findViewById(R.id.sPieInc);
        sPieDec = (ImageButton) findViewById(R.id.sPieDec);
        sPieCount = (TextView) findViewById(R.id.sPieCount);
        pizzaInc = (ImageButton) findViewById(R.id.pizzaInc);
        pizzaDec = (ImageButton) findViewById(R.id.pizzaDec);
        pizzaCount = (TextView) findViewById(R.id.pizzaCount);
        stDonutInc = (ImageButton) findViewById(R.id.stDonutInc);
        stDonutDec = (ImageButton) findViewById(R.id.stDonutDec);
        stDonutCount = (TextView) findViewById(R.id.stDonutCount);
        creamInc = (ImageButton) findViewById(R.id.cBreadInc);
        creamDec = (ImageButton) findViewById(R.id.cBreadDec);
        creamCount = (TextView) findViewById(R.id.cBreadCount);
        sandInc = (ImageButton) findViewById(R.id.sandwichInc);
        sandDec = (ImageButton) findViewById(R.id.sandiwchDec);
        sandCount = (TextView) findViewById(R.id.sandwichCount);
        sCupInc = (ImageButton) findViewById(R.id.sCupCakeInc);
        sCupDec = (ImageButton) findViewById(R.id.sCupCakeDec);
        sCupCount = (TextView) findViewById(R.id.sCupCakeCount);
        choInc = (ImageButton) findViewById(R.id.chocolateInc);
        choDec = (ImageButton) findViewById(R.id.chocolateDec);
        choCount = (TextView) findViewById(R.id.chocolateCount);
        cCupInc = (ImageButton) findViewById(R.id.cCupCakeInc);
        cCupDec = (ImageButton) findViewById(R.id.cCupCakeDec);
        cCupCount = (TextView) findViewById(R.id.cCupCakeCount);
        bCCInc = (ImageButton) findViewById(R.id.bCCInc);
        bCCDec = (ImageButton) findViewById(R.id.bCCDec);
        bCCCount = (TextView) findViewById(R.id.bCCCount);
        cCakeInc = (ImageButton) findViewById(R.id.cCakeInc);
        cCakeDec = (ImageButton) findViewById(R.id.cCakeDec);
        cCakeCount = (TextView) findViewById(R.id.cCakeCount);
        rDonutInc = (ImageButton) findViewById(R.id.rDonutInc);
        rDonutDec = (ImageButton) findViewById(R.id.rDonutDec);
        rDonutCount = (TextView) findViewById(R.id.rDonutCount);
        croInc = (ImageButton) findViewById(R.id.croInc);
        croDec = (ImageButton) findViewById(R.id.croDec);
        croCount = (TextView) findViewById(R.id.croCount);
        sWrapInc = (ImageButton) findViewById(R.id.sWrapInc);
        sWrapDec = (ImageButton) findViewById(R.id.sWrapDec);
        sWrapCount = (TextView) findViewById(R.id.sWrapCount);
        jRollInc = (ImageButton) findViewById(R.id.jRollInc);
        jRollDec = (ImageButton) findViewById(R.id.jRollDec);
        jRollCount = (TextView) findViewById(R.id.jRollCount);
        breadInc = (ImageButton) findViewById(R.id.breadInc);
        breadDec = (ImageButton) findViewById(R.id.breadDec);
        breadCount = (TextView) findViewById(R.id.breadCount);
        hDogInc = (ImageButton) findViewById(R.id.hDogInc);
        hDogDec = (ImageButton) findViewById(R.id.hDogDec);
        hDotCount = (TextView) findViewById(R.id.hDogCount);
        cDonutInc = (ImageButton) findViewById(R.id.cDonutInc);
        cDonutDec = (ImageButton) findViewById(R.id.cDonutDec);
        cDonutCount = (TextView) findViewById(R.id.cDonutCount);
        mBreadInc = (ImageButton) findViewById(R.id.mBreadInc);
        mBreadDec = (ImageButton) findViewById(R.id.mBreadDec);
        mBreadCount = (TextView) findViewById(R.id.mBreadCount);
        sBreadInc = (ImageButton) findViewById(R.id.sBreadInc);
        sBreadDec = (ImageButton) findViewById(R.id.sBreadDec);
        sBreadCount = (TextView) findViewById(R.id.sBreadCount);
        donutInc = (ImageButton) findViewById(R.id.donutInc);
        donutDec = (ImageButton) findViewById(R.id.donutDec);
        donutCount = (TextView) findViewById(R.id.donutCount);

    }

    private void setListeners(){
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
                    editor.putInt("Macaroon", count);
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
                    sandCount.setText(Integer.toString(count));
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
                hDotCount.setText(Integer.toString(count));
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
                    hDotCount.setText(Integer.toString(count));
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
}
