package com.example.sandra


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.concurrent.TimeUnit


class Verification : AppCompatActivity() {
    //val editTextCode = findViewById<EditText>(R.id.edit_code)
    lateinit var editTextCode:EditText
    var mAuth = FirebaseAuth.getInstance()
    private var mVerificationId: String? = null
    var getPhone:String ?= null

    var sharedpreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)

        getPhone = intent.getStringExtra("phone_number")

        val button2 = findViewById<Button>(R.id.button2)
        editTextCode = findViewById(R.id.edit_code)

        sendVerificationCode(getPhone!!)

        button2.setOnClickListener {
            val otp = editTextCode.text.toString()
            if (otp.isEmpty() || otp.length < 6) {
                return@setOnClickListener
            }
            verifyVerificationCode(otp)
        }

    }
    private fun sendVerificationCode(mobile: String) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            "$mobile",
            60,
            TimeUnit.SECONDS,
            this,
            mCallbacks
        )
    }


    private val mCallbacks: OnVerificationStateChangedCallbacks =
        object : OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                //Getting the code sent by SMS
                val code = phoneAuthCredential.smsCode

                //sometime the code is not detected automatically
                //in this case the code will be null
                //so user has to manually enter the code
                if (code != null) {
                    editTextCode.setText(code)
                    //verifying the code
                    verifyVerificationCode(code)
                }
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(this@Verification, e.message, Toast.LENGTH_LONG).show()
            }

            override fun onCodeSent(s: String, forceResendingToken: ForceResendingToken) {
                super.onCodeSent(s, forceResendingToken)
                mVerificationId = s
                //mResendToken = forceResendingToken
            }
        }
    private fun verifyVerificationCode(otp: String) {
        //creating the credential
        val credential = PhoneAuthProvider.getCredential(mVerificationId!!, otp)

        //signing the user
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this,
                OnCompleteListener<AuthResult?> { task ->
                    if (task.isSuccessful) {

                        val ref = FirebaseDatabase.getInstance().getReference("/data").addValueEventListener(
                            object : ValueEventListener{
                                override fun onDataChange(snapshot: DataSnapshot) {

                                    if(snapshot.exists()) {
                                        for (i in snapshot.children) {
                                            val w = i.getValue(Data::class.java)

                                            if (w!!.text == getPhone) {
                                                //verification successful we will start the profile activity


                                                sharedpreferences = getSharedPreferences(
                                                    "MyPREFERENCES",
                                                    Context.MODE_PRIVATE
                                                );
                                                val editor = sharedpreferences!!.edit();

                                                editor.putString("id", w.id)
                                                editor.putString("phone", w.text);
                                                editor.apply();
                                                Toast.makeText(
                                                    this@Verification,
                                                    "Thanks",
                                                    Toast.LENGTH_LONG
                                                ).show();

                                                val intent =
                                                    Intent(this@Verification, Children::class.java)
                                                intent.putExtra("parentId", i.ref.key)
                                                startActivity(intent)


                                            }
                                        }
                                    }
                                        else{
                                            saveData()
                                        }
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    Toast.makeText(this@Verification, error.toString(), Toast.LENGTH_LONG).show()
                                }

                            }
                        )



                    } else {

                        //verification unsuccessful.. display an error message
                        var message = "Something is wrong, we will fix it soon..."
                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            message = "Invalid code entered..."
                        }
                    }
                })
    }

    private fun saveData(){

        val ref = FirebaseDatabase.getInstance().getReference("/data") //reference
        //ref.child(text).setValue(hashMapOf(Pair("name", "ivan")))

        val dataId = ref.push().key //unique key

        val data = Data(dataId.toString(),getPhone!!)
        ref.child(dataId.toString()).setValue(data).addOnCompleteListener(){


            sharedpreferences = getSharedPreferences("MyPREFERENCES",Context.MODE_PRIVATE);
            val editor = sharedpreferences!!.edit();

            editor.putString("id", dataId)
            editor.putString("phone", getPhone);
            editor.apply();
            Toast.makeText(this,"Thanks",Toast.LENGTH_LONG).show();


            Toast.makeText(applicationContext, "Number Saved Successfully", Toast.LENGTH_LONG).show()
            val intent = Intent(this@Verification, Children::class.java)
            intent.putExtra("parentId", dataId.toString())
            startActivity(intent)

        }


    }
}
