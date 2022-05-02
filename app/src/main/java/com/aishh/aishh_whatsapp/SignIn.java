package com.aishh.aishh_whatsapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.aishh.aishh_whatsapp.Models.Users;
import com.aishh.aishh_whatsapp.databinding.ActivitySignInBinding;
import com.aishh.aishh_whatsapp.databinding.ActivitySignUpBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

public class SignIn extends AppCompatActivity {
    ActivitySignInBinding binding;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        progressDialog=new ProgressDialog(SignIn.this);
        progressDialog.setTitle("LogIn");
        progressDialog.setMessage("LogIn to your account");
        getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        //Configure Google Signin
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        binding.signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.email.getText().toString().isEmpty()){
                    binding.email.setError("Enter your email");
                    return;
                }
                if(binding.password.getText().toString().isEmpty()){
                    binding.password.setError("Enter your Password");
                    return;
                }
                progressDialog.show();
                auth.signInWithEmailAndPassword(binding.email.getText().toString(),binding.password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if(task.isSuccessful()){
                                Intent intent=new Intent(SignIn.this,MainActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(SignIn.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                    }
                });
            }
        });
        binding.donthaveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignIn.this,SignUp.class);
                startActivity(intent);
            }
        });
        binding.google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultLauncher.launch(new Intent(mGoogleSignInClient.getSignInIntent()));
            }
        });
        //Once sign in user will directly get main activity page after reopening
        if(auth.getCurrentUser()!=null){
            Intent intent=new Intent(SignIn.this,MainActivity.class);
            startActivity(intent);
        }
    }

    ActivityResultLauncher<Intent> resultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
        if(result.getResultCode()== Activity.RESULT_OK){
            Intent intent=result.getData();
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(intent);
            try {
                //Google sign in was successfull authenticate with firebase
                GoogleSignInAccount account=task.getResult(ApiException.class);
                Log.d("TAG","firebaseAuthWithGoogle:"+account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            }catch (ApiException e){
                //Google signin failed update ui automaticaly
                Log.w("TAG","Google signin failed",e);
            }
        }
        }
    });
   private void firebaseAuthWithGoogle(String idToken){
       AuthCredential credential= GoogleAuthProvider.getCredential(idToken,null);
       auth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {
               if(task.isSuccessful()){
                    Log.d("TAG","signInWithCredential:Success");
                    FirebaseUser user=auth.getCurrentUser();
                   Toast.makeText(SignIn.this, "Signed in with Google", Toast.LENGTH_SHORT).show();
                    Users users=new Users();
                    users.setUserid(user.getUid());
                    users.setUsername(user.getDisplayName());
                    users.setProfilepic(user.getPhotoUrl().toString());
                    database.getReference().child("Users").child(user.getUid()).setValue(users);

                    Intent intent=new Intent(SignIn.this,MainActivity.class);
                    startActivity(intent);
//                    updateUI(user);
               }else{
                   Log.w("TAG","signInWithCredential:failure",task.getException());
                   Snackbar.make(binding.getRoot(),"Authentication failed",Snackbar.LENGTH_SHORT).show();

               }
           }
       });
   }
}