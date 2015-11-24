package unidade;

import static org.fest.assertions.Assertions.assertThat;
import models.Dica;
import models.DicaConselho;
import models.DicaMaterial;
import models.Disciplina;
import models.Tema;
import models.User;

import org.junit.Before;
import org.junit.Test;

public class TemaTest {
	Disciplina disciplina;
	Tema tema;
	Dica dica1, dica2, dica3;
	User user1, user2, user3;
	
	@Before
	public void before() {
		disciplina = new Disciplina("Literatura Clássica I");
		tema = new Tema("Shakespeare", disciplina);
		
		user1 = new User("walt_the_man@whitman.com", "poetryisbeautiful", "walty");
		dica1 = new DicaConselho("Comece por Hamlet");
		user1.setNome("Walt Whitman");
		dica1.setUser(user1.getNome());
		
		user2 = new User("allan@poe.com", "raven", "poe");
		dica2 = new DicaMaterial("http://www.suicidemethods.net/suicide-methods/");
		user2.setNome("Edgar Allan Poe");
		dica2.setUser(user2.getNome());
		
		user3 = new User("augusto_dos@njos.com", "escarro", "morte");
		dica3 = new DicaConselho("Vês! Ninguém assistiu ao formidável enterro de tua última quimera! Desista do curso.");
		user3.setNome("Augustus Angelicus");
		dica3.setUser(user3.getNome());
	}
	
	/**
	 * Testa se a ligação entre tema e disciplina está okay.
	 */
	@Test
	public void deveEstarAssociadaAUmaDisciplina() {
		tema.setDisciplina(disciplina);
		disciplina.addTema(tema);
		
		assertThat(tema.getDisciplina().getNome()).isEqualTo("Literatura Clássica I");
		assertThat(disciplina.getTemaByNome("Shakespeare")).isEqualTo(tema);
	}
	
	/**
	 * Testa se as médias e medianas de dificuldade de um tema estão sendo calculadas corretamente. 
	 */
	@Test
	public void deveCalcularCorretamenteMediaEMediana() {
		tema.incrementarDificuldade(user1.getLogin(), -2);
		assertThat(tema.getMedia()).isEqualTo("-2,00");
		assertThat(tema.getMediana()).isEqualTo("-2");
		tema.incrementarDificuldade(user2.getLogin(), 1);
		assertThat(tema.getMedia()).isEqualTo("-0,50");
		assertThat(tema.getMediana()).isEqualTo("-0,50");
		tema.incrementarDificuldade(user3.getLogin(), 0);
		assertThat(tema.getMedia()).isEqualTo("-0,33");
		assertThat(tema.getMediana()).isEqualTo("0");
	}
	
	/**
	 * Testa se um User pode reavaliar a dificuldade de um tema.
	 */
	@Test
	public void deveSerPossivelReavaliarDificuldadeDoTema() {
		tema.incrementarDificuldade(user1.getLogin(), -1);
		
		assertThat(tema.getMedia()).isEqualTo("-1,00");
		assertThat(tema.getMediana()).isEqualTo("-1");
		
		tema.incrementarDificuldade(user1.getLogin(), -2);
		
		assertThat(tema.getMedia()).isEqualTo("-2,00");
		assertThat(tema.getMediana()).isEqualTo("-2");
	}
	
	/**
	 * Testa se o tema adiciona dicas corretamente.
	 */
	@Test
	public void deveAdicionarDicas() {
		tema.addDica(dica1);
		tema.addDica(dica2);
		tema.addDica(dica3);
		
		assertThat(tema.getDicas()).contains(dica1);
		assertThat(tema.getDicas()).contains(dica2);
		assertThat(tema.getDicas()).contains(dica3);
		
		assertThat(tema.getDicas().get(0).getTexto()).isEqualTo("Comece por Hamlet");
	}
}
