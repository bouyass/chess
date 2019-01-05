/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echecs;

import echecs.Pieces.Piece;

/**
 *
 * @author lyes
 */
public class Case {

    public Piece piece;
    int x, y;
    Couleur couleur;

    public Case(int x, int y, Couleur couleur) {
        this.x = x;
        this.x = y;
        this.couleur = couleur;

        System.out.println(couleur);

    }

    // retourne la couleur de la case
    public Couleur getCouleur() {
        return this.couleur;
    }

    // retourne la coordonnée x de la case 
    public int getX() {
        return x;
    }

    // retourne la coordonnée y de la case 
    public int getY() {
        return y;
    }

    //methode associant une piece a une case 
    public void setPiece(Piece piece) {
        this.piece = piece;
    }
    // methode retournant la piece contenue par la case 
    public Piece getPiece() {
        return this.piece;
    }

}
