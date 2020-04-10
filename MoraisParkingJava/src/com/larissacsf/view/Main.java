package com.larissacsf.view;

import com.larissacsf.model.Area;
import com.larissacsf.model.Veiculo;

public class Main {

    public static void main(String[] args) {
	    // testando Veiculo
	    Veiculo v1 = new Veiculo("Larissa", 20192007035L, "Sistemas para Internet", "BAR7801", "Carro");
	    System.out.println(v1.toString());

	    // testando Area
        Area comum = new Area("comum", 500);
        System.out.println(comum);
        for (int i = 1; i <= 200; i++) {
            comum.adicionarVeiculo(new Veiculo(Integer.toString(i), "Carro"));
        }
        System.out.println(comum);

    }
}
