package com.example.sparsh23.laltern;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.backup.FileBackupHelper;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import com.facebook.*;
import com.facebook.Profile;
import com.google.android.gms.auth.api.Auth;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor>,GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

    private static final int REQUEST_READ_CONTACTS = 0;

    public String DOWN_URL = "http://www.whydoweplay.com/lalten/CheckLogin.php";
//    LoginActivity loginActivity;





    // UI references.
   // private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleApiClient mGoogleApiClient;

    SessionManager sessionManager;
    CallbackManager callbackManager;
    private int RC_SIGN_IN = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


           if (!isNetworkAvailable())
            {
                startActivity(new Intent(LoginActivity.this,No_Internet_Connection.class));
                finish();
            }

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        mAuth = FirebaseAuth.getInstance();

       // LoginManager.getInstance().logOut();


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this ,(GoogleApiClient.OnConnectionFailedListener) this )
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        setContentView(R.layout.activity_login);

        findViewById(R.id.sign_in_button).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        // Set up the login form.
        populateAutoComplete();
        sessionManager = new SessionManager(getApplicationContext());

        if((sessionManager.isLoggedIn())&&(sessionManager.getUserDetails().get("uid")!=null)){

            Toast.makeText(getApplicationContext(),""+sessionManager.getUserDetails().get("uid"),Toast.LENGTH_SHORT).show();

            startActivity(new Intent(LoginActivity.this,Update.class));
            finish();

        }








        callbackManager = CallbackManager.Factory.create();
        final Button loginButton = (Button) findViewById(R.id.login_button);

        mAuthListener = new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null)
                {
                    Log.d("firebaselogin", "onAuthStateChanged:signed_in:" + user.getUid() +""+user.getEmail()+"");
                } else {
                    // User is signed out
                    Log.d("firebaselogout", "onAuthStateChanged:signed_out");
                }
                // [START_EXCLUDE]
                //updateUI(user);
                // [END_EXCLUDE]
            }
        };





        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                //loginButton.setReadPermissions("public_profile","email");
                //LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, );
             //   LoginManager.getInstance().logInWithReadPermissions();
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile","email"));
                LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult loginResult) {


                        ProfileTracker profileTracker;

    //                    GraphRequest.newMeRequest()

                        if (Profile.getCurrentProfile()==null)
                        {
                            profileTracker = new ProfileTracker() {
                                @Override
                                protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {




                                    this.stopTracking();
                                    Profile.setCurrentProfile(currentProfile);
                                    com.facebook.Profile profile = Profile.getCurrentProfile();

                                    Log.d("facebook_details fn",""+profile.getFirstName().toString());
                                    Log.d("facebook_details dp",""+profile.getProfilePictureUri(400,400).toString());
                                    Log.d("facebook_details tok", "" +  loginResult.getAccessToken().toString());
                                    Log.d("facebook_details id", "" +  loginResult.getAccessToken().getUserId().toString());
                                    Log.d("facebook_detailspid", "" +  profile.getId().toString());
                                    Log.d("facebook_detailspl", "" +  profile.getLinkUri().toString());
                                    Log.d("facebook_detailspn", "" +  profile.getName().toString());

                                    handleFacebookAccessToken(loginResult.getAccessToken(),loginResult,profile.getName(),profile.getProfilePictureUri(400,400).toString());
                                }
                            };
                            profileTracker.startTracking();
                        }
                        else {
                            Profile pro = Profile.getCurrentProfile();
                            loginResult.getRecentlyGrantedPermissions();
                            AccessToken accessToken = loginResult.getAccessToken();
                            Log.d("facebook_details", "" + loginResult.getRecentlyGrantedPermissions().toString());
                            Log.d("facebook_details",""+pro.getFirstName().toString());
                            Log.d("facebook_details",""+pro.getProfilePictureUri(400,400).toString());
                            Log.d("facebook_details", "" + accessToken.getUserId().toString());
                            Log.d("facebook_detailspid", "" +  pro.getId().toString());
                            Log.d("facebook_detailspl", "" +  pro.getLinkUri().toString());
                            Log.d("facebook_detailspn", "" +  pro.getName().toString());
                            handleFacebookAccessToken(loginResult.getAccessToken(),loginResult,pro.getName(),pro.getProfilePictureUri(400,400).toString());
                        }
                    }

                    @Override
                    public void onCancel() {



                    }

                    @Override
                    public void onError(FacebookException error) {

                        Toast.makeText(getApplicationContext(),""+error.toString(),Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });


    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }


    public  boolean hasActiveInternetConnection(Context context) {
        if (isNetworkAvailable()) {
            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://clients3.google.com/generate_204").openConnection());
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.connect();
                return (urlc.getResponseCode() == 204 && urlc.getContentLength() == 0);
            } catch (IOException e) {
                Log.e("error_no_data", "Error checking internet connection", e);
            }
        } else {
            Log.d("error_no_data", "No network available!");
        }
        return false;
    }



    public boolean Register_FacebookUser(LoginResult loginResult, final String name, final String uid, final String dp)
    {
        String email;

        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        Log.v("LoginActivity Response ", response.toString());

                        try {
                            sessionManager.createLoginSession(object.getString("email"),name,mAuth.getCurrentUser().getUid(),dp);
                            Log.v("Email = ", " " + object.getString("email"));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "email");
        request.setParameters(parameters);
        request.executeAsync();

            return  true;

    }

    public void RegisterGoogleUser()
    {


    }





    private void handleFacebookAccessToken(AccessToken token, final LoginResult loginResult, final String name,  final String dp) {
        Log.d("firebase_token", "handleFacebookAccessToken:" + token);
        // [START_EXCLUDE silent]
        //showProgressDialog();
        // [END_EXCLUDE]

        final ProgressDialog loading = ProgressDialog.show(this,"logging in...","Please wait...",false,false);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());




        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        loading.dismiss();
                        Log.d("firebase_signin", "signInWithCredential:onComplete:" + task.isSuccessful());

                        Register_FacebookUser(loginResult,name, mAuth.getCurrentUser().getUid(),dp);
                        startActivity(new Intent(LoginActivity.this,Update.class));
                        finish();

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            loading.dismiss();
                            Log.w("firebase handle", "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
                        //hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });

    }



    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {

        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        final ProgressDialog loading = ProgressDialog.show(this,"Getting Logging...","Please wait...",false,false);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                loading.dismiss();
                GoogleSignInAccount account = result.getSignInAccount();
                Log.d("data google",""+account.getDisplayName());
                Log.d("data google",""+account.getPhotoUrl());
                Log.d("google email",""+account.getEmail());


                firebaseAuthWithGoogle(account);
            } else {
                loading.dismiss();
                // Google Sign In failed, update UI appropriately
                // [START_EXCLUDE]
         //       updateUI(null);
                // [END_EXCLUDE]
            }
        }
        loading.dismiss();
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {

        final ProgressDialog loading = ProgressDialog.show(this,"Logging in...","Please wait...",false,false);
        Log.d("google signin", "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]
       // showProgressDialog();
        // [END_EXCLUDE]

        final AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("google_signin", "signInWithCredential:onComplete:" + task.isSuccessful());

                        loading.dismiss();
                        sessionManager.createLoginSession(acct.getEmail(),acct.getDisplayName(),mAuth.getCurrentUser().getUid(), String.valueOf(acct.getPhotoUrl()));
                        startActivity(new Intent(LoginActivity.this,Update.class));
                        finish();



                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            loading.dismiss();
                            Log.w("google signin", "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [START_EXCLUDE]
                        //hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }



    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

       // mEmailView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */







    public boolean LogInUser(final String email, final String pass)
    {

        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this,"Logging In User...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DOWN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {


                        loading.dismiss();
                        Log.d("response", s.toString());


                        if (s!=null)
                        {



                            if (s.equals("Incorrent Credentials"))
                            {

                                Toast.makeText(getApplicationContext(),""+s,Toast.LENGTH_SHORT).show();

                                 }
                        else
                            {
                                //sessionManager.createLoginSession(email,pass,s);
                                startActivity(new Intent(LoginActivity.this,Update.class));
                                finish();
                            }

    //                        else
                            Toast.makeText(getApplicationContext(),""+s,Toast.LENGTH_SHORT).show();
                                                 }





                        //Disimissing the progress dialog

                        //Showing toast message of the response
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog


                        loading.dismiss();
                        //Showing toast
                        Toast.makeText(LoginActivity.this, "Error In Connectivity one", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError


            {
                //Converting Bitmap to String


                HashMap<String,String> Keyvalue = new HashMap<String,String>();
                Keyvalue.put("email", email);
                Keyvalue.put("pass",pass);




                //returning parameters
                return Keyvalue;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //Adding request to the queue
        requestQueue.add(stringRequest);
        return false;
    }






}

