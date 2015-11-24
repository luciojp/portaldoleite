package unidade;

import static org.fest.assertions.Assertions.assertThat;
import models.Dica;
import models.DicaAssunto;
import models.DicaConselho;
import models.Disciplina;
import models.MetaDica;
import models.Tema;
import models.User;

import org.junit.Test;

public class DisciplinaTest {

	@Test
	public void deveConseguirAdicionarTemas() {
		Disciplina disciplina = new Disciplina("Algoritmos Avançados");
		Tema tema = new Tema("Programação Dinâmica", disciplina);
		
		assertThat(disciplina.getTemas().size()).isEqualTo(0);
		
		disciplina.addTema(tema);
		
		assertThat(disciplina.getTemas().size()).isEqualTo(1);
		assertThat(disciplina.getTemaByNome("Programação Dinâmica").getName()).isEqualTo("Programação Dinâmica");
	}
	
	@Test
	public void deveConseguirAdicionarMetaDicas() {
		Disciplina disciplina = new Disciplina("Algoritmos Avançados");
		
		User user = new User("sir.leo@hotmail.com", "1234", "leulz");
		MetaDica metaDica = new MetaDica(disciplina, user.getNome(), "Estas dicas são as melhores.");
		Dica dica1 = new DicaConselho("Tente achar a relação de recorrência no problema");
		Dica dica2 = new DicaAssunto("Guloso");
		
		metaDica.addDica(dica1);
		metaDica.addDica(dica2);
		
		disciplina.addMetaDica(metaDica);
		
		assertThat(disciplina.getMetaDicas().size()).isEqualTo(1);
		assertThat(disciplina.getMetaDicas().get(0).getDicasAdicionadas().size()).isEqualTo(2);
		assertThat(disciplina.getMetaDicas().get(0).getComment()).isEqualTo("Estas dicas são as melhores.");
	}
}