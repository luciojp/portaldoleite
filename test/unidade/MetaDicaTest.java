package unidade;

import static org.fest.assertions.Assertions.assertThat;
import models.Dica;
import models.DicaAssunto;
import models.Disciplina;
import models.MetaDica;
import models.Tema;
import models.User;

import org.junit.Before;
import org.junit.Test;

public class MetaDicaTest {
	Disciplina disciplina;
	Tema tema;
	Dica dica1;
	MetaDica metadica1, metadica2;
	User user;
	
	@Before
	public void before() {
		disciplina = new Disciplina("Estruturas de Dados e Algoritmos");
		tema = new Tema("Árvore balanceada", disciplina);
		
		dica1 = new DicaAssunto("Taoísmo");
		user = new User("l@o.tse", "yinyang", "loltsez");
		user.setNome("Lao Tsé");
		metadica1 = new MetaDica(disciplina, user.getLogin(), "Only the wise know true balance");
		metadica2 = new MetaDica(disciplina, user.getLogin(), "This is the best tip. The wise can see it");
		
	}
	
	/**
	 * Testa se as dicas são corretamente adicionadas à metadica.
	 */
	@Test
	public void deveAdicionarDica() {
		metadica1.addDica(dica1);
		
		assertThat(metadica1.getDicasAdicionadas().get(0)).isEqualTo(dica1);
	}
	
	/**
	 * Testa se as metadicas são corretamente adicionadas à metadica.
	 */
	@Test
	public void deveAdicionarMetaDica() {
		metadica1.addDica(dica1);
		
		metadica2.addMetaDica(metadica1);
		
		assertThat(metadica2.getMetaDicasAdicionadas().get(0)).isEqualTo(metadica1);
		assertThat(metadica2.getMetaDicasAdicionadas().get(0).getDicasAdicionadas().get(0).getTexto()).isEqualTo("Taoísmo");
	}
	
	/**
	 * Testa se as funcionalidades de incrementar concordâncias e discordâncias estão corretas.
	 */
	@Test
	public void deveIncrementarConcordanciasEDiscordancias() {
		assertThat(metadica1.getConcordancias()).isEqualTo(0);
		metadica1.incrementaConcordancias();
		assertThat(metadica1.getConcordancias()).isEqualTo(1);
		
		assertThat(metadica1.getDiscordancias()).isEqualTo(0);
		metadica1.incrementaDiscordancias();
		assertThat(metadica1.getDiscordancias()).isEqualTo(1);
	}
	
	/**
	 * Testa se os comentários discordantes são devidamente acrescentados à metadica.
	 */
	@Test
	public void deveAdicionarComentariosDeDiscordantes() {
		metadica1.addUserCommentary(user.getNome(), "I go back on my indication, wisdom sucks, yoloswag 420 blaze it");
		assertThat(metadica1.getUsersCommentaries().get("Lao Tsé")).isEqualTo("I go back on my indication, wisdom sucks, yoloswag 420 blaze it");
	}
	
	/**
	 * Testa se a funcionalidade de flag numa metadica está correta.
	 */
	@Test
	public void deveIncrementarFlag() {
		assertThat(metadica1.getFlag()).isEqualTo(0);
		metadica1.incrementaFlag();
		assertThat(metadica1.getFlag()).isEqualTo(1);
	}
}