package moraisparkingnb.model;

public class Proprietario {
	
	private String nome;
	private long matricula;
	private String curso;
	

	// CONSTRUCTOR
	public Proprietario(String nome, long matricula, String curso) {
		this.nome = nome;
		this.matricula = matricula;
		this.curso = curso;
	}


	// GETTERS AND SETTERS
	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public long getMatricula() {
		return matricula;
	}


	public void setMatricula(long matricula) {
		this.matricula = matricula;
	}


	public String getCurso() {
		return curso;
	}


	public void setCurso(String curso) {
		this.curso = curso;
	}

	
	// TO STRING
	@Override
	public String toString() {
		return nome + " (" + matricula + ")";
	}
	
	
	

}