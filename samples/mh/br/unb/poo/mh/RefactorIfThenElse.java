package br.unb.poo.mh;

public class RefactorIfThenElse extends VisitorAdapter<Expressao>{


	@Override
	public void visitar(IfThenElse exp) {
		Expressao expThen = exp.clausulaThen;
		Expressao expElse = exp.clausulaElse;
		ValorBooleano vt = new ValorBooleano(true);
		ValorBooleano vf = new ValorBooleano(false); 
		
		if(expThen.equals(vt) && expElse.equals(vf)) {
			resultado = exp.condicao;
		}
		else {
			resultado = exp; 
		}
	}
}
