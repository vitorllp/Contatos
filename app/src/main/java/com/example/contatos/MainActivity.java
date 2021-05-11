package com.example.contatos;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;




public class MainActivity extends AppCompatActivity {
    private com.example.contatos.databinding.ActivityMainBinding activityMainBinding;
    private Contato contato;
    private int CALL_PHONE_PERMISSION_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = com.example.contatos.databinding.ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
    }

    public void onClick(View view){
        contato = new Contato(
                activityMainBinding.nomeText.getText().toString(),
                activityMainBinding.emailText.getText().toString(),
                activityMainBinding.switch1.isChecked(),
                activityMainBinding.telefonetext.getText().toString(),
                activityMainBinding.celularText.getText().toString(),
                activityMainBinding.site.getText().toString()
                );
        switch (view.getId()){
            case R.id.buttonSalvar:
                Toast.makeText(this, contato.toString(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.buttonEmail:
                Intent enviarEmail = new Intent(Intent.ACTION_SENDTO);
                enviarEmail.setData(Uri.parse("mailto: "));
                enviarEmail.putExtra(Intent.EXTRA_EMAIL,new String[]{activityMainBinding.buttonEmail.getText().toString()});
                enviarEmail.putExtra(Intent.EXTRA_SUBJECT,"Contato");
                enviarEmail.putExtra(Intent.EXTRA_TEXT,contato.toString());
                startActivity(enviarEmail);
                break;
            case R.id.buttonSite:
                Intent abrirNavegadorIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://"+contato.getSite()));
                startActivity(abrirNavegadorIntent);
                break;
            case R.id.buttonPDF:
                break;
            case R.id.buttonTelefone:
                verifyCallPhonePermission();
                break;
            default:break;
        }
    }

    private void verifyCallPhonePermission(){
        Intent ligarIntent = new Intent(Intent.ACTION_CALL);
        ligarIntent.setData(Uri.parse("tel:"+contato.getTelefone()));
        if(Build.VERSION.SDK_INT == Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED){
                startActivity(ligarIntent);
            } else {
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE},CALL_PHONE_PERMISSION_REQUEST_CODE);
            }
        } else {
            startActivity(ligarIntent);
        }
    }
}