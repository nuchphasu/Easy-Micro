package stars.v.nuchphasu.easymicro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ExpandedMenuView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class ServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        ListView listView = (ListView) findViewById(R.id.livUser);

        final String[] nameStrings = getIntent().getStringArrayExtra("Name");
        String[] imageStrings = getIntent().getStringArrayExtra("Image");

        MyAdapter myAdapter = new MyAdapter(ServiceActivity.this, nameStrings, imageStrings);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ServiceActivity.this,
                        "You click " + nameStrings[i],
                        Toast.LENGTH_LONG).show();
            }
        });



    }//Main Method
}//Main Class
