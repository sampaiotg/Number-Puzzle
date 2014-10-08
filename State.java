
package numberpuzzle;


public class State implements Comparable<State> {

    private int[][] matriz;
    private int vazio;
    public State pai;

    public State(int[][] matriz) {
        this.matriz = matriz;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (matriz[i][j] == 0){
                    vazio = (i*3)+j;
                }
            }
        }
    }

    public State() {
        int[][] a = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
        matriz = a;
        vazio = 0;
    }

    public int getBotao(int x) {
        return matriz[x/3][x%3];
    }

    public void move(int lado) {
        int x1 = 0, x2 = 0, y1 = 0, y2 = 0;
        if (vazio == 0) {
            x1 = 0;
            y1 = 0;

        } else if (vazio == 1) {
            x1 = 0;
            y1 = 1;
        } else if (vazio == 2) {
            x1 = 0;
            y1 = 2;
        } else if (vazio == 3) {
            x1 = 1;
            y1 = 0;
        } else if (vazio == 4) {
            x1 = 1;
            y1 = 1;
        } else if (vazio == 5) {
            x1 = 1;
            y1 = 2;
        } else if (vazio == 6) {
            x1 = 2;
            y1 = 0;
        } else if (vazio == 7) {
            x1 = 2;
            y1 = 1;
        } else if (vazio == 8) {
            x1 = 2;
            y1 = 2;
        }

        if (lado == 0) {
            y2 = y1;
            x2 = x1 - 1;
            vazio -= 3;
        } else if (lado == 1) {
            y2 = y1 + 1;
            x2 = x1;
            vazio += 1;
        } else if (lado == 2) {
            y2 = y1 - 1;
            x2 = x1;
            vazio -= 1;
        } else if (lado == 3) {
            y2 = y1;
            x2 = x1 + 1;
            vazio += 3;
        }

        matriz[x1][y1] = matriz[x2][y2];
        matriz[x2][y2] = 0;
    }

    public int qtd_mov() {
        if (vazio == 0 || vazio == 2 || vazio == 6 || vazio == 8) {
            return 2;
        } else if (vazio == 1 || vazio == 3 || vazio == 5 || vazio == 7) {
            return 3;
        } else {
            return 4;
        }
    }

    @Override
    public int compareTo(State o) {
        if (java.util.Arrays.deepEquals(o.matriz, this.matriz)) {
            return 0;
        } else {
            return -1;
        }
    }

    @Override
    public String toString() {
        String aux = "-------------\n";
        aux = aux + "| " + matriz[0][0] + " | " + matriz[0][1] + " | " + matriz[0][2] + " |\n";
        aux = aux + "| " + matriz[1][0] + " | " + matriz[1][1] + " | " + matriz[1][2] + " |\n";
        aux = aux + "| " + matriz[2][0] + " | " + matriz[2][1] + " | " + matriz[2][2] + " |\n";
        aux = aux + "-------------\n";
        return aux;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        int[][] aux  = new int[3][3];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(matriz[i], 0, aux[i], 0, 3);
        }
        State clone = new State(aux);
        clone.pai = this;
        aux = null;
        return clone;
    }

    public boolean mov_baixo(int x) {
        if (x > 2) {
            return true;
        } else {
            return false;
        }
    }

    public boolean mov_esq(int x) {
        if (x != 2 && x != 5 && x != 8) {
            return true;
        } else {
            return false;
        }
    }

    public boolean mov_dir(int x) {
        if (x != 0 && x != 3 && x != 6) {
            return true;
        } else {
            return false;
        }
    }

    public int getVazio() {
        return vazio;
    }

    public boolean mov_cima(int x) {
        if (x < 6) {
            return true;
        } else {
            return false;
        }
    }
}
