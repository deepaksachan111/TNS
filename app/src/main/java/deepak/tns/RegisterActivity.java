package deepak.tns;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.view.ContextThemeWrapper;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private String responce;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static   String device_id ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "OstrichSans-Heavy.otf");
        TextView tx = (TextView) findViewById(R.id.text_name);
        tx.setTypeface(typeface);
        // tx.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        // tx.setSelected(true);
        // tx.setSingleLine(true);
        sharedPreferences = getSharedPreferences("MY",0);
        editor = sharedPreferences.edit();


        TextView tv = (TextView) findViewById(R.id.text_welcome);
        Typeface typeface2 = Typeface.createFromAsset(getAssets(), "OstrichSans-Bold.otf");
        tv.setTypeface(typeface2);

        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        device_id = tm.getDeviceId();


        findViewById(R.id.button_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(RegisterActivity.this, "IME No. :  " + device_id, Toast.LENGTH_LONG).show();
                registerUserGet("http://tnssofts.com/apiservices/ReqService.svc/CheckIMEI/" + device_id);


            }
        });




    }


    private void registerUserGet(String REGISTER_URL) {


        StringRequest stringRequest = new StringRequest(Request.Method.GET,REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String resp = jsonObject.getString("CheckIMEIResult");

                            if(resp.equals("1")){
                                   editor.putString("message",device_id);
                                Log.v("Ressponceeeer........", device_id);
                                editor.commit();
                                startActivity(new Intent(RegisterActivity.this, SiteInformationActivity.class));
                                finish();
                            }
                            else
                            {

                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(RegisterActivity.this, R.style.AlertDialogCustom));

                                alertDialogBuilder.setTitle("Your Phone has not be registered");
                                alertDialogBuilder.setCancelable(true);
                                alertDialogBuilder
                                        .setMessage("IME No is not registerd")
                                        .setCancelable(false)
                                        .setPositiveButton("Go Back",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog,
                                                                        int id) {

                                                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                                        finish();
                                                    }
                                                });
                     /*                   .setNegativeButton("No",

                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog,
                                                                        int id) {
                                                        // if this button is clicked, just close
                                                        // the dialog box and do nothing
                                                        dialog.cancel();
                                                    }
                                                });
                                                */
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                            }




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //  Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this,"Internet is not working or please check your internet connection ", Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

















    public void getMethod(String url) {


        HttpClient httpClient = new DefaultHttpClient();

        HttpGet httpGet = new HttpGet(url);

        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            responce = EntityUtils.toString(httpEntity);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String postMethod(String url) {

        HttpResponse httpResponse = null;
        HttpClient httpClient = new DefaultHttpClient();
        // replace with your url
        HttpPost httpPost = new HttpPost(url);


        //Post Data
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
        nameValuePair.add(new BasicNameValuePair("type", "name"));
        nameValuePair.add(new BasicNameValuePair("deviceId", "dee@gmail.com"));
        nameValuePair.add(new BasicNameValuePair("userName", "test_user"));
        nameValuePair.add(new BasicNameValuePair("email", "deegggggggggggggggggggg@gmail.com"));
        nameValuePair.add(new BasicNameValuePair("phoneNumber", "123456789"));
        nameValuePair.add(new BasicNameValuePair("password", "1234567890"));
        nameValuePair.add(new BasicNameValuePair("profilePic", "picturePath"));


        //Encoding POST data
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //making POST request.
        try {
            httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            responce = EntityUtils.toString(httpEntity);
            // write response to log
            Log.d("Http Post Response:", responce.toString());
        } catch (ClientProtocolException e) {
            // Log exception
            e.printStackTrace();
        } catch (IOException e) {
            // Log exception
            e.printStackTrace();
        }

        return responce;
    }


    private void registerUserPost(String REGISTER_URL) {
        REGISTER_URL = "http://clients.aksinteractive.com/PlanningWale/signUp.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("type", "name");
                params.put("deviceId", "1213");
                params.put("userName", "test_user");
                params.put("email", "gopppgmail.com");
                params.put("phoneNumber", "123456789");
                params.put("password", "1234567890");
                params.put("profilePic", "picturePath");


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }






    private class RecordDisplayAsyncTask extends AsyncTask<String, Void, Void> {


        private ProgressDialog dialog = new ProgressDialog(RegisterActivity.this);



        protected void onPreExecute() {
            dialog.setMessage("Getting your data... Please wait...");
            dialog.setCancelable(true);
            dialog.show();
        }

        protected Void doInBackground(String... urls) {

            String URL = null;

            // registerUser("http://clients.aksinteractive.com/PlanningWale/signUp.php");

            //getMethod(urls[0]);


            return null;
        }


        protected void onPostExecute(String content) {
            dialog.dismiss();

            if (content.equals("")) {
                Toast.makeText(RegisterActivity.this, "Data Not found", Toast.LENGTH_LONG).show();


                //displayCountryList(content);
            } else {

                try {
                    JSONObject jsonObject = new JSONObject(content);

                    JSONArray jsonArray = jsonObject.getJSONArray("contacts");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(3);
                    JSONObject jsonPhone = jsonObject1.getJSONObject("phone");


                    String aa = jsonObject1.getString("id");
                    String aa2 = jsonObject1.getString("name");
                    String aa3 = jsonObject1.getString("email");
                    String aa4 = jsonObject1.getString("address");
                    String aa5 = jsonObject1.getString("gender");
                    String getphobne = jsonPhone.getString("mobile");
                    String gethome = jsonPhone.getString("home");
                    String office = jsonPhone.getString("office");


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
