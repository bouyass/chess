/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echecs.Pieces;

import javax.swing.Icon;
import echecs.Couleur;
import echecs.Type;
/**
 *
 * @author lyes
 */
public class Pion extends Piece {
  
    public Pion(int x, int y, Type type, String icone,Couleur couleur, int id){
        this.x = x;
        this.y = y;
        this.id = id;
        System.out.println("pion "+this.id+" couleur"+couleur);
        this.type = type;
        this.icone = icone;
        this.couleur = couleur;
    }
   
    
}
