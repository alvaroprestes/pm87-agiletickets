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
			//quando estiver acabando os ingressos... 
			double quantoAumenta = 0.10;
			double porcentagemParaUltimasVagas = 0.05;

			if(porcentagemReservada <= porcentagemParaUltimasVagas) { 
				preco = precoOriginal.add(precoOriginal.multiply(BigDecimal.valueOf(quantoAumenta)));
			} else {
				preco = precoOriginal;
			}
		} else if(sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.BALLET)) {
			double quantoAumenta = 0.20;
			double porcentagemParaUltimasVagas = 0.50;

			if(porcentagemReservada <= porcentagemParaUltimasVagas) {
				preco = precoOriginal.add(precoOriginal.multiply(BigDecimal.valueOf(quantoAumenta)));
			} else {
				preco = precoOriginal;
			}
			
			if(sessao.getDuracaoEmMinutos() > 60){
				preco = preco.add(precoOriginal.multiply(BigDecimal.valueOf(0.10)));
			}
		} else if(sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.ORQUESTRA)) {
			double quantoAumenta = 0.20;
			double porcentagemParaUltimasVagas = 0.50;
			
			if(porcentagemReservada <= porcentagemParaUltimasVagas) { 
				preco = precoOriginal.add(precoOriginal.multiply(BigDecimal.valueOf(quantoAumenta)));
			} else {
				preco = precoOriginal;
			}

			if(sessao.getDuracaoEmMinutos() > 60){
				preco = preco.add(precoOriginal.multiply(BigDecimal.valueOf(0.10)));
			}
		}  else {
			//nao aplica aumento para teatro (quem vai é pobretão)
			preco = precoOriginal;
		} 

		return preco.multiply(BigDecimal.valueOf(quantidade));
	}

}