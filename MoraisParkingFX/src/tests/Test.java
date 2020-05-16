package tests;

import model.SetorUsuario;
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

    public static void testCadastro() {
        DataCadastro.getInstance().open();
    }

    public static void main(String[] args) {
        testUsuarios();
//        testCadastro();
    }
}
