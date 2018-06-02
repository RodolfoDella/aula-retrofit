package br.com.wedosoft.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.wedosoft.control.PessoaApi;
import br.com.wedosoft.consomews.R;
import br.com.wedosoft.control.RetrofitBuilder;
import br.com.wedosoft.model.ContentModel;
import br.com.wedosoft.model.PessoaModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView mListView;
    List<PessoaModel> mPessoa;
    final String SALVAR_INSTANCIA_PESSOAS = "salva_pessoas";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mListView = findViewById(R.id.listPessoa);
        mListView.setOnItemClickListener(this);

        // Recuperar itens da lista
        recoverInstanceState(savedInstanceState);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adicionarPessoaClick();
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putSerializable(SALVAR_INSTANCIA_PESSOAS, new ArrayList<>(mPessoa));
        //https://medium.com/@mdmasudparvez/android-os-transactiontoolargeexception-on-nougat-solved-3b6e30597345
    }

    private void recoverInstanceState(Bundle savedInstanceState){
        if (savedInstanceState != null){
            mPessoa = (List<PessoaModel>) savedInstanceState.getSerializable(SALVAR_INSTANCIA_PESSOAS);
        }

        if (mPessoa != null) {
            PessoaAdapter mAdapter = new PessoaAdapter(MainActivity.this, mPessoa);
            mListView.setAdapter(mAdapter);
        } else {
            buscar();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            buscar();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        PessoaModel pessoa = (PessoaModel) adapterView.getItemAtPosition(i);
        editarPessoa(pessoa);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void adicionarPessoaClick() {
        Intent intent = new Intent(MainActivity.this, PessoaNewActivity.class);
        startActivity(intent);
    }

    public void editarPessoa(PessoaModel pessoa) {
        Intent it = new Intent(this, PessoaNewActivity.class);
        it.putExtra(PessoaNewActivity.EXTRA_PESSOA, pessoa);
        startActivity(it);
    }

    public void buscar() {
        //creating the pessoaApi interface
        PessoaApi pessoaApi = RetrofitBuilder.getBuilder().create(PessoaApi.class);

        //making the call object
        Call<ContentModel> call = pessoaApi.select();

        call.enqueue(new Callback<ContentModel>() {
            @Override
            public void onResponse(@NonNull Call<ContentModel> call,
                                   @NonNull Response<ContentModel> response) {

                if(response.isSuccessful()) {
                    ContentModel content = response.body();
                    mPessoa = content.getPessoas();

                    PessoaAdapter mAdapter = new PessoaAdapter(MainActivity.this, mPessoa);
                    mListView.setAdapter(mAdapter);

                    showSnackBar("Lista atualizada!");

                } else {
                    int statusCode  = response.code();
                    showSnackBar("Ops.. Algo deu errado!");
                }
            }
            @Override
            public void onFailure(Call<ContentModel> call, Throwable t) {
                Log.e("onFailure", String.valueOf(t.getMessage()));
                showSnackBar(String.valueOf(t.getMessage()));
            }
        });
    }

    private void showSnackBar(String message) {
        Snackbar.make(getWindow().getDecorView(), message, Snackbar.LENGTH_LONG).show();
    }
}
