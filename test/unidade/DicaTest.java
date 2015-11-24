package unidade;

import static org.fest.assertions.Assertions.assertThat;
import models.Dica;
import models.DicaAssunto;
import models.DicaConselho;
import models.DicaDisciplina;
import models.DicaMaterial;
import models.Disciplina;
import models.Tema;
import models.User;

import org.junit.Before;
import org.junit.Test;

public class DicaTest {
	Disciplina disciplina;
	Tema tema;
	Dica dica1, dica2, dica3, dica4;
	User user;
	
	@Before
	public void before() {
		disciplina = new Disciplina("Álgebra Linear");
		tema = new Tema("Transformações lineares", disciplina);
		
		dica1 = new DicaAssunto("LaPlace");
		dica2 = new DicaConselho("Não subestime a última prova");
		dica3 = new DicaMaterial("https://www.youtube.com/watch?v=NyAp-3QXdC0");
		dica4 = new DicaDisciplina("Álgebra Vetorial", "Pré-requisito importante");
		
		user = new User("schopenh@uer.com", "lifesucks", "schops");
	}
	
	/**
	 * Testa se a dica está armazenando votos corretamente.
	 */
	@Test
	public void devePoderReceberVotos() {
		assertThat(dica1.getTexto()).isEqualTo("LaPlace");
		assertThat(dica1.getConcordancias()).isEqualTo(0);
		
		dica1.incrementaConcordancias();
		
		assertThat(dica1.getConcordancias()).isEqualTo(1);
	}
	/**
	 * Testa se a dica está sendo conectada a um tema corretamente.
	 */
	@Test
	public void deveEstarAssociadoATema() {
		dica1.setTema(tema);
		tema.addDica(dica1);
		
		assertThat(dica1.getTema().getDicas().get(0)).isEqualTo(dica1);
		assertThat(dica1.getTema().getName()).isEqualTo("Transformações lineares");
	}
	
	/**
	 * Testa se a recuperação de qual o tipo da dica está sendo feita corretamente.
	 */
	@Test
	public void deveSerDeUmaInstanciaEspecificaDeDica() {
		assertThat(dica1.getTipo()).isEqualTo("DicaAssunto");
		assertThat(dica2.getTipo()).isEqualTo("DicaConselho");
		assertThat(dica3.getTipo()).isEqualTo("DicaMaterial");
		assertThat(dica4.getTipo()).isEqualTo("DicaDisciplina");
	}
	
	/**
	 * Testa se a dica está armazenando corretamente os comentários discordantes.
	 */
	@Test
	public void deveArmazenarComentarioDeDiscordantes() {
		dica1.setTema(tema);
		dica1.addUserCommentary("schops", "Der Mensch kann tun was er will; er kann aber nicht wollen was er will. Dieses Tipp ist wie deine Mutter, es ist Scheiss.");
		dica1.incrementaDiscordancias();
		
		assertThat(dica1.getUsersCommentaries().size()).isEqualTo(1);
		assertThat(dica1.getUsersCommentaries().get("schops")).isEqualTo("Der Mensch kann tun was er will; er kann aber nicht wollen was er will. Dieses Tipp ist wie deine Mutter, es ist Scheiss.");
		assertThat(dica1.getDiscordancias()).isEqualTo(1);
	}
	
	/**
	 * Testa se a flag de denúncia é incrementada corretamente numa dica.
	 */
	@Test
	public void devePoderReceberFlags() {
		assertThat(dica2.getFlag()).isEqualTo(0);
		dica2.incrementaFlag();
		assertThat(dica2.getFlag()).isEqualTo(1);
		dica2.incrementaFlag();
		assertThat(dica2.getFlag()).isEqualTo(2);
	}
	
	/**
	 * Testa se os campos de cada dica são recuperados corretamente.
	 */
	@Test
	public void deveRecuperarCorretamenteOConteudoDaDica() {
		assertThat(dica1.getTexto()).isEqualTo("LaPlace");
		assertThat(dica2.getTexto()).isEqualTo("Não subestime a última prova");
		assertThat(dica3.getTexto()).isEqualTo("https://www.youtube.com/watch?v=NyAp-3QXdC0");
		assertThat(dica4.getTexto()).isEqualTo("Álgebra Vetorial");
		
		dica4.checaTipoDica();
		
		assertThat(dica4.getInstanciaDisciplina().getRazao()).isEqualTo("Pré-requisito importante");
	}
}