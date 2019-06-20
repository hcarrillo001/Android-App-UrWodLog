package com.carrillo.urwodlog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ContactUsActivity extends AppCompatActivity {

    private EditText emailFld = null;
    private EditText phoneFld = null;
    private EditText commentFld = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        emailFld = (EditText)findViewById(R.id.emailAddressEditText);
        phoneFld = (EditText)findViewById(R.id.phoneNumberEditText);
        commentFld =(EditText)findViewById(R.id.commentsEditText);

    }


    public void onSubmitButton(View view){
            if(validFields()){
                Toast.makeText(this,"Thank you for your feedback", Toast.LENGTH_LONG).show();
                finish();
            }else{
                Toast.makeText(this,"All fields are mandatory", Toast.LENGTH_LONG).show();
            }


    }

    public void onCancelButton(View view){
        Toast.makeText(this,"Cancelled",Toast.LENGTH_LONG).show();
        finish();
    }


    public boolean validFields(){
        if(emailFld.getText().toString().equals("")){
            return false;
        }
        if(phoneFld.getText().toString().equals("")){
            return false;
        }
        if(commentFld.getText().toString().equals("")){
            return false;
        }

        return true;
    }



}
