package lao.treinacerebrosimples;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private double resultadoOperacao = 0 ;
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    TextView txtStatus;
    int btnRespostaCorreta = 0;
    CheckBox chkAdicao;
    CheckBox chkSubtracao;
    CheckBox chkDivisao;
    CheckBox chkMultiplicacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = (Button) findViewById(R.id.idBtn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                verificaSeAcertou(1);
            }
        });

        btn2 = (Button) findViewById(R.id.idBtn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                verificaSeAcertou(2);
            }
        });

        btn3 = (Button) findViewById(R.id.idBtn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                verificaSeAcertou(3);
            }
        });

        btn4 = (Button) findViewById(R.id.idBtn4);
        btn4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                verificaSeAcertou(4);
            }
        });

        chkAdicao = (CheckBox) findViewById(R.id.idchkAdicao);
        chkSubtracao = (CheckBox) findViewById(R.id.idChkSubtracao);
        chkDivisao = (CheckBox) findViewById(R.id.idChkDivisao);
        chkMultiplicacao = (CheckBox) findViewById(R.id.idChkMultiplicacao);

        txtStatus = (TextView) findViewById(R.id.idLblStatus);
        geraDesafio();

        Button btnAplicarNovaConfiguracao = (Button) findViewById(R.id.idAplicar);
        btnAplicarNovaConfiguracao.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                geraDesafio();
            }
        });
    }

    private void verificaSeAcertou(int botaoClicado) {
        if ( btnRespostaCorreta == botaoClicado ) {
            txtStatus.setText("acertou : " + getTextoSemVirgulaSePuder(resultadoOperacao));
            geraDesafio();
        }
        else {
            txtStatus.setText("ops... Tente novamente!");
        }
    }

    private void geraDesafio() {

        TextView txtOperacao = (TextView) findViewById(R.id.idLblOperacao);
        geraNovoDesafio(txtOperacao);

        double randonColocarBotao = resultadoOperacao;

        ArrayList<Integer> naoPodeSer = new ArrayList<>();
        naoPodeSer.add((int)randonColocarBotao);

        ArrayList<Integer> naoPodeSerBotoes = new ArrayList<>();
        limpaBotoes();

        while ( true ) {

            int escolheBotaoRandom = getRandom(4, naoPodeSerBotoes);
            naoPodeSerBotoes.add(escolheBotaoRandom);

            if ( randonColocarBotao == -1 ) {
                if ( ((int)resultadoOperacao*2) <= 3 ) {
                    randonColocarBotao = getRandom(5, naoPodeSer); // need some numbers to fill all buttons
                }
                else {
                    randonColocarBotao = getRandom(((int)resultadoOperacao*2), naoPodeSer);
                }
                naoPodeSer.add((int)randonColocarBotao);
            }
            else {
                btnRespostaCorreta = escolheBotaoRandom;
            }

            colocarValorNosBotoes(randonColocarBotao, escolheBotaoRandom);

            if ( isBotoesPreenchidos() ) {
                break;
            }
            System.out.println(" escolheBotaoRandom = " + escolheBotaoRandom + ", randonColocarBotao = " + randonColocarBotao);
            randonColocarBotao = -1;
        }
    }

    private void geraNovoDesafio(TextView txtOperacao) {
        txtOperacao.setText("");

        int maior = getMaiorOperador();

        criaAdicao(txtOperacao, maior);
        criaSubtracao(txtOperacao, maior);
        criaMultiplicacao(txtOperacao, maior);
        criaDivisao(txtOperacao, maior);

        txtOperacao.setText(txtOperacao.getText() + " = ? ");
    }

    private int getMaiorOperador() {
        TextView txtMaior = (TextView) findViewById(R.id.idTxtMaior);
        if (    txtMaior.getText().toString().trim().length() == 0 ||
                txtMaior.getText().toString().trim().equals("0") ||
                txtMaior.getText().toString().trim().length() > 3 ) {

            txtMaior.setText("9"); // default
        }
        return Integer.parseInt(txtMaior.getText().toString());
    }

    private void colocarValorNosBotoes(double randonColocarBotao, int escolheBotaoRandom) {

        if ( escolheBotaoRandom == 1 ) {
            btn1.setText(getTextoSemVirgulaSePuder(randonColocarBotao));
        }
        if ( escolheBotaoRandom == 2 ) {
            btn2.setText(getTextoSemVirgulaSePuder(randonColocarBotao));
        }
        if ( escolheBotaoRandom == 3 ) {
            btn3.setText(getTextoSemVirgulaSePuder(randonColocarBotao));
        }
        if ( escolheBotaoRandom == 4 ) {
            btn4.setText(getTextoSemVirgulaSePuder(randonColocarBotao));
        }
    }

    private String getTextoSemVirgulaSePuder(double randonColocarBotao) {
        if ( randonColocarBotao - (int)randonColocarBotao > 0 ) {
            return "" + randonColocarBotao;
        }
        return "" + (int)randonColocarBotao;
    }

    private void criaDivisao(TextView txtOperacao, int maior) {
        if ( chkDivisao.isChecked() ) {
            int numberUsadoA = getRandom(maior, null);
            if ( txtOperacao.getText().toString().length() != 0 ) {
                txtOperacao.setText( txtOperacao.getText() + " / " + numberUsadoA);
                resultadoOperacao = round( resultadoOperacao / numberUsadoA,1);

            }
            else {
                int numberUsadoB = getRandom(maior, null);
                txtOperacao.setText( "" + numberUsadoA + " / " + numberUsadoB );
                resultadoOperacao = round((double) numberUsadoA / numberUsadoB, 1);
            }
        }
    }

    private void criaMultiplicacao(TextView txtOperacao, int maior) {
        if ( chkMultiplicacao.isChecked() ) {
            int numberUsadoA = getRandom(maior, null);
            if ( txtOperacao.getText().toString().length() != 0 ) {
                txtOperacao.setText( " ( " + txtOperacao.getText() + " ) X " + numberUsadoA);
                resultadoOperacao = resultadoOperacao * numberUsadoA;
            }
            else {
                int numberUsadoB = getRandom(maior, null);
                txtOperacao.setText( "" + numberUsadoA + " X " + numberUsadoB );
                resultadoOperacao = numberUsadoA * numberUsadoB;
            }
        }
    }

    private void criaSubtracao(TextView txtOperacao, int maior) {
        if ( chkSubtracao.isChecked() ) {
            int numberUsadoA = getRandom(maior, null);
            if ( txtOperacao.getText().toString().length() != 0 ) {
                txtOperacao.setText( " ( " + txtOperacao.getText() + " ) - " + numberUsadoA );
                resultadoOperacao = resultadoOperacao - numberUsadoA;
            }
            else {
                int numberUsadoB = getRandom(maior, null);
                txtOperacao.setText( "" + numberUsadoA + " - " + numberUsadoB );
                resultadoOperacao = numberUsadoA - numberUsadoB;
            }
        }
    }

    private void criaAdicao(TextView txtOperacao, int maior) {
        if ( chkAdicao.isChecked() ) {
            int numberUsadoA = getRandom(maior, null);
            int numberUsadoB = getRandom(maior, null);
            txtOperacao.setText( "" + numberUsadoA + " + " + numberUsadoB );
            resultadoOperacao = numberUsadoA + numberUsadoB;
        }
    }

    private static double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    private int getRandom(int maior, ArrayList<Integer> naoPodeSer) {
        int random = -1;
        while ( random == -1 ) {
            random = ( (int) (Math.random() * (maior)) ) + 1 ; // 1..maior
            System.out.println(" random 1 = " + random );
            if ( naoPodeSer != null ) {
                for (int naoPodeSerIgual : naoPodeSer) {
                    System.out.println(" naopodeser = " + naoPodeSerIgual);
                    if ( naoPodeSerIgual == random) {
                        random = -1; // bad, generated a new one
                    }
                }
            }
            System.out.println(" random 2 = " + random );
        }
        return random;
    }

    private boolean isBotoesPreenchidos() {
        return  btn1.getText().toString().trim().length() != 0 &&
                btn2.getText().toString().trim().length() != 0 &&
                btn3.getText().toString().trim().length() != 0 &&
                btn4.getText().toString().trim().length() != 0 ;
    }

    private void limpaBotoes() {
        btn1.setText("");
        btn2.setText("");
        btn3.setText("");
        btn4.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
