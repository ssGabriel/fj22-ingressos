package br.com.caelum.ingresso.validacao;

import static org.junit.Assert.*;

import java.io.File;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;

public class GerenciadorDeSessaoTest {
@Test
public void garanteQueNãoVaiPermitirSessaoNoMesmoHorario(){
	Filme filme = new Filme("Rougue One", Duration.ofMinutes(120),"SCI-FI",BigDecimal.ONE);
	LocalTime horario= LocalTime.parse("10:00:00");
	
	Sala sala =  new Sala("",BigDecimal.ONE);
	List<Sessao> sessoes = Arrays.asList(new Sessao(horario,filme,sala));
	
	Sessao sessao = new Sessao(horario,filme,sala);
	
	GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoes);
	
	Assert.assertFalse(gerenciador.cabe(sessao));
}
	@Test
	public void garanteQueNaoVaiPermitirSessoesIniciandoDentroDoHorarioDeUmaSessaoJaExistente(){
		Filme filme = new Filme("Rougue One",Duration.ofMinutes(120),"SCI-FI",BigDecimal.ONE);
		LocalTime horario = LocalTime.parse("10:00:00");
		
		Sala sala = new Sala("",BigDecimal.ONE);
		List<Sessao> sessoes = Arrays.asList(new Sessao(horario,filme,sala));
		
		Sessao sessao = new Sessao(horario.minusHours(1),filme,sala);
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoes);
		
		Assert.assertFalse(gerenciador.cabe(sessao));
	}
	
	@Test
	public void garanteQueNaoDevePermitirSessoesIniciandoDentroDoHorarioDeUmaSessaoJaExistente()
	{
		Filme filme = new Filme("Rogue One", Duration.ofMinutes(120),"SCI-FI",BigDecimal.ONE);
		LocalTime horario = LocalTime.parse("10:00:00");
		Sala sala =new Sala("",BigDecimal.ONE);
		
		List<Sessao> sessoesDaSala = Arrays.asList(new Sessao(horario,filme,sala));
		
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoesDaSala);
		Assert.assertFalse(gerenciador.cabe(new Sessao(horario.plusHours(1),filme,sala)));
		
	}
	
	@Test
	public void garanteQueDevePermitirUmaInsercaoEntreDoisFilmes(){
		
		Sala sala = new Sala("",BigDecimal.ONE);
		
		Filme filme1 = new Filme("Rogue One", Duration.ofMinutes(120),"SCI-FI",BigDecimal.ONE);
		LocalTime dezHoras = LocalTime.parse("10:00:00");
		Sessao sessaoDasDez = new Sessao(dezHoras,filme1,sala);
		
		
		Filme filme2 = new Filme("Rogue One", Duration.ofMinutes(120),"SCI-FI",BigDecimal.ONE);
		LocalTime dezoitoHoras = LocalTime.parse("18:00:00");
		Sessao sessaoDasDezoito = new Sessao(dezHoras,filme2,sala);
		
		List<Sessao> sessoes = Arrays.asList(sessaoDasDez,sessaoDasDezoito);
		
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoes);
		
		Assert.assertTrue(gerenciador.cabe(new Sessao(LocalTime.parse("13:00:00"),filme2,sala)));
		
		
	}
	
	public class SessaoTest{
		@Test
		public void oPrecoDasSessaoDeveSerIgualASomaDoPrecoDaSalaMaisPrecoDoFilme(){
			
			Sala sala = new Sala("Eldorado = Imax", new BigDecimal("22.5"));
			Filme filme = new Filme ("Rooge Oner", Duration.ofMinutes(120),
					"SIC_FI",new BigDecimal("12.0"));
			BigDecimal somaDosPrecosDaSalaEFilme = sala.getPreco().add(filme.getPreco());
			Sessao sessao = new Sessao (LocalTime.parse("10:00:00"),filme,sala);
			
			assertEquals(somaDosPrecosDaSalaEFilme,sessao.getPreco());
		}
	}
}
