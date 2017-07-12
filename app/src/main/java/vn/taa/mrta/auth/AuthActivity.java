package vn.taa.mrta.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.HashMap;

import vn.taa.mrta.custom.ButtonCSE;
import vn.taa.mrta.custom.TypefaceSpanCSE;
import vn.taa.mrta.general.CommonField;
import vn.taa.mrta.main.MainActivity;
import vn.taa.mrta.R;
import vn.taa.mrta.object.Clinic;
import vn.taa.mrta.object.Patient;

public class AuthActivity extends AppCompatActivity implements View.OnClickListener, IViewAuth {

    private EditText edtPatientBriefID;
    private ButtonCSE btnLogin;
    private ButtonCSE btnScan;
    private Typeface typeface;
    private TypefaceSpan typefaceSpan;
    private PresenterAuth presenterAuth;
    private SharedPreferences sprfSKB;
    private SharedPreferences.Editor editorSKB;
    private String resultContent = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        initialize();
        if (sprfSKB.getBoolean(CommonField.LOGIN_STATUS, false)) {
            Intent intentMain = new Intent(this, MainActivity.class);
            startActivity(intentMain);
        }
    }

    private void initialize() {
        edtPatientBriefID = (EditText) findViewById(R.id.edt_patient_brief_id);
        btnLogin = (ButtonCSE) findViewById(R.id.btn_login);
        btnScan = (ButtonCSE) findViewById(R.id.btn_scan_to_login);

        sprfSKB = getSharedPreferences(CommonField.SHARED_PREFERENCES, MODE_PRIVATE);

        typeface = Typeface.createFromAsset(getAssets(), "fonts/" + getString(R.string.font_opensans_light) + ".ttf");
        typefaceSpan = new TypefaceSpanCSE(typeface);
        String hintPatientBriefID = getString(R.string.edt_hint_patient_brief_id);
        SpannableString spannableStringHintUsername = new SpannableString(hintPatientBriefID);

        spannableStringHintUsername.setSpan(typefaceSpan, 0, hintPatientBriefID.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        edtPatientBriefID.setHint(spannableStringHintUsername);

        btnLogin.setOnClickListener(this);
        btnScan.setOnClickListener(this);
        presenterAuth = new PresenterAuth(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btn_login:
                if (edtPatientBriefID.getText().toString().length() < CommonField.CLINIC_ID_SIZE) {
                    Toast.makeText(this, getString(R.string.id_invalid), Toast.LENGTH_SHORT).show();
                } else {
                    presenterAuth.getHostInfo(AuthActivity.this, edtPatientBriefID.getText().toString());
                    presenterAuth.login(AuthActivity.this, edtPatientBriefID.getText().toString());
                }
                break;
            case R.id.btn_scan_to_login:
                IntentIntegrator integrator = new IntentIntegrator(AuthActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt(getString(R.string.btn_scan_barcode_to_login));
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
                break;
        }
    }

    @Override
    public void loginSuccess(Patient patient) {
        Toast.makeText(this, getString(R.string.message_login_success) + " " + patient.getName(), Toast.LENGTH_SHORT).show();

        editorSKB = sprfSKB.edit();
        editorSKB.putBoolean(CommonField.LOGIN_STATUS, true);

        if (resultContent.isEmpty()) {
            editorSKB.putString(CommonField.PATIENT_BRIEF_ID, edtPatientBriefID.getText().toString());
        } else {
            editorSKB.putString(CommonField.PATIENT_BRIEF_ID, resultContent);
        }
        editorSKB.apply();

        Intent intentMain = new Intent(this, MainActivity.class);
        startActivity(intentMain);
    }

    @Override
    public void loginFailed() {
        Toast.makeText(this, getString(R.string.message_login_failed), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null && result.getContents() != null) {
            resultContent = result.getContents();
            if (resultContent.length() < CommonField.CLINIC_ID_SIZE) {
                Toast.makeText(this, getString(R.string.id_invalid), Toast.LENGTH_SHORT).show();
            } else {
                presenterAuth.getHostInfo(AuthActivity.this, resultContent.substring(0, CommonField.CLINIC_ID_SIZE));
                presenterAuth.login(AuthActivity.this, resultContent);
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}