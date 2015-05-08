package br.com.caelum.agiletickets.domain.precos;

import java.math.BigDecimal;

import br.com.caelum.agiletickets.models.Sessao;
import br.com.caelum.agiletickets.models.TipoDeEspetaculo;

public class CalculadoraDePrecos {

	public BigDecimal calcula(Sessao sessao, Integer quantidade) {
		BigDecimal precoOriginal = sessao.getPreco();
		double porcentagemReservada = (sessao.getTotalIngressos() - sessao.getIngressosReservados()) / sessao.getTotalIngressos().doubleValue();

		BigDecimal preco;
		
		if(sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.CINEMA) || sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.SHOW)) {
			preco = calculaPrecoPorLugaresAcabando(porcentagemReservada, sessao);
		} else if(sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.BALLET)) {
			preco = calculaPrecoPorLugaresAcabando(porcentagemReservada, sessao);
			preco = calculaPrecoPorDuracao(sessao, preco);
			
		} else if(sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.ORQUESTRA)) {
			preco = calculaPrecoPorLugaresAcabando(porcentagemReservada, sessao);

			preco = calculaPrecoPorDuracao(sessao, preco);
		}  else {
			//nao aplica aumento para teatro (quem vai é pobretão)
			preco = precoOriginal;
		} 

		return preco.multiply(BigDecimal.valueOf(quantidade));
	}

	private BigDecimal calculaPrecoPorDuracao(Sessao sessao, BigDecimal precoAtual) {
		BigDecimal precoOriginal = sessao.getPreco();
		if(sessao.getDuracaoEmMinutos() > 60){
			precoAtual = precoAtual.add(precoOriginal.multiply(BigDecimal.valueOf(0.10)));
		}
		return precoAtual;
	}

	private BigDecimal calculaPrecoPorLugaresAcabando(double porcentagemReservada, Sessao sessao) {
		TipoDeEspetaculo tipo = sessao.getEspetaculo().getTipo();
		BigDecimal precoOriginal = sessao.getPreco();

		if(porcentagemReservada <= tipo.getPercentualAumentoPorOcupacao()) { 
			return precoOriginal.add(precoOriginal.multiply(BigDecimal.valueOf(tipo.getPercentualAumento())));
		} else {
			return precoOriginal;
		}
	}

}