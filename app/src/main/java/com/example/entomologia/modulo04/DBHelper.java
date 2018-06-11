package com.example.entomologia.modulo04;

//******************************************************

//Instituto Federal de São Paulo - Campus Sertãozinho

//Disciplina......: M4DADM

//Programação de Computadores e Dispositivos Móveis

//Aluno...........: Rafael Said Bhering Cardoso

//******************************************************

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

public class DBHelper {

    //nome do banco de dados
    private static final String DATABASE_NAME = "cadastro.db";
    //versão do banco de dados
    private static final int DATABASE_VERSION = 1;
    //nome da tabela no banco de dados
    private static final String TABLE_NAME = "cadastro";

    private Context context;
    private SQLiteDatabase db;

    private SQLiteStatement insertStmt;
    //insere os dados na tabela (espaços depois de into e antes de ()
    private static final String INSERT = "insert into " + TABLE_NAME + " (nome, cpf, idade, telefone, email) values (?, ?, ?, ?, ?)";

    //construtor do DBHelper
    public DBHelper (Context context) {
        this.context = context;
        OpenHelper openHelper = new OpenHelper(this.context);
        this.db = openHelper.getWritableDatabase();
        this.insertStmt = this.db.compileStatement(INSERT);

    }

    //insere os dados no banco
    public long insert (String nome, String cpf, String idade, String telefone, String email){
        this.insertStmt.bindString(1, nome);
        this.insertStmt.bindString(2, cpf);
        this.insertStmt.bindString(3, idade);
        this.insertStmt.bindString(4, telefone);
        this.insertStmt.bindString(5, email);
        return  this.insertStmt.executeInsert();
    }

    //apaga os registros
    public void deleteAll(){
        this.db.delete(TABLE_NAME, null, null);
    }

    //obtem as informações do banco de dados
    public List<CadastrarPessoas> queryGetAll(){
        List<CadastrarPessoas> list = new ArrayList<CadastrarPessoas>();
        //trata as exceções
        try{
            Cursor cursor = this.db.query(TABLE_NAME, new String[] {"nome", "cpf", "idade", "telefone", "email"},
                    null, null, null, null, null, null);

            int nregistros = cursor.getCount();
            if (nregistros != 0){
                cursor.moveToFirst();
                //retorna os dados enquanto não for nulo
                do {
                    CadastrarPessoas cadastro = new CadastrarPessoas(cursor.getString(0), cursor.getString(1),
                            cursor.getString(2), cursor.getString(3), cursor.getString(4));
                    list.add(cadastro);
                } while (cursor.moveToNext());

                //se o cursor for diferente de null e não está fechado, fecha o cursor
                if (cursor != null && ! cursor.isClosed())
                    cursor.close();
                return list;

            } else
                return null;

        } catch (Exception err){
            return null;
        }
    }

    private static class OpenHelper extends SQLiteOpenHelper{
        OpenHelper(Context context){
            super (context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        //cria de fato o banco de dados
        public void onCreate(SQLiteDatabase db){
            String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, " +
                    "cpf TEXT, idade TEXT, telefone TEXT, email TEXT);";
            db.execSQL(sql);
        }

        public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}
