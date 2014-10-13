
package numberpuzzle;

import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JOptionPane;

public class NumberPuzzle {

    static int profundidade;

    public static void executa(String[] args) throws CloneNotSupportedException {
        profundidade = 1;
        int[][] meta = {{1, 2, 3}, {8, 0, 4}, {7, 6, 5}};
        int[][] facil = {{1, 3, 4}, {8, 6, 2}, {7, 0, 5}};
        int[][] medio = {{2, 8, 1}, {0, 4, 3}, {7, 6, 5}};
        int[][] dificil = {{2, 8, 1}, {4, 6, 3}, {0, 7, 5}};
        int[][] hell = {{5, 6, 7}, {4, 0, 8}, {3, 2, 1}};
        options.largura = false;
        State raiz = null;
        if (options.Iten.equals("Dificil")) {
            raiz = new State(dificil);
        } else if (options.Iten.equals("Facil")) {
            raiz = new State(facil);
        } else if (options.Iten.equals("Medio")) {
            raiz = new State(medio);
        } else if (options.Iten.equals("HELL")) {
            raiz = new State(hell);
        }
        State resultado = new State(meta);

        State atual = raiz;
        State[] nivel = aprofunda(atual, resultado);
        boolean res = false;
        ArrayList<State> filhos = new ArrayList<>();
        ArrayList<State[]> niveis = null;
        ArrayList<State> solucao = new ArrayList<>();
        options.cont_ant_exist = 0;
        int nos = 0;
        if (options.largura) {
            niveis = new ArrayList<>();
        }
        do {
            if (options.largura) {
                niveis.add(nivel);
            }
            
            System.out.println("Nível " + profundidade);
            System.out.println("\t" + nivel.length + " Filhos no nivel " + profundidade);
            
            for (State state : nivel) {
                nos++;
                //System.out.println("Nó nº "+nos);
                
                
                if (state.compareTo(resultado) == 0) {
                    System.out.println("Solução encontrada");
                    res = true;
                    while (state.pai != null) {
                        solucao.add(state);
                        state = state.pai;
                    }
                    solucao.add(state);
                }
                
            }
            if (res) {
                break;
            } else {

                State[] aux = null;
                for (State state : nivel) {
                    aux = aprofunda(state, resultado);                  
                    filhos.addAll(Arrays.asList(aux));
                }
                nivel = filhos.toArray(nivel);
                filhos.removeAll(Arrays.asList(aux));
                aux = null;
                
            }

            profundidade++;
           System.gc();
        } while (true);

        System.out.println("" + nivel.length + " Filhos no nivel " + profundidade);
        System.out.println("Um total de "+nos+" nós na árvore.");
        System.out.println("Existia um total de "+options.cont_ant_exist+" antecessores repetidos.");
        State[] result = new State[solucao.size()];
        int c = solucao.size() - 1;
        for (State state : solucao) {
            //System.out.println("" + state.toString());
            result[c] = state;
            c--;
        }
        options.Resultado_Completo = result;
        options.resultado = options.Resultado_Completo[0];

        JOptionPane.showMessageDialog(null, "Resultado Encontrado!");
        NewJFrame.preenche();
    }

    public static State[] aprofunda(State atual, State resultado) throws CloneNotSupportedException {
        
        //System.out.println(" Raiz do nivel " + profundidade);
        //System.out.println(atual.toString());
        State[] aux = new State[atual.qtd_mov()];
        State[] finalAux = new State[atual.qtd_mov()];
        int cont = 0;
        boolean found = false;
        State stt_aux;
        if (atual.mov_baixo(atual.getVazio())) {
            stt_aux = (State) atual.clone();
            stt_aux.move(0);
            aux[cont] = stt_aux;
            cont++;                
        }

        if (atual.mov_esq(atual.getVazio())) {
            stt_aux = (State) atual.clone();
            stt_aux.move(1);
            aux[cont] = stt_aux;
            cont++;
        }
        if (atual.mov_dir(atual.getVazio())) {
            stt_aux = (State) atual.clone();
            stt_aux.move(2);
            aux[cont] = stt_aux;
            cont++;
        }
        if (atual.mov_cima(atual.getVazio())) {
            stt_aux = (State) atual.clone();
            stt_aux.move(3);
            aux[cont] = stt_aux;
            cont++;
        }
        ///Verifica se o novo tabuleiro é igual a algum já existente
        for(int x = 0; x < atual.qtd_mov(); x++){
            State auxpai = aux[x].pai;
                while(auxpai != null){
                    if(aux[x].compareTo(auxpai)==0){
                        //System.out.println("Antecessor já existente!");
                        options.cont_ant_exist++;
                        ///Hora de excluir este estado
                        for(int k = x; k < aux.length-1; k++){
                            aux[k] = aux[k+1]; 
                        }
                        finalAux = Arrays.copyOf(aux, aux.length-1);
                        
                        found = true;
                        break;
                    }                 
                    auxpai = auxpai.pai;
                }
                auxpai = null;
                //System.gc();
        }
        
        
        
        //System.out.println(""+aux.length);
        /*
         System.out.println("" + aux.length + " Filhos do nivel " + profundidade);
         for (State state : aux) {
         System.out.println("" + state.toString());
         }*/
        ///Se encontrar algum antecessor repetido logo, retorna-se o vetor menor sem a repeticao
        
        if(found){
            aux = null;
            return finalAux;
        }else{
            finalAux = null;
            return aux;
        }
    }
}
