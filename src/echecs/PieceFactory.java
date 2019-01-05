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
public interface PieceFactory {

    public Piece createPawn(int x, int y, Type type, String icone, Couleur couleur, int id);

    public Piece createKing(int x, int y, Type type, String icone, Couleur couleur);

    public Piece createKnight(int x, int y, Type type, String icone, Couleur couleur);

    public Piece createRook(int x, int y, Type type, String icone, Couleur couleur);

    public Piece createBishop(int x, int y, Type type, String icone, Couleur couleur);

    public Piece createQueen(int x, int y, Type type, String icone, Couleur couleur);

}
