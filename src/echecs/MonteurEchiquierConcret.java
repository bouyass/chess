/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echecs;

/**
 *
 * @author lyes joue le role de monteur conret dans le design pattern monteur
 */
public class MonteurEchiquierConcret extends MonteurEchiquier {

    @Override
    public void monterEchiquier() {
        echiquier.createCases();
    }

}
