package com.example.contatos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.contatos.databinding.ActivityContatosBinding;

import java.util.ArrayList;

public class ContatosActivity extends AppCompatActivity {
    private ActivityContatosBinding activityContatosBinding;
    private ArrayList<Contato> contatosList;
    private ArrayAdapter<Contato> contatosAdapter;
    private final int NOVO_CONTATO_REQUEST_CODE = 0;
    private Contato contato;
    private int CALL_PHONE_PERMISSION_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityContatosBinding = ActivityContatosBinding.inflate(getLayoutInflater());
        setContentView(activityContatosBinding.getRoot());

        contatosList = new ArrayList<>();
        popularContatosList();

        contatosAdapter = new ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                contatosList
        );

        activityContatosBinding.contatosLv.setAdapter(contatosAdapter);

        registerForContextMenu(activityContatosBinding.contatosLv);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterForContextMenu(activityContatosBinding.contatosLv);
    }

    private void popularContatosList(){
        for (int i = 0; i<20;i++){
            contatosList.add(
                    new Contato(
                            "Nome "+ i,
                            "Email "+ i,
                            "Telefone "+ i,
                            (i % 2 == 0) ? false :true,
                            "Celular " + i,
                            "www.sote" + i + ".com.br"
                    )
            );
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contatos,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.novoContatoMi)
        {
            Intent novoContatoItent = new Intent(this,ContatoActivity.class);
            startActivityForResult(novoContatoItent,NOVO_CONTATO_REQUEST_CODE);
            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == NOVO_CONTATO_REQUEST_CODE && resultCode == RESULT_OK){
            Contato contato = (Contato) data.getSerializableExtra(Intent.EXTRA_USER);
            if(contato!=null){
                contatosList.add(contato);
                contatosAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context_menu_contato,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        //posicao
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        contato = contatosAdapter.getItem(menuInfo.position);
        switch (item.getItemId()){
            case R.id.enviarEmailMi:
                Intent enviarEmailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto: "));
                enviarEmailIntent.putExtra(Intent.EXTRA_EMAIL,new String[]{contato.getEmail()});
                enviarEmailIntent.putExtra(Intent.EXTRA_TEXT,contato.toString());
                 startActivity(enviarEmailIntent);
                return true;
            case R.id.ligarMi:
                verifyCallPhonePermission();
                return true;
            case R.id.acessarSiteMi:
                Intent abrirNavegadorIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://"+contato.getSite()));
                startActivity(abrirNavegadorIntent);
                return true;
            case R.id.detalhesContatoMi:
                return true;
            case R.id.editarContatoMi:
                return true;
            case R.id.removerContatoMi:
                return true;
            default:return false;
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}