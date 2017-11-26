package br.unb.poo.mh;

public class ExpressaoGT extends ExpressaoBinaria {

	public ExpressaoGT(Expressao expDireita, Expressao expEsquerda) {
		super(expDireita, expEsquerda);
	}

	@Override
	public Valor avaliar() {
		ValorInteiro v1 = (ValorInteiro)expEsquerda.avaliar();
		ValorInteiro v2 = (ValorInteiro)expDireita.avaliar();
		
		return new ValorBooleano(v1.getValor() > v2.getValor());
	}

	@Override
	public Tipo tipo() {
		return expDireita.tipo() == Tipo.Inteiro &&
			   expEsquerda.tipo() == Tipo.Inteiro ?
					   Tipo.Booleano : Tipo.Error;
	}

	@Override
	public void aceitar(Visitor v) {
		v.visitar(this);
	}

}
