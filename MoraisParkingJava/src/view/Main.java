
package view;

import java.util.ArrayList;

import model.Estacionamento;
import model.Veiculo;

public class Main {

    public static void main(String[] args) {

        Estacionamento e1 = new Estacionamento();
        e1.cadastrarVeiculo("Larissa", "20192007035L", "Sistemas para internet", "LAR0104", "Fusca", "Carro");
        
        System.out.println("--------------------------------");
        
        e1.cadastrarArea("Carros", "Carros", 100);
        e1.cadastrarArea("Moto", "Moto", 50);
       
       

        
        
        
         

        
        
        
    }
    
}
