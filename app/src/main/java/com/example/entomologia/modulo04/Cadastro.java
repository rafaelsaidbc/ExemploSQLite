package com.example.entomologia.modulo04;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class Cadastro extends AppCompatActivity {

    private DBHelper dbhelper;
    EditText editTextNome, editTextCpf, editTextIdade, editTextTelefone, editTextEmail;
    Button btnInserir, btnListar, btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        this.dbhelper = new DBHelper(this);

        editTextNome = (EditText) findViewById(R.id.editTextNome);
        editTextCpf = (EditText) findViewById(R.id.editTextCPF);
        editTextIdade = (EditText) findViewById(R.id.editTextIdade);
        editTextTelefone = (EditText) findViewById(R.id.editTextTelefone);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);

        btnInserir = (Button) findViewById(R.id.btnInserir);
        btnListar = (Button) findViewById(R.id.btnListar);
        btnVoltar = (Button) findViewById(R.id.btnVoltar);

        btnInserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextNome.getText().length() > 0 && editTextCpf.getText().length() > 0 &&
                        editTextIdade.getText().length() > 0 && editTextTelefone.getText().length() > 0 &&
                        editTextEmail.getText().length() > 0) {
                    dbhelper.insert(editTextNome.getText().toString(), editTextCpf.getText().toString(),
                            editTextIdade.getText().toString(), editTextTelefone.getText().toString(),
                            editTextEmail.getText().toString());
                    AlertDialog.Builder adb = new AlertDialog.Builder(Cadastro.this);
                    adb.setTitle("Sucesso");
                    adb.setMessage("Cadastro Realizado!");
                    adb.show();

                    editTextNome.setText("");
                    editTextCpf.setText("");
                    editTextIdade.setText("");
                    editTextTelefone.setText("");
                    editTextEmail.setText("");

                } else {
                    AlertDialog.Builder adb = new AlertDialog.Builder(Cadastro.this);
                    adb.setTitle("Erro");
                    adb.setMessage("Todos os campos devem ser preenchidos!");
                    adb.show();

                    editTextNome.setText("");
                    editTextCpf.setText("");
                    editTextIdade.setText("");
                    editTextTelefone.setText("");
                    editTextEmail.setText("");
                }
            }
        });

        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pega todos os contatos e lista
                List<CadastrarPessoas> cadastrarPessoas = dbhelper.queryGetAll();
                if (cadastrarPessoas == null) {
                    AlertDialog.Builder adb = new AlertDialog.Builder(Cadastro.this);
                    adb.setTitle("Mensagem");
                    adb.setMessage("Não há registros cadastrados!");
                    adb.show();

                    editTextNome.setText("");
                    editTextCpf.setText("");
                    editTextIdade.setText("");
                    editTextTelefone.setText("");
                    editTextEmail.setText("");
                    return;
                }

                for (int i = 0; i < cadastrarPessoas.size(); i++) {
                    CadastrarPessoas cadastro = (CadastrarPessoas) cadastrarPessoas.get(i);
                    AlertDialog.Builder adb = new AlertDialog.Builder(Cadastro.this);
                    adb.setTitle("Registro " + i);
                    adb.setMessage("Nome: " + cadastro.getNome() + "\nCPF: " + cadastro.getCpf() +
                            "\nIdade: " + cadastro.getIdade() + "\nTelefone: " + cadastro.getTelefone() +
                            "\nE-mail: " + cadastro.getEmail());
                    adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //aperta OK, fecha a janela e vai para o próximo
                            dialog.dismiss();
                        }
                    });

                    adb.show();
                }
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cadastro.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}