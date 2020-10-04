package com.it190988.payment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.it190988.*;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity {

    EditText txtPID,txtcardNum,txtexpDate,txtcvvNum,txtcardHolder;
    private Button addPayBtn;

    DatabaseReference dbRef;

    payment_details payDetail;
    AwesomeValidation awesomeValidation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtcardNum = findViewById(R.id.etInputcardNum);
        txtexpDate = findViewById(R.id.etInputDate);
        txtcvvNum = findViewById(R.id.etInputCvv);
        txtcardHolder = findViewById(R.id.etInputCardHolderName);

        addPayBtn = (Button) findViewById(R.id.payBtn);

        payDetail = new payment_details();

        //initalize validation style
        awesomeValidation=new AwesomeValidation(ValidationStyle.BASIC);

        //Add validation for cardNumber
        awesomeValidation.addValidation(this,R.id.etInputcardNum, RegexTemplate.NOT_EMPTY,R.string.invalid_number);
        //Add validation
        awesomeValidation.addValidation(this,R.id.etInputDate, RegexTemplate.NOT_EMPTY,R.string.invalid_number);
        awesomeValidation.addValidation(this,R.id.etInputCvv, RegexTemplate.NOT_EMPTY,R.string.invalid_number);
        awesomeValidation.addValidation(this,R.id.etInputCvv, RegexTemplate.NOT_EMPTY,R.string.invalid_number);
        awesomeValidation.addValidation(this,R.id.etInputCardHolderName, RegexTemplate.NOT_EMPTY,R.string.invalid_number);



        addPayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check validation
                if(awesomeValidation.validate()){
                    //on success
                    Toast.makeText(getApplicationContext(),"form validate sucessful",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"validation failed",Toast.LENGTH_SHORT).show();
                }

                dbRef = FirebaseDatabase.getInstance().getReference().child("payment_details");
                try {
                    if (TextUtils.isEmpty(txtcardNum.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Empty Number", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtexpDate.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Empty expiry date", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtcvvNum.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Empty cvv", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtcardHolder.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Empty card holderName", Toast.LENGTH_SHORT).show();
                    else {
                        payDetail.setCardNum(Integer.parseInt(txtcardNum.getText().toString().trim()));
                        payDetail.setDate(txtexpDate.getText().toString().trim());
                        payDetail.setCvv(Integer.parseInt(txtcvvNum.getText().toString().trim()));
                        payDetail.setDate(txtcardHolder.getText().toString().trim());
                        dbRef.child("Payment1").setValue(payDetail);
                        Toast.makeText(getApplicationContext(), "Sucessfully inserted", Toast.LENGTH_SHORT).show();
                        int data1=Integer.parseInt(txtcardNum.getText().toString());
                        int data2=Integer.parseInt(txtexpDate.getText().toString());
                        int data3=Integer.parseInt(txtcvvNum.getText().toString());
                        String data4=txtcardHolder.getText().toString();

                        Intent i=new Intent(getApplicationContext(),payment2.class);
                        clearControls();

                        i.putExtra("cardno",data1);
                        i.putExtra("date",data2);
                        i.putExtra("cvv",data3);
                        i.putExtra("holderName",data4);

                        startActivity(i);

                    }
                } catch (NumberFormatException nfe) {
                    Toast.makeText(getApplicationContext(), "Successfully inserted", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }





    private void clearControls(){
        txtcardNum.setText("");
        txtexpDate.setText("");
        txtcvvNum.setText("");
        txtcardHolder.setText("");
    }
//    public void openpayment2(){
//        Intent intent=new Intent(this,payment2.class);
//        startActivity(intent);
//    }


}

