import java.util.ArrayList;
import java.util.List;

import models.DicaDisciplina;
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
				if (dao.findAllByClassName(Disciplina.class.getName()).isEmpty()) {
					criaDisciplinaTemas();
					criaDicasDisciplinas();
				}

				if (dao.findAllByClassName(User.class.getName()).isEmpty()) {
					criaUsuarios();
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
	
	/**
	 * Percorre a lista de disciplinas, quando achar SI1 vai adicionar uma dica
	 * Sendo que Disciplina só tem implementado o método para adicionar um metaDica
	 * Tem que fazer a mesma coisa de metaDica na classe Disciplina sendo que para criar uma DicaDisciplina
	 * Para só ai poder adiocionar a dica 
	 */
	private void criaDicasDisciplinas(){
		
		
		disciplinas = dao.findAllByClassName("Disciplina");
		
		for (int i = 0; i < disciplinas.size(); i++) {
			
			if(disciplinas.get(i).equals("Sistemas de Informação 1")){
				
				List<Tema> listaTema = dao.findAllByClassName("Tema");
				Long idDoTema = new Long(0);
				
				for (int j = 0; j < listaTema.size(); j++) {
					if(listaTema.get(j).getName().equals("Análise x Design")){
						idDoTema = listaTema.get(j).getId();
					}
					
				}
				
				Tema tema = dao.findByEntityId(Tema.class, idDoTema);
				DicaDisciplina dicaDisciplina = new DicaDisciplina("GI", "pq sim");
				tema.addDica(dicaDisciplina);
				dicaDisciplina.setTema(tema);
				dicaDisciplina.setUser("Diogo 0° Melo Barbosa");
				dao.persist(dicaDisciplina);
				dao.flush();
				
			}
			
		}
		
		disciplinas.clear();
		
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
