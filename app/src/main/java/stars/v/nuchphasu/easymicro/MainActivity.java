package stars.v.nuchphasu.easymicro;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import static stars.v.nuchphasu.easymicro.R.id.button2;
import static stars.v.nuchphasu.easymicro.R.id.image;

public class MainActivity extends AppCompatActivity {

    //Explicit การประกาศตัวแปร ใช้ Ram
    //Access / Type / Name
    //Public - Private
    //ประกาศตัวแปรแบบมี type ห้อยท้าย เช่น private string name
    private Button signInButton, signUpButton;
    private EditText userEditText, passwordEditText;
    private String userString, passwordString;
    private MyConstante myConstante;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myConstante = new MyConstante();

        //Bind Widget
        signInButton = (Button) findViewById(R.id.button);
        signUpButton = (Button) findViewById(R.id.button2);
        userEditText = (EditText) findViewById(R.id.editText4);
        passwordEditText = (EditText) findViewById(R.id.editText5);

        //SignUp controller
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));

            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Get Value From Edit Text
                userString = userEditText.getText().toString().trim();
                passwordString = passwordEditText.getText().toString().trim();

                //Check Space
                if (userString.equals("") || passwordString.equals("")) {
                    //Have Space
                    MyAlert myAlert = new MyAlert(MainActivity.this,
                            R.drawable.kon48,
                            getResources().getString(R.string.title_have_space),
                            getResources().getString(R.string.message_haveSpace));
                    myAlert.myDialog();

                } else {
                    //No Space
                    GetUser getUser = new GetUser(MainActivity.this);
                    getUser.execute(myConstante.getUrlJSoN());


                }


            }//onClick
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    } // Main Method


    private class GetUser extends AsyncTask<String, Void, String> {
        //Explicit

        private Context context;
        private String[] nameStrings, imageStrings;
        private String truePasswordString;
        private boolean aBoolean = true;

        public GetUser(Context context) {
            this.context = context;
        }//GetUser

        @Override
        protected String doInBackground(String... strings) {
            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(strings[0]).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }//do In

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("6novV3", "JSON ==> " + s);
            try {
                JSONArray jsonArray = new JSONArray(s);
                nameStrings = new String[jsonArray.length()];
                imageStrings = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    nameStrings[i] = jsonObject.getString("Name");
                    imageStrings[i] = jsonObject.getString("Image");
                    //CheckLog
                    Log.d("6novV4", "Name(" + i + ") ==> " + nameStrings[i]);
                    if (userString.equals(jsonObject.getString("User"))) {
                        aBoolean = false;
                        truePasswordString = jsonObject.getString("Password");
                    }
                }//for

                if (aBoolean) {
                    MyAlert myAlert = new MyAlert(context,
                            R.drawable.rat48,
                            "User False",
                            "No " + userString + " in my Database");
                    myAlert.myDialog();
                } else if (passwordString.equals(truePasswordString)) {
                    Toast.makeText(context, "Welcome",
                            Toast.LENGTH_SHORT).show();
                    //intent
                    Intent intent = new Intent(MainActivity.this,
                            ServiceActivity.class);
                    intent.putExtra("Name", nameStrings);
                    intent.putExtra("Image", imageStrings);
                    startActivity(intent);
                    finish(); //clear session ออก ไม่สามารถ เข้าไปที่หน้า Login พร้อมมี Username/password ค้างไว้
                } else {
                    MyAlert myAlert = new MyAlert(context,
                            R.drawable.rat48,
                            "Password False",
                            "Please Try Again Password False");
                    myAlert.myDialog();
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }//on Post

    } //Class


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}// Main Class นี้คือคลาสหลัก นะจ้า
