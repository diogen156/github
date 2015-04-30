package com.example.marko.zagreen;

/**
 * Created by Marko on 27.4.2015..
 */
public interface ButtonStateInterface {

    /**
     * Metoda prenosi stanje gumba iz fragmenta
     * @param state
     */
    public void getLocationButtonState (boolean state);

    /**
     * Metoda prima stanja checkboxova iz fragmenta
     * @param papirState
     * @param stakloState
     * @param plastikaState
     * @param tekstilState
     * @param reciklaznoDvoristeState
     */
    public void getCheckBoxChangeState(boolean papirState ,boolean stakloState,
                                       boolean plastikaState,boolean tekstilState,
                                       boolean reciklaznoDvoristeState);

    public void getFiltrationButtonState(boolean state);
}
