/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echecs;

/**
 *
 * @author lyes joue le rôle de directeur dans le design pattern Monteur
 */
public class CreateurEchiquier {

    public MonteurEchiquier me;

    public void setMonteurEchiquier(MonteurEchiquier me) {
        this.me = me;
    }

    public Echiquier getEchiquier() {
        return this.me.getEchiquier();
    }

    public void construireEchiquier() {
        this.me.creerEchiquier();
        this.me.monterEchiquier();
    }

}
