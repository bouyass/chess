/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echecs;

import echecs.Pieces.Piece;

/**
 *
 * @author lyes joue le role de produit dans le design pattern monteur
 */
public class Echiquier {

    final public Case[][] cases = new Case[8][8];
    final public Piece[] pieces = new Piece[40];
    PieceFactory pf;
    int cpt = 0;
    Piece piece = new Piece() {
    };
    // variable pour récupérer le chemin du projet sur le système
    String path = System.getProperty("user.dir");

    public Echiquier() {
        pf = new ConcretePieceFactroy();
    }

    public Piece createPiece(String name, int i, int j, Type type, String icone, Couleur couleur, int id) {
        switch (name) {
            case "PION":
                piece = pf.createPawn(i, j, type, icone, couleur, id);
                break;
            case "CAVALIER":
                piece = pf.createKnight(j, j, type, icone, couleur);
                break;
            case "TOUR":
                piece = pf.createRook(j, j, type, icone, couleur);
                break;
            case "DAME":
                piece = pf.createQueen(j, j, type, icone, couleur);
                break;
            case "ROI":
                piece = pf.createKing(j, j, type, icone, couleur);
            case "FOU":
                piece = pf.createBishop(j, j, type, icone, couleur);
                break;
        }

        return piece;
    }
// création des cses de l'Echiquier en leur associant des pièces
    public void createCases() {
        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 7; j++) {
                if (i % 2 == 0 && j % 2 == 0) {
                    cases[i][j] = new Case(i, j, Couleur.BLANC);
                    cases[i][j].piece = null;
                    if (i == 0) {
                        if (j == 0 || j == 7) {
                            pieces[cpt] = this.createPiece("TOUR", i, j, Type.TOUR, path + "/Icone/TN.gif", Couleur.NOIR, i);
                            cases[i][j].setPiece(pieces[cpt]);
                            cpt++;
                        } else if (j == 1 || j == 6) {
                            pieces[cpt] = this.createPiece("CAVALIER", i, j, Type.CAVALIER, path + "/Icone/CN.gif", Couleur.NOIR, i);
                            cases[i][j].setPiece(pieces[cpt]);
                            cpt++;
                        } else if (j == 2 || j == 5) {
                            pieces[cpt] = this.createPiece("FOU", i, j, Type.FOU, path + "/Icone/FN.gif", Couleur.NOIR, i);
                            cases[i][j].setPiece(pieces[cpt]);
                            cpt++;
                        } else if (j == 3) {
                            pieces[cpt] = this.createPiece("DAME", i, j, Type.DAME, path + "/Icone/DN.gif", Couleur.NOIR, i);
                            cases[i][j].setPiece(pieces[cpt]);
                            cpt++;
                        } else if (j == 4) {
                            pieces[cpt] = this.createPiece("ROI", i, j, Type.ROI, path + "/Icone/RN.gif", Couleur.NOIR, i);
                            cases[i][j].setPiece(pieces[cpt]);
                            cpt++;
                        }
                    } else if (i == 1) {
                        pieces[cpt] = this.createPiece("PION", i, j, Type.PION, path + "/Icone/PN.gif", Couleur.NOIR, j);
                        cases[i][j].setPiece(pieces[cpt]);
                        cpt++;
                    } else if (i == 6) {
                        pieces[cpt] = this.createPiece("PION", i, j, Type.PION, path + "/Icone/PB.gif", Couleur.BLANC, j);
                        cases[i][j].setPiece(pieces[cpt]);
                        cpt++;
                    } else if (i == 7) {
                        if (j == 0 || j == 7) {
                            pieces[cpt] = this.createPiece("TOUR", i, j, Type.TOUR, path + "/Icone/PB.gif", Couleur.BLANC, i);
                            cases[i][j].setPiece(pieces[cpt]);
                            cpt++;
                        } else if (j == 1 || j == 6) {
                            pieces[cpt] = this.createPiece("CAVALIER", i, j, Type.CAVALIER, path + "/Icone/CB.gif", Couleur.BLANC, i);
                            cases[i][j].setPiece(pieces[cpt]);
                            cpt++;
                        } else if (j == 2 || j == 5) {
                            pieces[cpt] = this.createPiece("FOU", i, j, Type.FOU, path + "/Icone/FB.gif", Couleur.BLANC, i);
                            cases[i][j].setPiece(pieces[cpt]);
                            cpt++;
                        } else if (j == 3) {
                            pieces[cpt] = this.createPiece("DAME", i, j, Type.DAME, path + "/Icone/DN.gif", Couleur.BLANC, i);
                            cases[i][j].setPiece(pieces[cpt]);
                            cpt++;
                        } else if (j == 4) {
                            pieces[cpt] = this.createPiece("ROI", i, j, Type.ROI, path + "/Icone/RN.gif", Couleur.BLANC, i);
                            cases[i][j].setPiece(pieces[cpt]);
                            cpt++;
                        }
                    }
                } else if (i % 2 != 0 && j % 2 != 0) {
                    cases[i][j] = new Case(i, j, Couleur.BLANC);
                    //cases[i][j].piece = null;
                    if (i == 0) {
                        if (j == 0 || j == 7) {
                            pieces[cpt] = this.createPiece("TOUR", i, j, Type.TOUR, path + "/Icone/TN.gif", Couleur.NOIR, i);
                            cases[i][j].setPiece(pieces[cpt]);
                            cpt++;
                        } else if (j == 1 || j == 6) {
                            pieces[cpt] = this.createPiece("CAVALIER", i, j, Type.CAVALIER, path + "/Icone/CN.gif", Couleur.NOIR, i);
                            cases[i][j].setPiece(pieces[cpt]);
                            cpt++;
                        } else if (j == 2 || j == 5) {
                            pieces[cpt] = this.createPiece("FOU", i, j, Type.FOU, path + "/Icone/FN.gif", Couleur.NOIR, i);
                            cases[i][j].setPiece(pieces[cpt]);
                            cpt++;
                        } else if (j == 3) {
                            pieces[cpt] = this.createPiece("DAME", i, j, Type.DAME, path + "/Icone/DN.gif", Couleur.NOIR, i);
                            cases[i][j].setPiece(pieces[cpt]);
                            cpt++;
                        } else if (j == 4) {
                            pieces[cpt] = this.createPiece("ROI", i, j, Type.ROI, path + "/Icone/RN.gif", Couleur.NOIR, i);
                            cases[i][j].setPiece(pieces[cpt]);
                            cpt++;
                        }
                    } else if (i == 1) {
                        pieces[cpt] = this.createPiece("PION", i, j, Type.PION, path + "/Icone/PN.gif", Couleur.NOIR, j);
                        cases[i][j].setPiece(pieces[cpt]);
                        cpt++;
                    } else if (i == 6) {
                        pieces[cpt] = this.createPiece("PION", i, j, Type.PION, path + "/Icone/PB.gif", Couleur.BLANC, j);
                        cases[i][j].setPiece(pieces[cpt]);
                        cpt++;
                    } else if (i == 7) {
                        if (j == 0 || j == 7) {
                            pieces[cpt] = this.createPiece("TOUR", i, j, Type.TOUR, path + "/Icone/TB.gif", Couleur.BLANC, i);
                            cases[i][j].setPiece(pieces[cpt]);
                            cpt++;
                        } else if (j == 1 || j == 6) {
                            pieces[cpt] = this.createPiece("CAVALIER", i, j, Type.CAVALIER, path + "/Icone/CB.gif", Couleur.BLANC, i);
                            cases[i][j].setPiece(pieces[cpt]);
                            cpt++;
                        } else if (j == 2 || j == 5) {
                            pieces[cpt] = this.createPiece("FOU", i, j, Type.FOU, path + "/Icone/FB.gif", Couleur.BLANC, i);
                            cases[i][j].setPiece(pieces[cpt]);
                            cpt++;
                        } else if (j == 3) {
                            pieces[cpt] = this.createPiece("DAME", i, j, Type.DAME, path + "/Icone/DB.gif", Couleur.BLANC, i);
                            cases[i][j].setPiece(pieces[cpt]);
                            cpt++;
                        } else if (j == 4) {
                            pieces[cpt] = this.createPiece("ROI", i, j, Type.ROI, path + "/Icone/DB.gif", Couleur.BLANC, i);
                            cases[i][j].setPiece(pieces[cpt]);
                            cpt++;
                        }
                    }

                } else {
                    cases[i][j] = new Case(i, j, Couleur.NOIR);
                    // cases[i][j].piece = null;
                    if (i == 0) {
                        if (j == 0 || j == 7) {
                            pieces[cpt] = this.createPiece("TOUR", i, j, Type.TOUR, path + "/Icone/TN.gif", Couleur.NOIR, i);
                            cases[i][j].setPiece(pieces[cpt]);
                            cpt++;
                        } else if (j == 1 || j == 6) {
                            pieces[cpt] = this.createPiece("CAVALIER", i, j, Type.CAVALIER, path + "/Icone/CN.gif", Couleur.NOIR, i);
                            cases[i][j].setPiece(pieces[cpt]);
                            cpt++;
                        } else if (j == 2 || j == 5) {
                            pieces[cpt] = this.createPiece("FOU", i, j, Type.FOU, path + "/Icone/FN.gif", Couleur.NOIR, i);
                            cases[i][j].setPiece(pieces[cpt]);
                            cpt++;
                        } else if (j == 3) {
                            pieces[cpt] = this.createPiece("DAME", i, j, Type.DAME, path + "/Icone/DN.gif", Couleur.NOIR, i);
                            cases[i][j].setPiece(pieces[cpt]);
                            cpt++;
                        } else if (j == 4) {
                            pieces[cpt] = this.createPiece("ROI", i, j, Type.ROI, path + "/Icone/RN.gif", Couleur.NOIR, i);
                            cases[i][j].setPiece(pieces[cpt]);
                            cpt++;
                        }
                    } else if (i == 1) {
                        pieces[cpt] = this.createPiece("PION", i, j, Type.PION, path + "/Icone/PN.gif", Couleur.NOIR, j);
                        cases[i][j].setPiece(pieces[cpt]);
                        cpt++;
                    } else if (i == 6) {
                        pieces[cpt] = this.createPiece("PION", i, j, Type.PION, path + "/Icone/PB.gif", Couleur.BLANC, j);
                        cases[i][j].setPiece(pieces[cpt]);
                        cpt++;
                    } else if (i == 7) {
                        if (j == 0 || j == 7) {
                            pieces[cpt] = this.createPiece("TOUR", i, j, Type.TOUR, path + "/Icone/TB.gif", Couleur.BLANC, i);
                            cases[i][j].setPiece(pieces[cpt]);
                            cpt++;
                        } else if (j == 1 || j == 6) {
                            pieces[cpt] = this.createPiece("CAVALIER", i, j, Type.CAVALIER, path + "/Icone/CB.gif", Couleur.BLANC, i);
                            cases[i][j].setPiece(pieces[cpt]);
                            cpt++;
                        } else if (j == 2 || j == 5) {
                            pieces[cpt] = this.createPiece("FOU", i, j, Type.FOU, path + "/Icone/FB.gif", Couleur.BLANC, i);
                            cases[i][j].setPiece(pieces[cpt]);
                            cpt++;
                        } else if (j == 3) {
                            pieces[cpt] = this.createPiece("DAME", i, j, Type.DAME, path + "/Icone/DN.gif", Couleur.BLANC, i);
                            cases[i][j].setPiece(pieces[cpt]);
                            cpt++;
                        } else if (j == 4) {
                            pieces[cpt] = this.createPiece("ROI", i, j, Type.ROI, path + "/Icone/RB.gif", Couleur.BLANC, i);
                            cases[i][j].setPiece(pieces[cpt]);
                            cpt++;
                        }
                    }
                }
            }
        }

    }

}
