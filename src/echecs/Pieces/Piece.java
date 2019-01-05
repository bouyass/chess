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
public abstract class Piece {
    int x,y,id;
    Type type = null;
    String icone;
    Couleur couleur;
    //boolean jouante = true;
    public int getX(){return this.x;}
    public  int getY(){return this.y;}
    public Type getType(){return this.type;}
    public Couleur getCouleur(){return this.couleur;}
    public String getIcone(){return icone;}
    public int getId(){return this.id;}
    
    
    
}
