package br.com.wedosoft.view;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.wedosoft.control.PessoaUtil;
import br.com.wedosoft.consomews.R;
import br.com.wedosoft.model.PessoaModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PessoaNewActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "PESSOA_NEW_ACTIVITY";
    public static final String EXTRA_PESSOA = "pessoa";

    private Context context;
    private PessoaModel mPessoa;

    EditText ed_nome;
    EditText ed_dataNascimento;
    EditText ed_cpf;
    EditText ed_telefone;
    EditText ed_email;

    RadioGroup rg_sexo;
    RadioButton radioMasculino;
    RadioButton radioFeminino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoa_new);

        Intent intent = getIntent();
        mPessoa = (PessoaModel) intent.getSerializableExtra("pessoa");

        context = this;

        ed_nome = findViewById(R.id.editTextNome);
        ed_dataNascimento = findViewById(R.id.editTextDataNascimento);
        ed_cpf = findViewById(R.id.editTextCpf);
        ed_telefone = findViewById(R.id.editTextTelefone);
        ed_email = findViewById(R.id.editTextEmail);

        rg_sexo = findViewById(R.id.radioGroupSexo);
        radioMasculino = findViewById(R.id.radioMasculino);
        radioFeminino = findViewById(R.id.radioFeminino);

        Button salvar = findViewById(R.id.buttonSalvar);
        Button excluir = findViewById(R.id.buttonExcluir);

        salvar.setOnClickListener(this);

        if (mPessoa != null) {
            salvar.setText("Atualizar");
            excluir.setOnClickListener(this);
            carregarInformacoesDoObjetoNaTela();
        } else {
            excluir.setVisibility(View.GONE);
        }

        setMaskData();
        setMaskCpf();
        setMaskCelPhone();
    }

    protected void carregarInformacoesDoObjetoNaTela() {
        ed_nome.setText(mPessoa.getNome());
        ed_cpf.setText(mPessoa.getCpfCnpj());
        ed_telefone.setText(mPessoa.getTelefone1());
        ed_email.setText(mPessoa.getEmail());

        if (mPessoa.getSexo().equals("MASCULINO")) {
            radioMasculino.setChecked(true);
        } else {
            radioFeminino.setChecked(true);
        }

        if (mPessoa.getDtNascimento() != null) {
            String dataNascimento = new SimpleDateFormat("dd/MM/yyyy")
                    .format(new Date(mPessoa.getDtNascimento()));
            ed_dataNascimento.setText(dataNascimento);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonSalvar:
                salvarPessoa();
                break;
            case R.id.buttonExcluir:
                excluirPessoa();
                break;
            default:
                String str = "Não foi encontrado o botão \"" +
                        ((Button) view).getText().toString() + "\" na tela \"" +
                        String.valueOf(this.getTitle()) + "\"";
                showSnackBar(str);
                break;
        }

    }

    public void salvarPessoa() {
        String sexo = radioMasculino.isChecked()?"MASCULINO":"FEMININO";

        Long dataNascimento = null;
        try {
            dataNascimento = new SimpleDateFormat("dd/MM/yyyy")
                    .parse(ed_dataNascimento.getText().toString())
                    .getTime();
        } catch (ParseException e) {
            showSnackBar("Ops.. Algo inesperado ocorreu ao fazer a conversão da data :(");
            e.printStackTrace();
        }

        if (mPessoa == null) {
            //"criar";
            mPessoa = new PessoaModel(ed_nome.getText().toString(),
                    sexo,
                    dataNascimento,
                    ed_cpf.getText().toString(),
                    ed_telefone.getText().toString(),
                    ed_email.getText().toString());

            enviarPost(mPessoa);

        } else {
            //"atualizar";
            mPessoa = new PessoaModel(mPessoa.getId(),
                    ed_nome.getText().toString(),
                    sexo,
                    dataNascimento,
                    ed_cpf.getText().toString(),
                    ed_telefone.getText().toString(),
                    ed_email.getText().toString());

            enviarPost(mPessoa);
        }

        finish();
    }

    public void enviarPost(PessoaModel pessoa) {
        PessoaUtil.getAPIService().insert(pessoa).enqueue(new Callback<PessoaModel>() {
            @Override
            public void onResponse(Call<PessoaModel> call, Response<PessoaModel> response) {

                if (response.isSuccessful()) {
                    Log.i(TAG, "Post subetido na API." + response.body().toString());
                    showSnackBar("Pessoa salva com sucesso!");

                } else {
                    int statusCode = response.code();
                    Log.d(TAG, String.valueOf(statusCode) + " " + response.errorBody().toString());
                    showSnackBar("Ops.. Algo deu errado!");
                }
            }

            @Override
            public void onFailure(Call<PessoaModel> call, Throwable t) {
                Log.e(TAG, "Ops.. Não foi possível fazer o post na API.");
            }
        });
    }

    public void enviarPut(PessoaModel pessoa) {
        PessoaUtil.getAPIService().update(pessoa.getId(), pessoa).enqueue(new Callback<PessoaModel>() {
            @Override
            public void onResponse(Call<PessoaModel> call, Response<PessoaModel> response) {

                if (response.isSuccessful()) {
                    Log.i(TAG, "Put subetido na API." + response.body().toString());
                    showSnackBar("Pessoa atualizada com sucesso!");

                } else {
                    int statusCode = response.code();
                    Log.d(TAG, String.valueOf(statusCode) + " " + response.errorBody().toString());
                    showSnackBar("Ops.. Algo deu errado!");
                }
            }

            @Override
            public void onFailure(Call<PessoaModel> call, Throwable t) {
                Log.e(TAG, "Ops.. Não foi possível fazer o put na API.");
            }
        });
    }

    public void excluirPessoa() {
        enviarDelete(mPessoa);
        finish();
    }

    public void enviarDelete(PessoaModel pessoa) {
        PessoaUtil.getAPIService().delete(pessoa.getId(), pessoa).enqueue(new Callback<PessoaModel>() {
            @Override
            public void onResponse(Call<PessoaModel> call, Response<PessoaModel> response) {

                if (response.isSuccessful()) {
                    Log.i(TAG, "Delete subetido na API.");
                    showSnackBar("Pessoa excluída com sucesso!");
                } else {
                    int statusCode = response.code();
                    Log.d(TAG, String.valueOf(statusCode) + " " + response.errorBody().toString());
                    showSnackBar("Ops.. Algo deu errado!");
                }
            }

            @Override
            public void onFailure(Call<PessoaModel> call, Throwable t) {
                Log.e(TAG, "Ops.. Não foi possível fazer o delete na API.");
            }
        });
    }

    private void showSnackBar(String message) {
        Snackbar.make(getWindow().getDecorView(), message, Snackbar.LENGTH_LONG).show();
    }

    private void setMaskData() {
        ed_dataNascimento.addTextChangedListener(new TextWatcher() {
            boolean isUpdating;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int after) {
                // Quando o texto é alterado o onTextChange é chamado
                // Essa flag evita a chamada infinita desse método
                if (isUpdating) {
                    isUpdating = false;
                    return;
                }
                // Ao apagar o texto, a máscara é removida,
                // então o posicionamento do cursor precisa
                // saber se o texto atual tinha ou não, máscara
                boolean hasMask = s.toString().indexOf('/') > -1;

                // Remove o '.' e '-' da String
                String str = s.toString().replaceAll("[/]", "");

                // os parâmetros before e after dizem o tamanho
                // anterior e atual da String digitada, se after > before é
                // porque está digitando, caso contrário, está apagando
                if (after > before) {
                    // Se tem menos de 10 caracteres (sem máscara)
                    // coloca a '/'
                    if (str.length() < 10) {
                        if (str.length() > 8) {
                            str = str.substring(0, 2) + '/'
                                    + str.substring(2, 4) + '/'
                                    + str.substring(4, 8);

                        } else if (str.length() > 4) {
                            str = str.substring(0, 2) + '/'
                                    + str.substring(2, 4) + '/'
                                    + str.substring(4);

                        } else if (str.length() > 2) {
                            str = str.substring(0, 2) + '/'
                                    + str.substring(2);
                        }
                    }

                    // Seta a flag para evitar chamada infinita
                    isUpdating = true;
                    // seta o novo texto
                    ed_dataNascimento.setText(str);
                    // seta a posição do cursor
                    ed_dataNascimento.setSelection(ed_dataNascimento.getText().length());
                } else {
                    isUpdating = true;
                    ed_dataNascimento.setText(str);

                    // Se estiver apagando posiciona o cursor
                    // no local correto. Isso trata a deleção dos
                    // caracteres da máscara.
                    ed_dataNascimento.setSelection(Math.max(0, Math.min(hasMask ?
                            start - before : start, str.length())));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    private void setMaskCpf() {
        ed_cpf.addTextChangedListener(new TextWatcher() {
            boolean isUpdating;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int after) {
                // Quando o texto é alterado o onTextChange é chamado
                // Essa flag evita a chamada infinita desse método
                if (isUpdating) {
                    isUpdating = false;
                    return;
                }
                // Ao apagar o texto, a máscara é removida,
                // então o posicionamento do cursor precisa
                // saber se o texto atual tinha ou não, máscara
                boolean hasMask = s.toString().indexOf('.') > -1 ||
                        s.toString().indexOf('-') > -1;

                // Remove o '.' e '-' da String
                String str = s.toString().replaceAll("[.]", "")
                        .replaceAll("[-]", "");

                // os parâmetros before e after dizem o tamanho
                // anterior e atual da String digitada, se after > before é
                // porque está digitando, caso contrário, está apagando
                if (after > before) {
                    // Se tem menos de 13 caracteres (sem máscara)
                    // coloca o '.' e o '-'
                    if (str.length() < 13) {
                        if (str.length() > 11) {
                            str = str.substring(0, 3) + '.'
                                    + str.substring(3, 6) + '.'
                                    + str.substring(6, 9) + '-'
                                    + str.substring(9, 11);

                        } else if (str.length() > 9) {
                            str = str.substring(0, 3) + '.'
                                    + str.substring(3, 6) + '.'
                                    + str.substring(6, 9) + '-'
                                    + str.substring(9);

                        } else if (str.length() > 6) {
                            str = str.substring(0, 3) + '.'
                                    + str.substring(3, 6) + '.'
                                    + str.substring(6);

                        } else if (str.length() > 3) {
                            str = str.substring(0, 3) + '.'
                                    + str.substring(3);
                        }
                    }

                    // Seta a flag para evitar chamada infinita
                    isUpdating = true;
                    // seta o novo texto
                    ed_cpf.setText(str);
                    // seta a posição do cursor
                    ed_cpf.setSelection(ed_cpf.getText().length());
                } else {
                    isUpdating = true;
                    ed_cpf.setText(str);

                    // Se estiver apagando posiciona o cursor
                    // no local correto. Isso trata a deleção dos
                    // caracteres da máscara.
                    ed_cpf.setSelection(Math.max(0, Math.min(hasMask ?
                            start - before : start, str.length())));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    private void setMaskCelPhone() {
        ed_telefone.addTextChangedListener(new TextWatcher() {
            boolean isUpdating;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int after) {
                // Quando o texto é alterado o onTextChange é chamado
                // Essa flag evita a chamada infinita desse método
                if (isUpdating) {
                    isUpdating = false;
                    return;
                }
                // Ao apagar o texto, a máscara é removida,
                // então o posicionamento do cursor precisa
                // saber se o texto atual tinha ou não, máscara
                boolean hasMask = s.toString().indexOf('(') > -1 ||
                        s.toString().indexOf(')') > -1 ||
                        s.toString().indexOf(' ') > -1 ||
                        s.toString().indexOf('-') > -1;

                // Remove o '(', ')', ' ' e '-' da String
                String str = s.toString().replaceAll("[(]", "")
                        .replaceAll("[)]", "")
                        .replaceAll(" ", "")
                        .replaceAll("[-]", "");

                // os parâmetros before e after dizem o tamanho
                // anterior e atual da String digitada, se after > before é
                // porque está digitando, caso contrário, está apagando
                if (after > before) {
                    // Se tem menos de 13 caracteres (sem máscara)
                    // coloca o '(', ')', ' ' e '-'
                    if (str.length() < 13) {
                        if (str.length() > 11) {
                            str = '(' + str.substring(0, 1)
                                    + str.substring(1, 2) + ") "
                                    + str.substring(2, 7) + '-'
                                    + str.substring(7, 11);

                        } else if (str.length() > 7) {
                            str = '(' + str.substring(0, 1)
                                    + str.substring(1, 2) + ") "
                                    + str.substring(2, 7) + '-'
                                    + str.substring(7);

                        } else if (str.length() > 2) {
                            str = '(' + str.substring(0, 1)
                                    + str.substring(1, 2) + ") "
                                    + str.substring(2);

                        } else if (str.length() > 1) {
                            str = '(' + str.substring(0, 1)
                                    + str.substring(1);
                        }
                    }

                    // Seta a flag para evitar chamada infinita
                    isUpdating = true;
                    // seta o novo texto
                    ed_telefone.setText(str);
                    // seta a posição do cursor
                    ed_telefone.setSelection(ed_telefone.getText().length());
                } else {
                    isUpdating = true;
                    ed_telefone.setText(str);

                    // Se estiver apagando posiciona o cursor
                    // no local correto. Isso trata a deleção dos
                    // caracteres da máscara.
                    ed_telefone.setSelection(Math.max(0, Math.min(hasMask ?
                            start - before : start, str.length())));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

}
