package br.unb.poo.mh;

public class TamanhoDasExpressoes extends Visitor<Integer> {

	public TamanhoDasExpressoes() {
		resultado = 0;
	}
	
	
	@Override
	public void visitar(ValorInteiro exp) {
		resultado += 1;
	}

	@Override
	public void visitar(ValorBooleano exp) {
		resultado += 1;
	}
	
	public void visitarEB(ExpressaoBinaria exp) {
		 exp.expEsquerda.aceitar(this);
		 exp.expDireita.aceitar(this);
		 resultado += 1;
	}

	@Override
	public void visitar(ExpressaoSoma exp) {
		visitarEB(exp);
	}

	@Override
	public void visitar(ExpressaoGT exp) {
		visitarEB(exp);
	}

	@Override
	public void visitar(Multiplicacao exp) {
		visitarEB(exp);
	}

	@Override
	public void visitar(IfThenElse exp) {
		exp.condicao.aceitar(this);
		exp.clausulaThen.aceitar(this);
		exp.clausulaElse.aceitar(this);
		resultado += 1;
	}

	@Override
	public void visitar(AplicacaoFuncao exp) {
		exp.parametros.stream().forEach(p -> { p.aceitar(this); });
		resultado += 1;
		
//		for(Expressao p: exp.parametros) {
//			p.aceitar(this);
//		}
	}

	@Override
	public void visitar(Identificador exp) {
		resultado += 1;
	}


	@Override
	public void visitar(ExpressaoSubtracao exp) {
		visitarEB(exp);
	}

}
