package br.unb.poo.mh;

public class PrettyPrinter extends Visitor<StringBuffer>{

	public PrettyPrinter() {
		resultado = new StringBuffer();
	}
	@Override
	public void visitar(ValorInteiro exp) {
		resultado.append(exp.getValor());
	}

	@Override
	public void visitar(ValorBooleano exp) {
		resultado.append(exp.getValor());
	}

	public void visitarEB(ExpressaoBinaria exp, String operador) {
		resultado.append("(");
		exp.expEsquerda.aceitar(this);
		resultado.append(operador);
		exp.expDireita.aceitar(this);
		resultado.append(")");
		
	}
	
	@Override
	public void visitar(ExpressaoSoma exp) {
		visitarEB(exp, "+");
	}
	
	@Override
	public void visitar(ExpressaoSubtracao exp) {
		visitarEB(exp, "-");
	}

	@Override
	public void visitar(ExpressaoGT exp) {
		visitarEB(exp, ">");
	}
	
	@Override
	public void visitar(Multiplicacao exp) {
		visitarEB(exp, "*");
	}

	@Override
	public void visitar(IfThenElse exp) {
		resultado.append("if(");
		exp.condicao.aceitar(this);
		resultado.append(")\n");
		
		resultado.append("then ");
		exp.clausulaThen.aceitar(this);
		
		resultado.append(" else " );
		exp.clausulaElse.aceitar(this);
	}

	@Override
	public void visitar(AplicacaoFuncao exp) {
		System.out.print(exp.nome);
		resultado.append("(");
		int i = 0;
		while(i < exp.parametros.size() - 1) {
			exp.parametros.get(i++).aceitar(this);
			resultado.append(",");
		}
		if(i == exp.parametros.size() - 1) {
			exp.parametros.get(i++).aceitar(this);
		}
		resultado.append(")");
	}

	@Override
	public void visitar(Identificador exp) {
		resultado.append(exp.id);
	}
	

}
