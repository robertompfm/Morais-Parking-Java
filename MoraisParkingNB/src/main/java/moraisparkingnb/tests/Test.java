/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moraisparkingnb.tests;

import moraisparkingnb.model.*;

public class Test {
	// proprietarios
	private static Proprietario roberto = new Proprietario("Roberto", 20192007007l, "Sistemas para Intenet");
	private static Proprietario larissa = new Proprietario("Larissa", 20192007023l, "Sistemas para Internet");
	private static Proprietario iria = new Proprietario("Iria", 20192007055l, "Sistemas para Internet");
	
	// veiculos
	private static Veiculo fordka = new Veiculo("QFI7285", roberto, "Ford ka", "Preto", "Comum");
	private static Veiculo jangga = new Veiculo("7777", larissa, "Jangga car", "Verde", "Elite");
	private static Veiculo hb20 = new Veiculo("QFJ2387", iria, "Hyundai HB20", "Vermelho", "Elite");
	private static Veiculo kombi = new Veiculo("D0M1N0", "Kombi do Domino", "Cinza");
	
	// areas
	private static AreaEstacionamento comum = new AreaEstacionamento("Comum", 40);
	private static AreaEstacionamento elite = new AreaEstacionamento("Elite", 10);
	
	// estacionamento
	private static Estacionamento moraisParking = new Estacionamento(50); 
	
	
	public static void testVeiculo() {
		System.out.println("PROPRIETARIOS:");
		System.out.println(roberto);
		System.out.println(larissa);
		System.out.println(iria);
		
		

		System.out.println("VEICULOS:");
		System.out.println(fordka);
		System.out.println(jangga);
		System.out.println(hb20);
		System.out.println(kombi);
	}
	
	public static void testArea() { 
		System.out.println("AREAS - pt 1:");
		System.out.println(comum);
		System.out.println(elite);
		
		comum.adicionarVeiculo(fordka);
		comum.adicionarVeiculo(kombi);
		comum.adicionarVeiculo(hb20);
		comum.adicionarVeiculo(jangga);
		
		System.out.println("AREAS - pt 2:");
		System.out.println(comum);
		System.out.println(elite);
		
		comum.removerVeiculo(hb20);
		comum.removerVeiculo(jangga);
		elite.adicionarVeiculo(hb20);
		elite.adicionarVeiculo(jangga);
		
		System.out.println("AREAS - pt 3:");
		System.out.println(comum);
		System.out.println(elite);
		
		
	}
	
	public static void testEstacionamento() {
		System.out.println("ESTACIONAMENTO:");
		
		moraisParking.autorizarEntrada("B1N60", "Corsa", "Branco");
		moraisParking.cadastrarArea("Elite", 1);
		moraisParking.cadastrarArea("Onibus", 10);
		moraisParking.cadastrarArea("Elite", 1); // repetida
		moraisParking.cadastrarArea("Area muito grande", 100); // muito grande
		moraisParking.autorizarEntrada("QFI7285", "Ford ka", "Preto");
		moraisParking.autorizarSaida("QFI7285", "", ""); // funciona mesmo sem valores de modelo e cor, mas eh bom te-los
		moraisParking.excluirArea("Onibus");
		moraisParking.cadastrarVeiculo("Iria", 20192007055l, "Sistemas para Internet", "QFJ2387", "Hyundai HB20", "Vermelho", "Elite");
		moraisParking.cadastrarVeiculo("Iria", 20192007055l, "Sistemas para Internet", "QFJ2387", "Hyundai HB20", "Vermelho", "Elite"); // repetido
		moraisParking.cadastrarVeiculo("Larissa", 20192007023l, "Sistemas para Internet", "7777", "Jangga car", "Verde", "Elite");
//		System.out.println(moraisParking.getPropCadastrados().size());
//		System.out.println(moraisParking.getVeiculosCadastrados().size());
		
		
		System.out.println(moraisParking.getStatus());
	}
	
	
}
