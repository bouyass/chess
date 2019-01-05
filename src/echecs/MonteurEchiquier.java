/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echecs;

/**
 *
 * @author lyes joue le role du monteur dans le design pattern monteur
 */
public abstract class MonteurEchiquier {

    protected Echiquier echiquier;

    public Echiquier getEchiquier() {
        return this.echiquier;
    }

    public void creerEchiquier() {
        this.echiquier = new Echiquier();
    }

    public abstract void monterEchiquier();
}
