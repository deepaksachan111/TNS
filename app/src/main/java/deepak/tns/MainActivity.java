package deepak.tns;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences("MY",0);

         String  device_id  = sharedPreferences.getString("message","");



        if(!device_id.equals("")){


            Thread thread = new Thread(){

                public void run(){

                    try {
                        Thread.sleep(2000);
                       // Log.v("Message.............", device_id);
                        startActivity(new Intent(MainActivity.this, SiteInformationActivity.class));
                        finish();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            };

            thread.start();

        }else {

            Thread thread = new Thread(){

                public void run(){

                    try {
                        Thread.sleep(2000);
                        startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                        finish();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            };

            thread.start();
        }


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
}
