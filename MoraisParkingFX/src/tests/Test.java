package tests;

import dao.DataAreas;
import model.AreaEstacionamento;
import model.SetorUsuario;
import model.TipoVeiculo;
import model.Usuario;
import dao.DataCadastro;
import dao.DataUsuarios;

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

        System.out.println(instance.queryAreaByName("Carros"));
        System.out.println(instance.queryAreaByName("Transporte"));
        System.out.println(instance.queryAreaByName("Deficientes"));
        instance.deleteAreaByName("Deficientes");
        System.out.println(instance.queryAreaByName("Deficientes"));
        instance.close();
    }

    public static void testCadastro() {
        DataCadastro.getInstance().open();
    }

    public static void main(String[] args) {
//        testUsuarios();
        testAreas();
//        testCadastro();
    }
}
