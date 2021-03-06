package com.cihatpala.week1project.Fragments;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cihatpala.week1project.R;
import com.cihatpala.week1project.databinding.FragmentLoginBinding;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.Executor;

public class LoginFragment extends Fragment {

    FragmentLoginBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private CallbackManager mCallbackManager;
    private static final String EMAIL = "email";
    AccessToken accessToken;
    FirebaseUser currentUser;
    private GoogleSignInClient mGoogleSignInClient;
    public View globalView;
    private static final int RC_SIGN_IN = 9001;


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mCallbackManager = CallbackManager.Factory.create();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //User is sign in
                    toastMessage("Zaten giri?? yap??ld??");
                    NavDirections action = LoginFragmentDirections.actionLoginFragmentToHomeFragment();
                    Navigation.findNavController(requireView()).navigate(action);
                } else {
                    //user is sign out
                    toastMessage("????k???? yap??ld??");
                }

            }
        };

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
            NavDirections action = LoginFragmentDirections.actionLoginFragmentToHomeFragment();
            Navigation.findNavController(requireView()).navigate(action);
        }
        binding.btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.etLoginMailText.getText().toString();
                String password = binding.etLoginPassText.getText().toString();
                if (!email.equals("") && !password.equals("")) {
                    mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            toastMessage("Login Ba??ar??l??!");
//                            NavDirections action = LoginFragmentDirections.actionLoginFragmentToHomeFragment();
//                            Navigation.findNavController(view).navigate(action);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            toastMessage("Login Ba??ar??s??z : " + e.getLocalizedMessage());
                        }
                    });
                } else {
                    //bo??
                    toastMessage("L??tfen alanlar?? bo?? b??rakmay??n??z");
                }
            }
        });

        binding.btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });

        binding.btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize Facebook Login button
                binding.btnFacebook.setReadPermissions(Arrays.asList(EMAIL, "password"));
                binding.btnFacebook.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d(TAG, "facebook:onSuccess:" + loginResult);
                        handleFacebookAccessToken(loginResult.getAccessToken(), view);
                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG, "facebook:onCancel");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d(TAG, "facebook:onError", error);
                    }
                });

            }
        });

        binding.btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalView = view;
                signInUsingGoogle();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, null, false);
        View view = binding.getRoot();
        return view;
    }

    private void signInUsingGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleFacebookAccessToken(AccessToken token, View view) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user, view);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null, null);
                        }
                    }
                });
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnSuccessListener(authResult -> {
                    Log.w("TAG", "onSuccess firebaseAuthWithGoogle");
//                    NavDirections action = LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment();
//                    Navigation.findNavController(globalView).navigate(action);
                }).addOnFailureListener(e -> {
            Log.w("TAG", "onFailure firebaseAuthWithGoogle");
            Toast.makeText(getContext(), "bir hata olu??tu : " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        });
    }

    private void updateUI(FirebaseUser user, View view) {
        if (user != null) {
            NavDirections action = LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment();
            Navigation.findNavController(view).navigate(action);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        Log.w("TAG", "onActivityResult: " + requestCode);
        Log.w("TAG", "resultCode: " + resultCode);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed
                Log.w("TAG", "GoogleSignIn: " + e.getLocalizedMessage());
                Toast.makeText(getContext(), "Login olunurken bir hata olu??tu :" + e
                        .getLocalizedMessage(), Toast.LENGTH_LONG).show();
                Log.w("TAG", "Google sign in failed", e);
            }
        }
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Pass the activity result back to the Facebook SDK
//        mCallbackManager.onActivityResult(requestCode, resultCode, data);
//    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void toastMessage(String string) {
        System.out.println("toastMessage");
        Toast.makeText(getContext(), string, Toast.LENGTH_LONG).show();
        System.out.println("message -> " + string);
    }
}