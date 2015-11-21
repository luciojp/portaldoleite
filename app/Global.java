import java.util.ArrayList;
import java.util.List;

import models.Dica;
import models.DicaDisciplina;
import models.DicaMaterial;
import models.Disciplina;
import models.Tema;
import models.User;
import models.dao.GenericDAOImpl;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.db.jpa.JPA;

public class Global extends GlobalSettings {

	private static GenericDAOImpl dao = new GenericDAOImpl();
	private List<Disciplina> disciplinas = new ArrayList<>();

	@Override
	public void onStart(Application app) {
		Logger.info("Aplicação inicializada...");

		JPA.withTransaction(new play.libs.F.Callback0() {
			@Override
			public void invoke() throws Throwable {
				if (dao.findAllByClassName(User.class.getName()).isEmpty()) {
					criaUsuarios();
				}

				if (dao.findAllByClassName(Disciplina.class.getName()).isEmpty()) {
					criaDisciplinaTemas();
					criaDicasDisciplinas();
					//computaVotos();
				}
			}
		});
	}

	@Override
	public void onStop(Application app) {
		JPA.withTransaction(new play.libs.F.Callback0() {
			@Override
			public void invoke() throws Throwable {
				Logger.info("Aplicação finalizando...");

				disciplinas = dao.findAllByClassName("Disciplina");

				for (Disciplina disciplina : disciplinas) {
					dao.removeById(Disciplina.class, disciplina.getId());
				}
			}
		});
	}

	private void computaVotos() {

		List<Dica> listDica = new ArrayList<>();
		listDica = dao.findAllByClassName("Dica");

		for (int i = 0; i < listDica.size(); i++) {

			String login = listDica.get(i).getUser();

			Dica dica = dao.findByEntityId(Dica.class,  listDica.get(i).getId());
			
			if (!dica.wasVotedByUser(login)) {
				dica.addUsuarioQueVotou(login);
				dica.incrementaConcordancias();
				dao.merge(dica);
				dao.flush();
			}

		}
	}

	private void criaDicasDisciplinas() {
		disciplinas = dao.findAllByClassName("Disciplina");

		for (int i = 0; i < disciplinas.size(); i++) {

			if (disciplinas.get(i).getNome().equals("Sistemas de Informação 1")) {

				Tema tema = disciplinas.get(i).getTemaByNome("Análise x Design");

				DicaDisciplina dicaDisciplina = new DicaDisciplina("GI", "pq sim");
				tema.addDica(dicaDisciplina);
				dicaDisciplina.setTema(tema);
				dicaDisciplina.setUser("Diogo 0° Melo Barbosa");

				DicaMaterial dicaMaterial = new DicaMaterial("http://www.wthreex.com/rup/process/workflow/ovu_and.htm");

				tema.addDica(dicaMaterial);
				dicaMaterial.setTema(tema);
				dicaMaterial.setUser("Diogo 1° Melo Barbosa");

				dao.persist(dicaMaterial);
				dao.persist(dicaDisciplina);
				dao.flush();

				dicaDisciplina.addUsuarioQueVotou("operep0");
				dicaDisciplina.incrementaConcordancias();
				dicaDisciplina.addUsuarioQueVotou("operep1");
				dicaDisciplina.incrementaConcordancias();
				
				dicaMaterial.addUsuarioQueVotou("operep0");
				dicaMaterial.incrementaConcordancias();
				dicaMaterial.addUsuarioQueVotou("operep1");
				dicaMaterial.incrementaConcordancias();
				
				dao.merge(dicaDisciplina);
				dao.merge(dicaMaterial);
				dao.flush();
				
				/**
				 * Assim como em application, realizei os mesmo passos para
				 * cadastrar o voto Já aproveitei que todas as informações que
				 * precisam para criar a dita já tem aqui em criar a dica da
				 * disciplina. (nada melhor do que criar a dica já com o voto
				 * regristrado né?!) Pois bem, tá dando nullPointerException
				 * assim que executa a linha 114. Já mexi, revirei, procurei e
				 * não achei o motivo, deve ser a hora/sono (04h da matina
				 * agora). Assim que der tenta ver isso ai. Vlw Wendeley =D
				 */

				// Dica dica = dao.findByEntityId(Dica.class,
				// dicaMaterial.getId());
				// dica.addUsuarioQueVotou("Diogo 0° Melo Barbosa");
				// dica.incrementaConcordancias();

				// dao.merge(dica);
				// dao.flush();

			} else if (disciplinas.get(i).getNome().equals("Cálculo 2")) {
				Tema tema = disciplinas.get(i).getTemaByNome("Integral imprópria");

				DicaDisciplina dicaDisciplina = new DicaDisciplina("Cálculo 1", "pq ta no fluxograma !!");
				tema.addDica(dicaDisciplina);
				dicaDisciplina.setTema(tema);
				dicaDisciplina.setUser("Diogo 2° Melo Barbosa");

				DicaMaterial dicaMaterial = new DicaMaterial("https://www.youtube.com/watch?v=TD8L1V6xckw");

				tema.addDica(dicaMaterial);
				dicaMaterial.setTema(tema);
				dicaMaterial.setUser("Diogo 1° Melo Barbosa");

				dao.persist(dicaMaterial);
				dao.persist(dicaDisciplina);
				dao.flush();
				
				dicaDisciplina.addUsuarioQueVotou("operep2");
				dicaDisciplina.incrementaConcordancias();
				dicaDisciplina.addUsuarioQueVotou("operep1");
				dicaDisciplina.incrementaConcordancias();
				
				dicaMaterial.addUsuarioQueVotou("operep2");
				dicaMaterial.incrementaConcordancias();
				dicaMaterial.addUsuarioQueVotou("operep1");
				dicaMaterial.incrementaConcordancias();
				
				dao.merge(dicaDisciplina);
				dao.merge(dicaMaterial);
				dao.flush();
				
			} else if (disciplinas.get(i).getNome().equals("Estrutura de Dados e Algoritmos")) {
				Tema tema = disciplinas.get(i).getTemaByNome("Estrutura de dados");

				DicaMaterial dicaMaterial = new DicaMaterial("https://pt.wikipedia.org/wiki/%C3%81rvore_AVL");

				tema.addDica(dicaMaterial);
				dicaMaterial.setTema(tema);
				dicaMaterial.setUser("Diogo 5° Melo Barbosa");

				dao.persist(dicaMaterial);
				dao.flush();
				
				
				dicaMaterial.addUsuarioQueVotou("operep5");
				dicaMaterial.incrementaConcordancias();
				dicaMaterial.addUsuarioQueVotou("operep1");
				dicaMaterial.incrementaConcordancias();
					
				dao.merge(dicaMaterial);
				dao.flush();
			}

		}

	}

	private void criaUsuarios() {
		for (int i = 0; i < 10; i++) {
			User user = new User("DiogoMeloBarbosa" + i + "@jourrapide.com", "1234", "operep" + i);
			user.setNome("Diogo " + i + "° Melo Barbosa");

			dao.persist(user);
			dao.flush();
		}
	}

	private void criaDisciplinaTemas() {
		Disciplina si1 = new Disciplina("Sistemas de Informação 1");
		si1.addTema(new Tema("Análise x Design"));
		si1.addTema(new Tema("Orientação a objetos"));
		si1.addTema(new Tema("GRASP"));
		si1.addTema(new Tema("GoF"));
		si1.addTema(new Tema("Arquitetura"));
		si1.addTema(new Tema("Play"));
		si1.addTema(new Tema("JavaScript"));
		si1.addTema(new Tema("HTML / CSS / Bootstrap"));
		si1.addTema(new Tema("Heroku"));
		si1.addTema(new Tema("Labs"));
		si1.addTema(new Tema("Minitestes"));
		si1.addTema(new Tema("Projeto"));

		Disciplina calculo = new Disciplina("Cálculo 2");
		calculo.addTema(new Tema("Integral imprópria"));
		calculo.addTema(new Tema("Aplicações de integrais"));
		calculo.addTema(new Tema("Sequências"));
		calculo.addTema(new Tema("Séries numéricas"));
		calculo.addTema(new Tema("Intervalor e raio de convergência"));
		calculo.addTema(new Tema("Séries de potências"));
		calculo.addTema(new Tema("Funções de vetoriais"));

		Disciplina eda = new Disciplina("Estrutura de Dados e Algoritmos");
		eda.addTema(new Tema("Custo de algoritmos"));
		eda.addTema(new Tema("Algoritmos de ordenação"));
		eda.addTema(new Tema("Estrutura de dados"));

		dao.persist(si1);
		dao.persist(calculo);
		dao.persist(eda);
		dao.flush();
	}

}
