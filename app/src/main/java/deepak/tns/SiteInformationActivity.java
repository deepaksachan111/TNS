package deepak.tns;

import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class SiteInformationActivity extends ListActivity {

    private    ProgressDialog pd;
  private  int PICK_IMAGE_REQUEST= 1;

  //  String []name ={"Noida","Lucknow","Kanpur","Allahabad","Delhi","Panjab","Bangalore"};
    //  ArrayList<String> listname = (ArrayList<String>) Arrays.asList(name);

    ArrayList<Integer> listimage = new ArrayList<>();

private TextView site_check_internet ;
    ImageView setuploadphoto ;
    Bitmap bitmap;
    TextView  txtsize_of_image;
    TextView kb ;
    String namess;
    /** Items entered by the user is stored in this ArrayList variable */
    ArrayList list = new ArrayList();
    ArrayList<SitelistData> sitelistDatas= new ArrayList<SitelistData>();
   int positions ;
    /** Declaring an ArrayAdapter to set items to ListView */
    ArrayAdapter adapter;
   private ListView listView;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** Setting a custom layout for the list activity */
        setContentView(R.layout.activity_site_information);
      /*  list.add("Noida");
        list.add("Lucknow");
        list.add("Kanpur");
        list.add("Allahabad");
        list.add("Delhi");
        list.add("Panjab");
        list.add("Bangalore");*/

       listimage.add(R.mipmap.n_img);
        listimage.add(R.mipmap.l_img);
        listimage.add(R.mipmap.k_img);
        listimage.add(R.mipmap.a_img);
        listimage.add(R.mipmap.d_img);
        listimage.add(R.mipmap.p_imv);
        listimage.add(R.mipmap.b_img);

       // sitelistDatas.add(new SitelistData(i,s));

       // listView =(ListView)findViewById(R.id.list);
        /** Reference to the add button of the layout main.xml */
      // Button btn = (Button) findViewById(R.id.btnAdd);
       listView = getListView();

         setuploadphoto =(ImageView)findViewById(R.id.getImage);
        txtsize_of_image =(TextView)findViewById(R.id.size_of_image);

        site_check_internet =(TextView)findViewById(R.id.site_check_internet);
         kb =(TextView)findViewById(R.id.kb);
        /** Reference to the delete button of the layout main.xml */
       // Button btnDel = (Button) findViewById(R.id.btnDel);
        findViewById(R.id.siteinfo_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.siteinfo_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("MY", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();


                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("MY",0);
        String id  = sharedPreferences.getString("message","");
        showProgressDialog();
        registerUserGet("http://tnssofts.com/apiservices/ReqService.svc/RetSite/"+id);

        /** Defining the ArrayAdapter to set items to ListView */
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);

        //adapter = new MyAdapter(this, R.layout.adapter_site_list, listimage,list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text

                positions = position;
            namess = (String) list.get(position);
              //  Toast.makeText(getApplicationContext(),
                     //   ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
                custumDialog();
            }
        });





        /** Defining a click event listener for the button "Add" */
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  EditText edit = (EditText) findViewById(R.id.txtItem);
                list.add(edit.getText().toString());
                edit.setText("");*/
                adapter.notifyDataSetChanged();
            }
        };

        /** Defining a click event listener for the button "Delete" */
      /*  View.OnClickListener listenerDel = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                *//** Getting the checked items from the listview *//*
                SparseBooleanArray checkedItemPositions = getListView().getCheckedItemPositions();
                int itemCount = getListView().getCount();

                for(int i=itemCount-1; i >= 0; i--){
                    if(checkedItemPositions.get(i)){
                        adapter.remove(list.get(i));
                    }
                }
                checkedItemPositions.clear();
                adapter.notifyDataSetChanged();
            }
        };
*/
        /** Setting the event listener for the add button */
      // btn.setOnClickListener(listener);

        /** Setting the event listener for the delete button */
       // btnDel.setOnClickListener(listenerDel);

        /** Setting the adapter to the ListView */
        setListAdapter(adapter);
        ListViewHelper.getListViewSize(listView);
  ;
    }


    private void registerUserGet(String REGISTER_URL) {


        StringRequest stringRequest = new StringRequest(Request.Method.GET,REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            int count = 0 ;
                            JSONArray jsonArray = jsonObject.getJSONArray("RetSiteResult");
                            for(int i = 0; i<jsonArray.length();i++){

                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                 String sitename = jsonObject1.getString("vSiteName");
                                list.add(sitename);

                                count ++;
                            }

                           Toast.makeText(getApplicationContext(),"No of records : "+count+"",Toast.LENGTH_LONG).show();
                            setListAdapter(adapter);
                            ListViewHelper.getListViewSize(listView);
                          //  String resp = jsonObject.getString("RetSiteResult");
                           pd.dismiss();



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //  Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                     pd.dismiss();
                        site_check_internet.setVisibility(View.VISIBLE);
                        Toast.makeText(SiteInformationActivity.this,"Internet is not working or please check your internet connection ", Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


    private void showProgressDialog(){
       pd = new ProgressDialog(SiteInformationActivity.this,R.style.StyledDialog);
        // Set progress dialog style spinner
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading.........");
        pd.setIndeterminate(false);
        // Finally, show the progress dialog
        pd.show();

        // Set the progress status zero on each button click

    }

    public void custumDialog(){
       // custom dialog

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.custom_dialog);
      //  dialog.setTitle("Title...");
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        // set the custom dialog components - text, image and button
        TextView text = (TextView) dialog.findViewById(R.id.txt_checklist);
        LinearLayout linear_checklist =(LinearLayout)dialog.findViewById(R.id.linear_checklist);

        TextView text2 = (TextView) dialog.findViewById(R.id.txt_checklistname);
        text2.setText("("+ namess + ")");
        TextView upload = (TextView) dialog.findViewById(R.id.txt_Upload);
        final TextView delete = (TextView) dialog.findViewById(R.id.txt_cloased);
        linear_checklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             // String  name = (String) listView.getItemAtPosition(positions);
                String name1 = (String) list.get(positions);
                int p = (int) listView.getItemIdAtPosition(positions);
                int fgfg = p;
                Intent intent = new Intent(SiteInformationActivity.this,SurveyData.class);
                intent.putExtra("name",name1);
                intent.putExtra("pos",p);

                startActivity(intent);
                dialog.dismiss();
            }
        });


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
                dialog.dismiss();
            }
        });

    delete.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View v){

        SparseBooleanArray checkedItemPositions = getListView().getCheckedItemPositions();
        int itemCount = getListView().getCount();

        for (int i = itemCount - 1; i >= 0; i--) {
            if (checkedItemPositions.get(i)) {
               // adapter.remove(listimage.get(i));
                adapter.remove(list.get(i));
                Toast.makeText(getApplicationContext(), "Item has been deleted", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
        }
        checkedItemPositions.clear();
        adapter.notifyDataSetChanged();
    }
    }

    );


  /*  Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
    // if button is clicked, close the custom dialog
    dialogButton.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View v){
        dialog.dismiss();
    }
    }

    );*/

    dialog.show();
}



    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
              //  setuploadphoto.setImageBitmap(bitmap);



                String value = getStringImage(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public String getStringImage(Bitmap bmp){
        int compressQuality =100;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
         compressQuality -= 5;
        bmp.compress(Bitmap.CompressFormat.JPEG, 10, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        long lengthbmp = imageBytes.length;
        long kbs = lengthbmp/1024;
        txtsize_of_image.setText(kbs +"");
        kb.setVisibility(View.VISIBLE);


        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        setuploadphoto.setImageBitmap(decodedByte);

        return encodedImage;
    }




    private static class MyAdapter extends ArrayAdapter {

        Activity aa;
        ArrayList<String> name;
        ArrayList<Integer> image;
       // int image[];
       // String name[];

        MyAdapter(Activity c,int r, ArrayList<Integer> i , ArrayList<String>  s){
            super(c,r,s);
            image = i;
            name =s;

            aa = c;
        }



        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return image;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            ViewHolder viewHolder = new ViewHolder();
            String names = name.get(position);
            int images = image.get(position);
            if(convertView == null) {

                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_site_list,null);


            }
            viewHolder.txtsn=(ImageView)convertView.findViewById(R.id.iv_adapter);
            viewHolder.plot=(TextView)convertView.findViewById(R.id.txt_adapter_name);



            viewHolder.txtsn.setImageResource(images);
            viewHolder.plot.setText(names);
           ;



            return convertView;
        }

        private   static class ViewHolder{
            ImageView txtsn;
            TextView plot;



        }
    }


    }

