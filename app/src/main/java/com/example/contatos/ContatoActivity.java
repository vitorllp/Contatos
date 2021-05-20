package com.example.contatos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.view.View;
import android.widget.Toast;

import com.example.contatos.databinding.ActivityContatoBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;


public class ContatoActivity extends AppCompatActivity {
    private ActivityContatoBinding activityContatoBinding;
    private Contato contato;
    private final int PERMISSAO_ESCRITA_AMAZENAMENTO_EXTERNO_REQUEST_CODE = 0;
    private int posicao = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityContatoBinding = ActivityContatoBinding.inflate(getLayoutInflater());
        setContentView(activityContatoBinding.getRoot());

        contato = (Contato) getIntent().getSerializableExtra(Intent.EXTRA_USER);
        if(contato != null){
            posicao = getIntent().getIntExtra(Intent.EXTRA_INDEX,-1);

            boolean ativo  = posicao != -1;
            alterarAtivacaoViews(ativo);
            if(ativo){
                getSupportActionBar().setSubtitle("Edição de contato");
            } else {
                getSupportActionBar().setSubtitle("Detalhes do contato");
            }
            activityContatoBinding.nomeText.setText(contato.getNome());
            activityContatoBinding.emailText.setText(contato.getEmail());
            activityContatoBinding.telefonetext.setText(contato.getTelefone());
            activityContatoBinding.switch1.setChecked(contato.isVerificaNumero());
            activityContatoBinding.site.setText(contato.getSite());
            activityContatoBinding.celularText.setText(contato.getCelular());
        } else {
            getSupportActionBar().setSubtitle("Novo contato");
        }
    }

    private void alterarAtivacaoViews(boolean ativo){
        activityContatoBinding.nomeText.setEnabled(ativo);
        activityContatoBinding.emailText.setEnabled(ativo);
        activityContatoBinding.celularText.setEnabled(ativo);
        activityContatoBinding.telefonetext.setEnabled(ativo);
        activityContatoBinding.switch1.setEnabled(ativo);
        activityContatoBinding.site.setEnabled(ativo);
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
                retornoIntent.putExtra(Intent.EXTRA_USER, contato);
                retornoIntent.putExtra(Intent.EXTRA_INDEX, posicao);
                setResult(RESULT_OK, retornoIntent);
                finish();
                break;
            case R.id.buttonPDF:
                verificarPermissaoEscritaArmazenamentoExterno();
                break;

            default:break;
        }
    }

    private void verificarPermissaoEscritaArmazenamentoExterno(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, PERMISSAO_ESCRITA_AMAZENAMENTO_EXTERNO_REQUEST_CODE);
            }
            else
                gerarDocumentoPDF();
        }
        else
            gerarDocumentoPDF();
    }

    private void gerarDocumentoPDF() {
        View conteudo = activityContatoBinding.getRoot();
        int altura = conteudo.getHeight();
        int largura = conteudo.getWidth();

        PdfDocument documentoPdf = new PdfDocument();

        PdfDocument.PageInfo configuracao = new PdfDocument.PageInfo.Builder(largura, altura, 1).create();
        PdfDocument.Page pagina = documentoPdf.startPage(configuracao);

        conteudo.draw(pagina.getCanvas());

        documentoPdf.finishPage(pagina);

        File diretorioDocumentos = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath());
        try{

            File documento = new File(diretorioDocumentos, contato.getNome().replace(" ", "_") + ".pdf");
            documento.createNewFile();
            documentoPdf.writeTo(new FileOutputStream(documento));
            documentoPdf.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSAO_ESCRITA_AMAZENAMENTO_EXTERNO_REQUEST_CODE)
            verificarPermissaoEscritaArmazenamentoExterno();
    }

}