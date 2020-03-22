package com.example.tictactoe2.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;



import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.example.tictactoe2.R;
import com.example.tictactoe2.roomselection;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);




        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);
        final Button RegistrationButton = findViewById(R.id.Registration);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                RegistrationButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);


            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             loadingProgressBar.setVisibility(View.VISIBLE);
              loginViewModel.login(usernameEditText.getText().toString(),
                    passwordEditText.getText().toString());
              String username =usernameEditText.getText().toString();
                String password =passwordEditText.getText().toString();
                SendMeassge();

            }
        });
        RegistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
                String username =usernameEditText.getText().toString();
                String password =passwordEditText.getText().toString();
                Sendregistration();

            }
        });
    }

    private void updateUiWithUser(LoggedInUserView model) {



    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }



    private static final int SERVERPORT = 3000;
    private static final String SERVER_IP = "127.0.0.1";




    public void SendMeassge() {
        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);

            HttpClient httpclient = new DefaultHttpClient();
//            String encoded = URLEncoder.encode("https://obscure-refuge-45482.herokuapp.com/login");
            HttpPost httppost = new HttpPost("https://obscure-refuge-45482.herokuapp.com/login");

            //add data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("user", usernameEditText.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("password", passwordEditText.getText().toString()));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            //execute http post
            HttpResponse httpResponse = httpclient.execute(httppost);
            HttpEntity httpEntity = httpResponse.getEntity();
            Intent intent = new Intent(LoginActivity.this, roomselection.class);
//            Toast.makeText(getApplicationContext(), httpEntity.toString(), Toast.LENGTH_SHORT).show();
            if (httpEntity != null) {

                if (httpResponse.getStatusLine().getStatusCode() == 200) {

                    Toast.makeText(getApplicationContext(), "login success", Toast.LENGTH_SHORT).show();
                    String username = usernameEditText.getText().toString();
                    intent.putExtra("username", username);
                    Log.d("username", usernameEditText.getText().toString());
                    intent.putExtra("loginstaus", true);
                    startActivity(intent);
                   finish();
//                    Intent intent = new Intent (this, Main2Activityonline.class);
//                    startActivity(intent);
//                   startActivity(new Intent(LoginActivity.this, Main2Activityonline.class));
                } else if(httpResponse.getStatusLine().getStatusCode() == 400){

                    AlertDialog.Builder MyAlertDialog = new AlertDialog.Builder(this);

                    MyAlertDialog.setTitle("Message");

                    MyAlertDialog.setMessage("login failed");

                    MyAlertDialog.show();

                  //  Toast.makeText(getApplicationContext(), "login failed", Toast.LENGTH_SHORT).show();
                }

            }

        }

        catch (Exception e) {
            AlertDialog.Builder MyAlertDialog = new AlertDialog.Builder(this);

            MyAlertDialog.setTitle("標題");

            MyAlertDialog.setMessage(e.toString());

            MyAlertDialog.show();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    public void Sendregistration() {
        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);

            HttpClient httpclient = new DefaultHttpClient();

            HttpPost httppost = new HttpPost("https://obscure-refuge-45482.herokuapp.com/Registation");

            //add data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("user", usernameEditText.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("password", passwordEditText.getText().toString()));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            //execute http post
            HttpResponse httpResponse = httpclient.execute(httppost);
            HttpEntity httpEntity = httpResponse.getEntity();

            if (httpEntity != null) {

                if (httpResponse.getStatusLine().getStatusCode() == 200) {


                    AlertDialog.Builder MyAlertDialog = new AlertDialog.Builder(this);

                    MyAlertDialog.setTitle("Registration success");

                    MyAlertDialog.setMessage("You may login now");

                    MyAlertDialog.show();


                } else if(httpResponse.getStatusLine().getStatusCode() == 400){

                    AlertDialog.Builder MyAlertDialog = new AlertDialog.Builder(this);

                    MyAlertDialog.setTitle("Registration failed");

                    MyAlertDialog.setMessage("User exist");

                    MyAlertDialog.show();

                }

            }

        }

        catch (Exception e) {
            AlertDialog.Builder MyAlertDialog = new AlertDialog.Builder(this);

            MyAlertDialog.setTitle("Error");

            MyAlertDialog.setMessage(e.toString());

            MyAlertDialog.show();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }


}
