/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echecs;

import Vue.FenetreJeu;
import echecs.CreateurEchiquier;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;

/**
 *
 * @author lyes jour le role de controleur dans l'application joue le role de
 * client dans le design pattern monteur
 */
public class Partie {

    FenetreJeu fj;
    Mediateur mediateur;
    mediateurAI mai;
    Timer timer;
    String choix;
    int pos;

    public Partie(String choix) {
        this.choix = choix;
        // création de l'Echiquier
        CreateurEchiquier createur = new CreateurEchiquier();
        MonteurEchiquier monteurEchiquier = new MonteurEchiquierConcret();
        createur.setMonteurEchiquier(monteurEchiquier);
        createur.construireEchiquier();
        Echiquier echiquier = createur.getEchiquier();
        fj = new FenetreJeu(echiquier, choix, this);
        timer = new Timer(fj);
        mediateur = new Mediateur(fj);
        mai = new mediateurAI(fj);
    
    }

    //méthode gérant la partie en captant les clickent des joueurs et lancer le traitement approprié
    public void jouer(int i, int j) {

        mediateur.getClick(i, j);
    }

   
}
