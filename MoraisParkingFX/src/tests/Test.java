package tests;

import model.objects.Usuario;
import model.data.DataCadastro;
import model.data.DataUsuarios;

public class Test {
    private static void testUsuarios() {
        Usuario usuario;
        DataUsuarios instance = DataUsuarios.getInstance();
//        DataUsuarios instance = new DataUsuarios();
        instance.open();
        instance.insertUsuario("robertompfm", "betobalanco", "ESTACIONAMENTO");
        instance.insertUsuario("larissacauane", "hunterhunter", "RH");
        instance.insertUsuario("iriaguazzi", "soju", "GESTOR");

        System.out.println(instance.queryUsuarioByUsername("robertompfm"));
        System.out.println(instance.queryUsuarioByUsername("melancia"));
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
