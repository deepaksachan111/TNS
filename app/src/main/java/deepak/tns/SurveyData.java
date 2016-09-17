package deepak.tns;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class SurveyData extends AppCompatActivity {

    List<Question> quesList  = new ArrayList<>();

    String fsdff;
    //List<Question> questionList = new ArrayList<>();

    List<String> d = new ArrayList<>();
    int score = 0;
    int qid = 0;
    Question currentQ;
    TextView txtQuestion;
    RadioButton rda, rdb, rdc;
TextView gfhfhf;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_data);

        DbHelper db=new DbHelper(this);
        quesList=db.getAllQuestions();

        String name = getIntent().getStringExtra("name");
        int pos =getIntent().getIntExtra("pos", 0);

        TextView txtview = (TextView)findViewById(R.id.servey_loc);
        ImageView back = (ImageView)findViewById(R.id.servey_home);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtview.setText(name);
      //  gfhfhf =(TextView)findViewById(R.id.textssss);
        currentQ=quesList.get(pos);

        txtQuestion=(TextView)findViewById(R.id.textView1);
        rda=(RadioButton)findViewById(R.id.radio0);
        rdb=(RadioButton)findViewById(R.id.radio1);
        rdc=(RadioButton)findViewById(R.id.radio2);

        setQuestionView();


    }


    @Override
    protected void onResume() {

        SiteInformationActivity.BUS.register(this);
        super.onResume();
    }

    @Subscribe
    public void hello(ArrayList<Daats> ss ){

        for( Daats s : ss){
            Toast.makeText(getApplicationContext(),s.getName(),Toast.LENGTH_SHORT).show();
        }

       // Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }

    private void setQuestionView()
    {
           Question d = currentQ;
        txtQuestion.setText(currentQ.getQUESTION());
        rda.setText(currentQ.getOPTA());
        rdb.setText(currentQ.getOPTB());
        rdc.setText(currentQ.getOPTC());
      //  qid++;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_survey_data, menu);
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
