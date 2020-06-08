package org.senac.relatorioescolar

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.KeyEventDispatcher
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.senac.relatorioescolar.calc.CalcValores

class MainActivity : AppCompatActivity() {

    private lateinit var alunoComponente: EditText
    private lateinit var nt1Componente: EditText
    private lateinit var nt2Componente: EditText
    private lateinit var nt3Componente: EditText

    private var CalcValores : MutableList<CalcValores> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        alunoComponente = findViewById(R.id.etAluno)
        nt1Componente = findViewById(R.id.etNota1)
        nt2Componente = findViewById(R.id.etNota2)
        nt3Componente = findViewById(R.id.etNota3)
    }

    fun addAluno(view: View){
        if (validador()){
            val nome :String = alunoComponente.text.toString()
            val nt1 :Double = nt1Componente.text.toString().toDouble()
            val nt2 :Double = nt2Componente.text.toString().toDouble()
            val nt3 :Double = nt3Componente.text.toString().toDouble()

            CalcValores.add(CalcValores(nome, nt1, nt2, nt3))

            alunoComponente.setText("")
            nt1Componente.setText("")
            nt2Componente.setText("")
            nt3Componente.setText("")

            Toast.makeText(this, " Cadastro Realizado com Adicionado ", Toast.LENGTH_SHORT).show()

        }
    }

    fun exibirRelatorio(view: View) {
        if (restricaodeCadastro()) {
            val Aprovados = CalcValores.filter { a -> a.AlunosAprovado() }.map {a -> a.aluno }.toString()
            val media = "%.2f".format(CalcValores.map { a -> a.getMediaAluno() }.reduce { acc, d -> acc + d } / CalcValores.count())
            val qtdAprovados = CalcValores.filter { a -> a.AlunosAprovado() }.count()
            val qdtReprovados = CalcValores.filter { a -> !a.AlunosAprovado() }.count()
            val melhorAluno = CalcValores.maxBy { a -> a.getMediaAluno() }?.aluno
            val melhorNota1 = CalcValores.maxBy { a -> a.nt1 }?.aluno
            val melhorNota2 = CalcValores.maxBy { a -> a.nt2 }?.aluno
            val melhorNota3 = CalcValores.maxBy { a -> a.nt3 }?.aluno

            var exibir = StringBuilder()
                exibir.append(" O nome dos alunos aprovados : ${Aprovados}").append("\n")
                exibir.append(" A média da turma em cada uma das prova : ${media}").append("\n")
                exibir.append(" O número de alunos aprovados : ${qtdAprovados}").append("\n")
                exibir.append(" O número de alunos reprovados : ${qdtReprovados}").append("\n")
                exibir.append(" O Melhor aluno da turma : ${melhorAluno}").append("\n")
                exibir.append(" Melhor aluno na prova 1 : ${melhorNota1}").append("\n")
                exibir.append(" Melhor aluno na prova 2 : ${melhorNota2}").append("\n")
                exibir.append(" Melhor aluno na prova 3 : ${melhorNota3}").append("\n")

            val alertDialogBuilder = AlertDialog.Builder(this@MainActivity)
                alertDialogBuilder.setTitle(" Resultados das Notas/Provas ")
                alertDialogBuilder.setMessage(exibir.toString())
                alertDialogBuilder.setNeutralButton(" [ Confirmar ] ") { _, _->}

                alertDialogBuilder.create().show()
        }
    }

    private fun restricaodeCadastro() : Boolean{
        var validador = true

        if (CalcValores.count() < 10){
            Toast.makeText(this, "O mínimo de Alunos é 10, atualmente tem ${CalcValores.count()}", Toast.LENGTH_SHORT).show()
            validador = false
        }
        return validador
    }


    fun validador() : Boolean {
        var validador = true

        if (alunoComponente.text.trim().length == 0) {
            alunoComponente.setError(" Informe o nome do aluno ")
            validador= false
        }

        if (nt1Componente.text.trim().length == 0) {
            nt1Componente.setError(" Informe a primeira nota ")
            validador= false
        }

        if (nt2Componente.text.trim().length == 0) {
            nt2Componente.setError(" Informe a primeira nota ")
            validador= false
        }

        if (nt3Componente.text.trim().length == 0) {
            nt3Componente.setError(" Informe a primeira nota ")
            validador= false
        }

        return validador
    }

}