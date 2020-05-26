package itau;

public class Cliente {
	private String nome;
	private String cpf ;
	private String senha;
	private int dac;
	private int agencia; 
	private int numeroConta;
	private double saldo;



	public Cliente (String n, String s,String cpf) {
		this.nome = n;
		this.senha =s;
		this.saldo = 0.00;
		this.cpf = cpf;

	}

	public Cliente (String n, String s,String cpf,double saldo,int dac , int agencia ,int numeroConta) {
		this.nome = n;
		this.senha =s;
		this.saldo = saldo;
		this.cpf = cpf;
		this.saldo = saldo;
		this.dac = dac;
		this.agencia = agencia;
		this.numeroConta = numeroConta;
	}

	public void debito(double R$) {
		this.saldo = this.saldo-R$; 
	}
	public void credito(double R$) {
		this.saldo = this.saldo + R$;
	}
	public String gerarStringAtualizacao() {
		return "INSERT INTO clientes (dac,agencia,nome,numeroConta,senha,cpf,saldo) VALUES("+this.dac+","+ this.agencia+",'"+this.nome+"',"+this.numeroConta+",'"+this.senha+"','"+this.cpf+"',"+this.saldo+")";
	}
	public String gerarStringDelete() {
		return "DELETE FROM clientes WHERE dac = "+this.dac+"";
	}
	public String gerarStringAtualizacaoSaldo() {
		return "UPDATE clientes SET saldo = "+this.saldo+" where dac = "+this.dac+"";
	}


	public int getDac() {
		return dac;
	}

	public void setDac(int dac) {
		this.dac = dac;
	}

	public int getAgencia() {
		return agencia;
	}

	public void setAgencia(int agencia) {
		this.agencia = agencia;
	}

	public int getNumeroConta() {
		return numeroConta;
	}

	public void setNumeroConta(int numeroConta) {
		this.numeroConta = numeroConta;
	}

	public String getCpf() {
		return cpf;
	}



	public void setCpf(String cpf) {
		this.cpf = cpf;
	}



	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

}
