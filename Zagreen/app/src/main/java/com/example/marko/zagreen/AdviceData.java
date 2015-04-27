package com.example.marko.zagreen;

/**
 * Created by Marko on 26.4.2015..
 */
public class AdviceData {

    private String[] groupsData = {"Što je recikliranje?",
            "Zašto trebamo reciklirati?",
            "Korisni savjet 3",
            "Korisni savjet 4"};

    private String[][] childrenData = {
            {"Recikliranje je postupak sakupljanja odbačenih proizvoda, razvrstavanje" +
                    " i njihovo pretvaranje u nove materijale za izradu novih proizvoda " +
                    "slične ili iste namjene. U recikliranje spada sve što se može ponovno " +
                    "iskoristiti, a da se ne baci."},

            {"Problemi sa odlagalištima otpada, zagađivanjem okoliša, troškovima odvoženja," +
                    " sanacijama i otvaranjima novih odlagališta otpada – toliko su narasli, da" +
                    " je stari način ponašanja i odnosa prema otpadu jednostavno NEPRIHVATLJIV !"},

            {"Opis 3"},
            {"Opis 4"}
    };


    public String[][] getChildrenData() {
        return childrenData;
    }

    public String[] getGroupsData() {
        return groupsData;
    }
}
