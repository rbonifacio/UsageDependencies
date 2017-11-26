package br.unb.poo.mh;

/**
 * Interface com a declaracao dos metodos que 
 * "visitam" aplicando alguma operacao sobre 
 * os elementos sintaticos de uma linguagem 
 * de programacao. 
 * @author rbonifacio
 *
 */
public abstract class Visitor<T> {
	protected T resultado;
	
	public abstract void visitar(ValorInteiro exp);
	public abstract void visitar(ValorBooleano exp);
	public abstract void visitar(ExpressaoSoma exp);
	public abstract void visitar(ExpressaoSubtracao exp);
	public abstract void visitar(ExpressaoGT exp);
	public abstract void visitar(Multiplicacao exp);
	public abstract void visitar(IfThenElse exp);
	public abstract void visitar(AplicacaoFuncao exp);
	public abstract void visitar(Identificador exp);
	
	public T getResultado() {
		return resultado;
	}
	
}
