package com.larissacsf.test;

import com.larissacsf.model.AreaEstacionamento;
import com.larissacsf.model.DataSource;
import com.larissacsf.model.Proprietario;

public class Test {

    public static void testSQLProp() {
        // OPENING
        DataSource.getInstance().open();
        // INSERTING PROPRIETARIO
        System.out.println(DataSource.getInstance().insertProprietario("roberto", 20192007007l, "sistemas para internet"));
        System.out.println(DataSource.getInstance().insertProprietario("larissa", 20192007023l, "sistemas para internet"));
        // QUERYING PROPRIETARIO
        Proprietario proprietario = DataSource.getInstance().queryProprietario("ROBERTO");
        System.out.println(proprietario);
        proprietario = DataSource.getInstance().queryProprietario("LARISSA");
        System.out.println(proprietario);
        proprietario = DataSource.getInstance().queryProprietario("IRIA");
        System.out.println(proprietario);
        // INSERTING AGAIN SAME PROPRIETARIO
        System.out.println(DataSource.getInstance().insertProprietario("roberto", 20192007007l, "sistemas para internet"));

        DataSource.getInstance().close();
    }

    public static void testSQLArea() {
        // OPENING
        DataSource.getInstance().open();
        // INSERTING AREA
        System.out.println(DataSource.getInstance().insertArea("comum", 20));
        System.out.println(DataSource.getInstance().insertArea("especial", 5));
        // QUERYING AREA
        AreaEstacionamento area = DataSource.getInstance().queryArea("COMUM");
        System.out.println(area);
        area = DataSource.getInstance().queryArea("ESPECIAL");
        System.out.println(area);
        area = DataSource.getInstance().queryArea("ONIBUS");
        System.out.println(area);
        // INSERTING AGAIN SAME AREA
        System.out.println(DataSource.getInstance().insertArea("comum", 25));

        DataSource.getInstance().close();
    }
}
