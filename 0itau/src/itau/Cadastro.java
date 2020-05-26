package itau;



public class Cadastro {

	public String cadastrar(Cliente c ,int dac,int agencia) {
		
		return "INSERT INTO clientes (dac,agencia,nome,numeroConta,senha,cpf) VALUES("+dac+","+agencia+",'"+c.getNome()+"',"+dac+",'"+c.getSenha()+"','"+c.getCpf()+"')";
	}

}
