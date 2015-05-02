package com.example.marko.zagreen;

/**
 * Interface za prijenos stanja varijabli iz fragmenata
 * sadrži metode za prijenos stanja buttona
 *
 * @author Collude
 * @version 2015.0502
 * @since 1.0
 */
public interface ButtonStateInterface {

    /**
     * Prenosi stanje locationButtona iz FragmentMapButtona.
     *
     * @param state stanje locationButtona
     */
    public void getLocationButtonState(boolean state);

    /**
     * Prenosi stanje checkInButtona iz FragmentMapButtona
     *
     * @param state stanje checkInButtona
     */
    public void getCheckInButtonState(boolean state);

    /**
     * Metoda prima stanja checkboxova iz fragmenta
     *
     * @param papirState              stanje papirState checkboxa
     * @param stakloState             stanje papirState checkboxa
     * @param plastikaState           stanje papirState checkboxa
     * @param tekstilState            stanje papirState checkboxa
     * @param reciklaznoDvoristeState stanje reciklaznoDvoristeState checkboxa
     */
    public void getCheckBoxChangeState(boolean papirState, boolean stakloState,
                                       boolean plastikaState, boolean tekstilState,
                                       boolean reciklaznoDvoristeState);

}
