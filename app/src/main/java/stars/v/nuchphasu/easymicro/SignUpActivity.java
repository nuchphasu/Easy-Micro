package stars.v.nuchphasu.easymicro;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.jibble.simpleftp.SimpleFTP;

import java.io.File;

public class SignUpActivity extends AppCompatActivity {


    //Explicit
    private EditText nameEditText, userEditText, passwordEditText;
    private ImageView imageView;
    private Button button;
    private String nameString, userString, passwordString,
            imageString, imagePathString, imageNameString;
    private Uri uri;
    private boolean aBoolean = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //Bind Widget
        nameEditText = (EditText) findViewById(R.id.editText);
        userEditText = (EditText) findViewById(R.id.editText2);
        passwordEditText = (EditText) findViewById(R.id.editText3);
        imageView = (ImageView) findViewById(R.id.imageView);
        button = (Button) findViewById(R.id.button3);

        //SignUp Controller
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get Value From EditText
                nameString = nameEditText.getText().toString().trim();
                userString = userEditText.getText().toString().trim();
                passwordString = passwordEditText.getText().toString().trim();

                //check Space
                if (nameString.equals("") ||
                        userString.equals("") ||
                        passwordString.equals("")) {
                    //have space

                    Log.d("5novV1", "Have Space");
                    MyAlert myAlert = new MyAlert(SignUpActivity.this,
                            R.drawable.doremon48,
                            getResources().getString(R.string.title_have_space),
                            getResources().getString(R.string.message_haveSpace));
                    myAlert.myDialog();


                } else if (aBoolean) {
                    //non Choose Image
                    MyAlert myAlert = new MyAlert(SignUpActivity.this,
                            R.drawable.nobita48,
                            getResources().getString(R.string.title_have_space),
                            getResources().getString(R.string.massage_ImageChoose));
                    myAlert.myDialog();

                } else {
                    //Upload to Server
                    upLoadImage();
                    upLoadString();
                }

            }// onClick
        });

        //Image Controller
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent to Other App
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "โปรดเลือกแอฟดูรูป"), 0);
            }//click
        });//onclickListener

    }// Main Method

    private class AddNewUser extends AsyncTask<String, Void, String> {
        //Explicit
        private Context context;

        public AddNewUser(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... strings) {

            try {


            } catch (Exception e) {
                Log.d("6novV2", "e doInBack ==>" + e.toString());
            }
            return null;
        }// doInBackground

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


        }// onPost
    }// AddNewUser Class

    private void upLoadString() {
        imageString = "http://swiftcodingthai.com/mic/Images" + imageNameString;

        MyConstante myConstante = new MyConstante();
        AddNewUser addNewUser = new AddNewUser(SignUpActivity.this);
        addNewUser.execute(myConstante.getUrlAddUserString());


    }// upLoadString

    private void upLoadImage() {

        //Change Policy
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy
                .Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);
        //สำหรับอนุญาตให้อัพโหลดรูปภาพได้

        try {
            MyConstante myConstante = new MyConstante();
            SimpleFTP simpleFTP = new SimpleFTP();
            simpleFTP.connect(myConstante.getHostString(),
                    myConstante.getPortAnInt(),
                    myConstante.getUserString(),
                    myConstante.getPasswordString());
            simpleFTP.bin(); // ย่อยเป็นตัวอักษร ทำให้ข้อมูล สามารถเคลื่อนย้าย ได้ง่ายขึ้น
            simpleFTP.cwd("Images"); // folder ที่ต้องการเข้าถึง
            simpleFTP.stor(new File(imagePathString));
            simpleFTP.disconnect();

            Toast.makeText(SignUpActivity.this, "Upload Image Finish",
            Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }//upLoadImage

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == 0) && (resultCode == RESULT_OK)) {
            //Result Success
            Log.d("5novV1", "Result OK");

            //Setup Choose Image to ImageView
            uri = data.getData();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                imageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //check Choosed
            aBoolean = false;

            //Find Path of Image Choose
            imagePathString = myFindPath(uri);
            Log.d("6novV1", "Path ==> " + imagePathString);

            //Find Name of Image Choose
            imageNameString = imagePathString.substring(imagePathString.lastIndexOf("/"));
            Log.d("6novV1", "Name ==> " + imageNameString);

        }//if

    }//onActivityResult

    private String myFindPath(Uri uri) {
        String result = null;
        String[] strings={MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, strings, null,null,null);
        if (cursor != null) {
            cursor.moveToFirst();
            int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            result = cursor.getString(index);
        } else {
            result = uri.getPath();
        }// if
        return result;
    }


}// Main Class
