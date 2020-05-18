package tests;

import dao.*;
import model.*;

import java.util.ArrayList;

public class Test {
    private static void testUsuarios() {
        Usuario roberto = new Usuario("Roberto", SetorUsuario.ESTACIONAMENTO,
                "robertompfm@gmail.com", "beto");
        Usuario larissa = new Usuario("Larissa", SetorUsuario.RH,
                "larissa@cauane.com", "hunterhunter");
        Usuario iria = new Usuario("Iria", SetorUsuario.GESTOR,
                "iria@guazzi.com", "soju");
        Usuario arthur = new Usuario("Arthur", SetorUsuario.GESTOR,
                "arthur@lacet.com", "arthur");
        DataUsuarios instance = DataUsuarios.getInstance();
        instance.open();
        instance.insertUsuario(roberto);
        instance.insertUsuario(larissa);
        instance.insertUsuario(iria);
        instance.insertUsuario(arthur);

        System.out.println(instance.queryUsuarioByEmail("robertompfm@gmail.com"));
        System.out.println(instance.queryUsuarioByEmail("melancia"));
        System.out.println(instance.queryUsuarioByEmail("arthur@lacet.com"));
        instance.deleteUsuarioByEmail("arthur@lacet.com");
        System.out.println(instance.queryUsuarioByEmail("arthur@lacet.com"));
        instance.close();
    }

    private static void testAreas() {
        AreaEstacionamento carros = new AreaEstacionamento("Carros", 10,
                TipoVeiculo.CARRO, false);
        AreaEstacionamento onibus = new AreaEstacionamento("Onibus", 5,
                TipoVeiculo.ONIBUS, false);
        AreaEstacionamento motos = new AreaEstacionamento("Motos", 20,
                TipoVeiculo.MOTOCICLETA, false);
        AreaEstacionamento deficientes = new AreaEstacionamento("Deficientes", 5,
                TipoVeiculo.CARRO, true);
        DataAreas instance = DataAreas.getInstance();
        instance.open();
        instance.insertArea(carros);
        instance.insertArea(onibus);
        instance.insertArea(motos);
        instance.insertArea(deficientes);

        ArrayList<String> specialAreas = instance.querySpecialAreasName();
        System.out.println("Special areas:");
        for (String name : specialAreas) {
            System.out.println("\t" + name);
        }

//        System.out.println(instance.queryAreaByName("Carros"));
//        System.out.println(instance.queryAreaByName("Transporte"));
//        System.out.println(instance.queryAreaByName("Deficientes"));
//        instance.deleteAreaByName("Deficientes");
//        System.out.println(instance.queryAreaByName("Deficientes"));
//        instance.deleteAreaByName("Carros");
//        System.out.println(instance.queryAreaByName("Carros"));
        instance.close();
    }

    public static void testProprietarios() {
        Proprietario roberto = new Proprietario(1,"Roberto", 11021093l, "SpI");
        Proprietario larissa = new Proprietario(2,"Larissa", 11021093l, "SpI");
        Proprietario iria = new Proprietario(3,"Iria", 11021093l, "SpI");
        Proprietario arthur = new Proprietario(4, "Arthur", 11021093l, "SpI");

        DataProprietarios instance = DataProprietarios.getInstance();
        instance.open();
        instance.insertOwner(roberto);
        instance.insertOwner(larissa);
        instance.insertOwner(iria);
        instance.insertOwner(arthur);

        System.out.println(instance.queryOwnerByName("Roberto"));
        System.out.println(instance.queryOwnerByName("Junior"));
//        instance.deleteOwner("Arthur");
//        System.out.println(instance.queryOwnerByName("Arthur"));

        ArrayList<String> placas = instance.queryOwnersPlatesByName("Iria");
        for (String placa : placas) {
            System.out.println(placa);
        }

//        instance.deleteOwner("Iria");

        instance.close();

    }

    public static void testVeiculos() {
        Proprietario roberto = new Proprietario(1,"Roberto", 11021093l, "SpI");
        Proprietario larissa = new Proprietario("Larissa", 11021093l, "SpI");
        Proprietario iria = new Proprietario(3, "Iria", 11021093l, "SpI");
        Proprietario arthur = new Proprietario("Arthur", 11021093l, "SpI");

        Veiculo fordka = new Veiculo("QFI7289", roberto, "Ford Ka", "Preto", TipoVeiculo.CARRO);
        Veiculo hb20 = new Veiculo("7777", iria, "HB20", "Vermelho", TipoVeiculo.CARRO);
        Veiculo ferrari = new Veiculo("S0JU", iria, "Ferrari", "Vermelho", TipoVeiculo.CARRO);

        DataVeiculos instance = DataVeiculos.getInstance();
        instance.open();
        instance.insertVehicle(fordka);
        instance.insertVehicle(hb20);
        instance.insertVehicle(ferrari);

        Veiculo veiculo = instance.queryVehicleByPlate("QFI7289");
        System.out.println(veiculo);
//        instance.deleteVehicle("QFI7289");
//        veiculo = instance.queryVehicleByPlate("QFI7289");
//        System.out.println(veiculo);
//        System.out.println(instance.queryOwnerByName("Junior"));
//        instance.deleteOwner("Arthur");
//        System.out.println(instance.queryOwnerByName("Arthur"));
        instance.close();

    }

    public static void main(String[] args) {
//        testUsuarios();
//        testAreas();
        testProprietarios();
//        testVeiculos();
    }
}
