package itau;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.Scanner;

public class main {

	public static void main(String[] args) {
		while (true) {//inicio do while 1
			Scanner s = new Scanner(System.in);
			int d=0;
			boolean encerrar = false;
			try {

				System.out.println("_____Tela principal_____\nDigite 1 para criar uma conta ou 2 para entrar com sua conta:\n (ou digite letras para encerrar) ");
				d = s.nextInt();
			}catch(java.util.InputMismatchException e) {
				encerrar = true;
			}

			if(d == 1) {//inicio do if 1 (para cadastrar)
				Scanner w = new Scanner(System.in);
				Scanner q = new Scanner(System.in);
				Scanner c = new Scanner(System.in);

				String nome;
				String senha;
				String cpf;
				System.out.println("_____Tela de cadastro_____\nDigite um nome de usuario:");
				nome = w.nextLine();

				System.out.println("Digite uma senha:");
				senha = q.nextLine();

				System.out.println("Digite seu cpf(apenas números) :");
				cpf = c.nextLine();
				while(cpf.length()!= 11) {
					Scanner erro = new Scanner(System.in);
					System.out.println("O cpf precisa ter 11 digitos: ");
					cpf = erro.nextLine();

				}

				Connection conexao;
				final String url ="jdbc:mariadb://localhost:3306:/Uscs_Itau";
				final String user = "root";
				final String password = "" ; 


				try {
					Cliente cliente = new Cliente(nome,senha,cpf);
					Cadastro cadastro = new Cadastro();
					Random aleatório = new Random();
					boolean validacaoCpf = false;
					int agencia = aleatório.nextInt(20);
					int ultimo = 0;

					conexao = DriverManager.getConnection(url,user,password);
					System.out.println("\nConexão feita");
					Statement stmt = conexao.createStatement();
					ResultSet rs = stmt.executeQuery("SELECT * FROM clientes");
					while(rs.next()) {
						ultimo= ultimo + 1;
					}

					int dac = ultimo + 1;
					String comando = cadastro.cadastrar(cliente,dac,agencia);
					try {
						stmt.executeUpdate(comando);
						validacaoCpf = true;
					}catch(java.sql.SQLIntegrityConstraintViolationException e) {
						System.out.println("Esse cpf já foi cadastrado");
					}
					if(validacaoCpf) {
						System.out.println("Parabéns "+cliente.getNome()+" você criou uma conta,o número da conta é "+dac);
					}
				}
				catch(SQLException exe){
					System.out.println("\nMais q porra deu um erro no sql: "+exe);
				}
				catch(Exception exe) {
					System.out.println("\nMais q porra deu um erro: "+exe);
				}

			}
			else if(d==2) {//inicio do if 2(para entrar na conta cadastrada)
				while(true) {//inicio do while 2
					Connection conexao;
					final String url ="jdbc:mariadb://localhost:3306:/Uscs_Itau";
					final String user = "root";
					final String password = "" ;
					Scanner w = new Scanner(System.in);
					Scanner q = new Scanner(System.in);
					Scanner a = new Scanner(System.in);



					int numConta;
					String senha;
					String cpf;
					try {
						System.out.println("_____Tela de loging_____\nDigite o numero da conta:\n(Ou digite letras para a tela principal)");
						numConta = w.nextInt();
					}catch(java.util.InputMismatchException e) {
						break;
					}

					System.out.println("Digite uma senha:");
					senha = q.nextLine();

					System.out.println("Digite seu cpf(apenas números) :");
					cpf = a.nextLine();

					try {
						conexao = DriverManager.getConnection(url,user,password);
						System.out.println("----Conexão feita----");
						String y = "SELECT * FROM clientes WHERE cpf LIKE '%"+cpf+"%' AND numeroConta ="+numConta+" AND senha ='"+senha+"'";
						Statement stmt = conexao.createStatement();
						//ResultSet rs = stmt.executeQuery("select * from clientes where numeroConta like '%""+cpf+""%'");
						ResultSet rs = stmt.executeQuery(y);

						if(rs.next()) {// verifica se o loging esta certo
							Scanner c  = new Scanner(System.in);
							Scanner p = new Scanner(System.in);
							String nome = rs.getString("nome");
							String cepef = rs.getString("cpf");
							String sen =rs.getString("senha");
							int dac=rs.getInt("dac");
							int agencia=rs.getInt("agencia"); 
							int numeroConta=rs.getInt("numeroConta");
							double saldo = rs.getDouble("saldo");
							Cliente cli = new Cliente(nome,sen,cepef,saldo,dac,agencia,numeroConta);
							System.out.println("______Bem vindo "+cli.getNome()+"______\n|cpf|"+cli.getCpf()+"|\n|dac| "+cli.getDac()+"|\n|agencia| "+cli.getAgencia()+"|\n|Numero da Conta| "+cli.getNumeroConta()+"|\n|Saldo "+cli.getSaldo()+"|");
							int resposta=0;
							try {// COLOCAR OUTRO WHILE
								System.out.println("Digite 1 para efetuar um débito\n"+"Ou 2 para efetuar uma operação de crédito:");
								resposta = c.nextInt();
							}catch(java.util.InputMismatchException e) {
								System.out.println("Apenas os digitos 1 e 2 serão aceitos.\n");
							}
							if(resposta== 1) {//debito

								while(true) {// inicio do while 3
									char resp;
									try {
										while(true) {//inicio do while 4
											String comando;
											System.out.println("Qual o valor do débito?\n(Digite qualquer letra para refazer o loging)");
											double valor = Double.parseDouble(p.nextLine());
											cli.debito(valor);
											if(valor <= 0) {
												System.out.println("Não é possivel relalizar um deposito com um valor menor ou igual a zero");
											}
											else {
												Scanner z = new Scanner(System.in);
												//stmt.executeUpdate(cli.gerarStringDelete());
												stmt.executeUpdate(cli.gerarStringAtualizacaoSaldo());
												System.out.println("Debito realizado seu saldo agora é de R$"+cli.getSaldo()+
														"\n digite  v para voltar a tela de loging ou qualquer outra coisa para realizar um novo débito ");
												resp = z.next().charAt(0);
												if(resp == 'v'||resp == 'V') {
													break;
												}
											}

										}//fim do while 4
									}catch(java.lang.NumberFormatException e) {
										System.out.println("peguei o erro");
										break;
									}
									if(resp == 'v'||resp == 'V') {
										break;
									}	
								}// fim do while 3

							}else if(resposta == 2) {//credito
								while(true) {// inicio do while 5
									char resp;
									try {
										while(true) {//inicio do while 6
											String comando;
											System.out.println("Qual o valor do crédito?\n(Digite qualquer letra para refazer o loging)");
											double valor = Double.parseDouble(p.nextLine());
											cli.credito(valor);
											if(valor <= 0) {
												System.out.println("Não é possivel relalizar um deposito com um valor menor ou igual a zero");
											}
											else {
												Scanner z = new Scanner(System.in);
												//stmt.executeUpdate(cli.gerarStringDelete());
												stmt.executeUpdate(cli.gerarStringAtualizacaoSaldo());
												System.out.println("Crédito realizado seu saldo agora é de R$"+cli.getSaldo()+
														"\n digite  v para voltar a tela de loging ou qualquer outra coisa para realizar um novo crédito ");
												resp = z.next().charAt(0);
												if(resp == 'v'||resp == 'V') {
													break;
												}
											}

										}//fim do while 6
									}catch(java.lang.NumberFormatException e) {
										System.out.println("peguei o erro");
										break;
									}
									if(resp == 'v'||resp == 'V') {
										break;
									}	
								}// fim do while 5
							}//credito fim
							else {
								System.out.println("Apenas ou valores 1 e 2 serão aceitos");
							}
						}else {//caso erre algo no loging

							while(true) {
								Scanner p = new Scanner(System.in);
								System.out.println("não tem, deseja redigitar?Digite s.\n");
								String z = p.nextLine();
								if (z == "s") {
									break;
								}
								break;

							}

						}

					}	 
					catch(SQLException exe){
						System.out.println("\nMais q porra deu um erro no sql: "+exe);
					}
					catch(Exception exe) {
						System.out.println("\nMais q porra deu um erro: "+exe);
					}
				}// fim do while 2
			}//fim do if 2
			else if(encerrar == false){
				System.out.println("Apenas os digitos 1 e 2 serão aceitos.\n");
			}
			if(encerrar == true) {
				System.out.println("Programa encerrado");
				break;
			}  
		}//fim do while 1
	}
}
