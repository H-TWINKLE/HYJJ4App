package com.example.twinkle.hyjj4;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
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

import com.example.twinkle.hyjj4.java.LoginMain;

import java.util.ArrayList;
import java.util.List;



/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView AutoCompleteTextView_admin;
    private EditText editText_password;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        AutoCompleteTextView_admin = (AutoCompleteTextView) findViewById(R.id.admin);


        editText_password = (EditText) findViewById(R.id.password);
        editText_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.Button_login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.Button_login);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        //new
        TextView textView_finish = (TextView)findViewById(R.id.textView_finish);
        textView_finish.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();



            }
        });

        TextView textView_forget = (TextView)findViewById(R.id.textView_forget);
        textView_forget.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Toast.makeText(getApplicationContext(), "暂时支持客服找回密码", Toast.LENGTH_SHORT).show();
                    String url="mqqwpa://im/chat?chat_type=wpa&uin=2662065155";
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "获取qq失败", Toast.LENGTH_SHORT).show();
                }





            }
        });

    }



    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        AutoCompleteTextView_admin.setError(null);
        editText_password.setError(null);

        // Store values at the time of the login attempt.
       final  String admin = AutoCompleteTextView_admin.getText().toString();
        final String password = editText_password.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            editText_password.setError(getString(R.string.error_invalid_password));
            focusView = editText_password;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(admin)) {
            AutoCompleteTextView_admin.setError(getString(R.string.error_field_required));
            focusView = AutoCompleteTextView_admin;
            cancel = true;
        }
        if (TextUtils.isEmpty(password)) {
            editText_password.setError(getString(R.string.error_field_required));
            focusView = editText_password;
            cancel = true;
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            /*mAuthTask = new UserLoginTask(admin, password);
            mAuthTask.execute((Void) null);*/
           login(admin,password);

        }
    }

    public void login(final String admin, final String password) {





        new Thread() {
            public void run() {

                try {
                    LoginMain loginMain = new LoginMain();

                    String info =  loginMain.login_main(admin,password).replaceAll("\\s*", "");

                     System.out.println("登录状态"+info);
                     System.out.println("info长度"+info.length());

                    if(info.equals("密码错误"))
                    {     Looper.prepare();
                            Toast.makeText(getApplicationContext(), "密码错误", Toast.LENGTH_SHORT).show();
                            finish();
                            Intent intent1 = new Intent(LoginActivity.this, LoginActivity.class);
                            startActivity(intent1);
                            Looper.loop();}
                     if(info.equals("null"))
                     {     Looper.prepare();
                            Toast.makeText(getApplicationContext(), "欢迎：新用户"+admin, Toast.LENGTH_SHORT).show();
                            String admininfo[] = new String[7];
                            admininfo = loginMain.get_admininfo(admin);
                            SharedPreferences preferences=getSharedPreferences("user", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=preferences.edit();
                            editor.putString("admin", admin);
                            editor.putString("password",admininfo[1]);
                            editor.putString("name", "新用户"+admin);
                            editor.putString("say", "我是新用户"+admin);
                            editor.putString("level", "0");
                            editor.putString("xuehao", admininfo[5]);
                            editor.putString("xuehaopassword", admininfo[6]);
                            editor.commit();

                            finish();
                            Intent intent2 = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent2);
                            Looper.loop();}

                        else
                     {       Looper.prepare();
                            Toast.makeText(getApplicationContext(), "欢迎："+info, Toast.LENGTH_SHORT).show();
                            String admininfo[] = new String[7];
                            admininfo = loginMain.get_admininfo(admin);
                            SharedPreferences preferences=getSharedPreferences("user", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=preferences.edit();
                            editor.putString("admin", admin);
                            editor.putString("password",admininfo[1]);
                            editor.putString("name", admininfo[2]);
                            editor.putString("say", admininfo[3]);
                            editor.putString("level", admininfo[4]);
                            editor.putString("xuehao", admininfo[5]);
                            editor.putString("xuehaopassword", admininfo[6]);
                            editor.commit();

                            finish();
                            Intent intent3 = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent3);
                            Looper.loop();
                    }



                } catch (Exception e) {


                    Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                    startActivity(intent);
                    e.printStackTrace();


                }


            }
        }.start();

    }




    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 5;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

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

        AutoCompleteTextView_admin.setAdapter(adapter);
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
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                editText_password.setError(getString(R.string.error_incorrect_password));
                editText_password.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }





    }
}

