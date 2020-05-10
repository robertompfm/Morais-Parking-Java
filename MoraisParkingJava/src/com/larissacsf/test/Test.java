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
        Proprietario proprietario = DataSource.getInstance().queryProprietarioByName("ROBERTO");
        System.out.println(proprietario);
        proprietario = DataSource.getInstance().queryProprietarioByName("LARISSA");
        System.out.println(proprietario);
        proprietario = DataSource.getInstance().queryProprietarioById(1);
        System.out.println(proprietario);
        proprietario = DataSource.getInstance().queryProprietarioById(2);
        System.out.println(proprietario);
        proprietario = DataSource.getInstance().queryProprietarioByName("IRIA");
        System.out.println(proprietario);
        proprietario = DataSource.getInstance().queryProprietarioById(3);
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
        AreaEstacionamento area = DataSource.getInstance().queryAreaByName("COMUM");
        System.out.println(area);
        area = DataSource.getInstance().queryAreaByName("ESPECIAL");
        System.out.println(area);
        area = DataSource.getInstance().queryAreaById(1);
        System.out.println(area);
        area = DataSource.getInstance().queryAreaById(2);
        System.out.println(area);
        area = DataSource.getInstance().queryAreaByName("ONIBUS");
        System.out.println(area);
        area = DataSource.getInstance().queryAreaById(3);
        System.out.println(area);
        // INSERTING AGAIN SAME AREA
        System.out.println(DataSource.getInstance().insertArea("comum", 25));

        DataSource.getInstance().close();
    }

    public static void main(String[] args) {
        DataSource.getInstance().open();
    }
}
