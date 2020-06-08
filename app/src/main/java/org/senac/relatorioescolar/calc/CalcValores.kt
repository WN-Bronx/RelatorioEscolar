package org.senac.relatorioescolar.calc

class CalcValores(var aluno : String, val nt1 : Double, val nt2 : Double, val nt3 : Double) {

    fun AlunosAprovado() : Boolean{
        return getMediaAluno() > 7
    }

    fun getMediaAluno(): Double{
        return (nt1 + nt2 + nt3)/3
    }

}