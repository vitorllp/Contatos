package com.example.contatos;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Toast;

import com.example.contatos.databinding.ActivityContatoBinding;


public class ContatoActivity extends AppCompatActivity {
    private ActivityContatoBinding activityContatoBinding;
    private Contato contato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityContatoBinding = ActivityContatoBinding.inflate(getLayoutInflater());
        setContentView(activityContatoBinding.getRoot());
    }

    public void onClick(View view){
        contato = new Contato(
                activityContatoBinding.nomeText.getText().toString(),
                activityContatoBinding.emailText.getText().toString(),
                activityContatoBinding.telefonetext.getText().toString(),
                activityContatoBinding.switch1.isChecked(),
                activityContatoBinding.celularText.getText().toString(),
                activityContatoBinding.site.getText().toString()
                );
        switch (view.getId()){
            case R.id.buttonSalvar:
                Intent retornoIntent = new Intent();
                retornoIntent.putExtra(Intent.EXTRA_USER, (Parcelable) contato);
                setResult(RESULT_OK,retornoIntent);
                finish();
                break;
            case R.id.buttonPDF:
                break;

            default:break;
        }
    }
}