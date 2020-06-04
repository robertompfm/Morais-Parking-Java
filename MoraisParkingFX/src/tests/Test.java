package tests;

import dao.*;
import model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
//
//        Veiculo veiculo = instance.queryVehicleByPlate("QFI7289");
//        System.out.println(veiculo);
//        instance.deleteVehicle("QFI7289");
//        veiculo = instance.queryVehicleByPlate("QFI7289");
//        System.out.println(veiculo);
//        System.out.println(instance.queryOwnerByName("Junior"));
//        instance.deleteOwner("Arthur");
//        System.out.println(instance.queryOwnerByName("Arthur"));
        instance.close();

    }

    public static void testPermissoes() {
        DataPermissoes instance = DataPermissoes.getInstance();

        Proprietario roberto = new Proprietario(1,"Roberto", 11021093l, "SpI");
        Proprietario larissa = new Proprietario("Larissa", 11021093l, "SpI");
        Proprietario iria = new Proprietario(3, "Iria", 11021093l, "SpI");
        Proprietario arthur = new Proprietario("Arthur", 11021093l, "SpI");

        Veiculo fordka = new Veiculo(1,"QFI7289", roberto, "Ford Ka", "Preto", TipoVeiculo.CARRO);
        Veiculo hb20 = new Veiculo(2, "7777", iria, "HB20", "Vermelho", TipoVeiculo.CARRO);
        Veiculo ferrari = new Veiculo(3,"S0JU", iria, "Ferrari", "Vermelho", TipoVeiculo.CARRO);

        AreaEstacionamento deficientes = new AreaEstacionamento(4,"Deficientes", 5,
                TipoVeiculo.CARRO, true);

        Permissao permissao01 = new Permissao(fordka, deficientes);
        Permissao permissao02 = new Permissao(hb20, deficientes);
        Permissao permissao03 = new Permissao(ferrari, deficientes);

        instance.open();
        instance.insertPermission(permissao01);
        instance.insertPermission(permissao02);
        instance.insertPermission(permissao03);

//        instance.deletePermission(1, 4);
//        instance.deletePermission(2, 4);
//        instance.deletePermission(3, 4);
//        System.out.println(instance.queryPermission(3, 4));
//        System.out.println(instance.hasPermission(3, 4));
//        System.out.println(instance.hasPermission(1, 6));
        instance.close();

    }

    public static void testEventos() {
        Evento inova = new Evento(1,"INOVA", LocalDate.parse("2020-08-10"), LocalDate.parse("2020-08-15"));

        AreaEstacionamento carros = new AreaEstacionamento(1, "Carros", 10,
                TipoVeiculo.CARRO, false);
        AreaEstacionamento onibus = new AreaEstacionamento(2, "Onibus", 5,
                TipoVeiculo.ONIBUS, false);
        HashMap<AreaEstacionamento, Integer> reservas = new HashMap<>();
        reservas.put(carros, 3);
        reservas.put(onibus, 2);
        inova.setVagasReservadas(reservas);

        System.out.println(inova);

        DataEventos instance = DataEventos.getInstance();

        instance.open();
//        instance.createEvent(inova);
//        instance.insertEvent(inova.getNome(), inova.getInicio(), inova.getFim());
//        System.out.println(instance.insertReserves(inova.getId(), inova.getVagasReservadas()));
//        System.out.println(instance.queryEventWithoutDatesByName("INOVA"));
//        System.out.println(instance.queryEventWithoutDatesById(1));
//        HashMap<Integer, Integer> queryResults = instance.queryReservesByEventId(1);
//        for (Map.Entry<Integer, Integer> entry : queryResults.entrySet()) {
//            System.out.println(entry.getKey() + " - " + entry.getValue());
//        }
//        instance.deleteEvent(inova.getNome());
//        instance.deleteReserves(inova.getId());
        instance.close();
    }

    public static void testEstacionamento() {
        DataEstacionamento instance = DataEstacionamento.getInstance();

        Proprietario roberto = new Proprietario(1,"Roberto", 11021093l, "SpI");
        Proprietario larissa = new Proprietario("Larissa", 11021093l, "SpI");
        Proprietario iria = new Proprietario(3, "Iria", 11021093l, "SpI");
        Proprietario arthur = new Proprietario("Arthur", 11021093l, "SpI");

        Veiculo fordka = new Veiculo(1,"QFI7289", roberto, "Ford Ka", "Preto", TipoVeiculo.CARRO);
        Veiculo hb20 = new Veiculo(2, "7777", iria, "HB20", "Vermelho", TipoVeiculo.CARRO);
        Veiculo ferrari = new Veiculo(3,"S0JU", iria, "Ferrari", "Vermelho", TipoVeiculo.CARRO);

        AreaEstacionamento deficientes = new AreaEstacionamento(4,"Deficientes", 5,
                TipoVeiculo.CARRO, true);
        AreaEstacionamento vip = new AreaEstacionamento(5,"Vip", 2,
                TipoVeiculo.CARRO, true);

//        instance.open();
//        instance.insertVehicle(fordka, deficientes);
//        instance.insertVehicle(fordka, deficientes);
//        instance.insertVehicle(hb20, vip);
//        instance.insertVehicle(ferrari, vip);

//        ArrayList<String> placas = instance.queryAllVehicles();
//        ArrayList<String> placas = instance.queryVehiclesByArea(vip);
//        for (String placa : placas) {
//            System.out.println(placa);
//        }

//        instance.deleteVehicle("7777");


//        instance.close();
    }

    public static void main(String[] args) {
//        testUsuarios();
//        testAreas();
//        testProprietarios();
//        testVeiculos();
//        testPermissoes();
//        testEventos();
//        testEstacionamento();
    }

}
