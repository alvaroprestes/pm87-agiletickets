package br.com.caelum.agiletickets.models;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Test;

public class EspetaculoTest {

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.Vagas(5));
	}

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.Vagas(6));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.Vagas(15));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.Vagas(5, 3));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.Vagas(10, 3));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.Vagas(5, 3));
	}

	private Sessao sessaoComIngressosSobrando(int quantidade) {
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(quantidade * 2);
		sessao.setIngressosReservados(quantidade);

		return sessao;
	}

	@Test
	public void criaUmaSessaoSeComecaETerminaHoje() throws Exception {
		Espetaculo espetaculo = new Espetaculo();
		LocalDate inicio = new LocalDate(2015, 5, 11);
		LocalDate fim = new LocalDate(2015, 5, 11);
		LocalTime horario = new LocalTime(20, 0, 0);
		List<Sessao> sessoes = espetaculo.criaSessoes(inicio, fim, horario, Periodicidade.DIARIA);
		
		assertEquals(1, sessoes.size());
		System.out.println(inicio.toDateTime(horario));
		assertEquals(inicio.toDateTime(horario), sessoes.get(0).getInicio());
		assertEquals(espetaculo, sessoes.get(0).getEspetaculo());
	}

	@Test
	public void criaTresSessoesSeComecaHojeETerminaDepoisDeAmanhaEmPeriodicidadeDiaria() throws Exception {
		Espetaculo espetaculo = new Espetaculo();
		LocalDate inicio = new LocalDate(2015, 5, 11);
		LocalDate fim = new LocalDate(2015, 5, 13);
		LocalTime horario = new LocalTime(20, 0, 0);
		List<Sessao> sessoes = espetaculo.criaSessoes(inicio, fim, horario, Periodicidade.DIARIA);
		
		assertEquals(3, sessoes.size());
		assertEquals(inicio.toDateTime(horario), sessoes.get(0).getInicio());
		assertEquals(espetaculo, sessoes.get(0).getEspetaculo());

		assertEquals(inicio.plusDays(1).toDateTime(horario), sessoes.get(1).getInicio());
		assertEquals(espetaculo, sessoes.get(1).getEspetaculo());

		assertEquals(fim.toDateTime(horario), sessoes.get(2).getInicio());
		assertEquals(espetaculo, sessoes.get(2).getEspetaculo());
	}

	@Test
	public void criaUmaSessaoSeComecaHojeETerminaAmanhaComPeriodicidadeSemanal() throws Exception {
		Espetaculo espetaculo = new Espetaculo();
		LocalDate inicio = new LocalDate(2015, 5, 11);
		LocalDate fim = new LocalDate(2015, 5, 11);
		LocalTime horario = new LocalTime(20, 0, 0);
		List<Sessao> sessoes = espetaculo.criaSessoes(inicio, fim, horario, Periodicidade.SEMANAL);
		
		inicio = inicio.plusDays(1);
		assertEquals(1, sessoes.size());
		System.out.println(inicio.toDateTime(horario));
		assertEquals(inicio.toDateTime(horario), sessoes.get(0).getInicio());
		assertEquals(espetaculo, sessoes.get(0).getEspetaculo());
	}
}
