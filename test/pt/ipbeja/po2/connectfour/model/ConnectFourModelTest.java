package pt.ipbeja.po2.connectfour.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipbeja.po2.connectfour.View;
import pt.ipbeja.po2.connectfour.gui.Board;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author André Dâmaso
 * @number 15307
 */

class ConnectFourModelTest{

    ConnectFourModel game;
    //View view;



    @BeforeEach
    public void setup(){

        ViewTest view = new ViewTest();
        game = new ConnectFourModel(view);
        //board = new Board();

    }

    @Test
    public void test01() { //testar a colocação de uma peça numa coluna vazia

        int line = game.dropPiece(4);
        assertEquals(5, line);


    }

    @Test
    public void test02(){ //testar a colocação de uma peça numa coluna contendo já uma peça na linha quatro

        assertEquals(5, game.cellSelected(5));
        assertEquals(4, game.cellSelected(5));
        assertEquals(3, game.cellSelected(5));


    }

    @Test
    public void test03(){ //vereficar se quando joga na mesma coluna, ela fica preenchida

        int col = 2;

        assertEquals(5, game.cellSelected(col));
        assertEquals(4, game.cellSelected(col));
        assertEquals(3, game.cellSelected(col));
        assertEquals(2, game.cellSelected(col));
        assertEquals(1, game.cellSelected(col));
        assertEquals(0, game.cellSelected(col));

        assertFalse(game.isFull(col)); //verefica se não se pode jogar, o metodo isFull retorna falso quando nao se pode jogar

    }

    @Test
    public void test04(){ //vereficar que na 7 jogada do player 1 que nao ganha o jogo

        assertEquals(5, game.cellSelected(0)); //player1 a jogar na linha 5
        assertEquals(4, game.cellSelected(0)); //player2 a jogar na linha 4
        assertEquals(5, game.cellSelected(1));
        assertEquals(4, game.cellSelected(1));
        assertEquals(5, game.cellSelected(2));
        assertEquals(4, game.cellSelected(2));
        assertEquals(5, game.cellSelected(5));//player1 a jogar 4 vezes na linha 5

        assertFalse(game.isWinPosition(5, 5)); //verificar se na ultima jogada do Player1 nao ganhou


    }

    @Test
    public void test05(){ //testar se o player 1 ganha por linha, na linha 5

        assertEquals(5, game.cellSelected(0)); //player 1 a jogar na linha 5
        assertEquals(4, game.cellSelected(0)); //player2 a jogar na linha 4
        assertEquals(5, game.cellSelected(1));
        assertEquals(4, game.cellSelected(1));
        assertEquals(5, game.cellSelected(2));
        assertEquals(4, game.cellSelected(2));
        assertEquals(5, game.cellSelected(3));//player 1

        assertTrue(game.isWinPosition(5, 3)); //verificar se na ultima jogada o Player1 ganhou por linha


    }

    @Test
    public void test06(){ //testar se o player 1 ganha por coluna, na ultima coluna (7)

        assertEquals(5, game.cellSelected(6)); //player 1 a jogar na linha 5
        assertEquals(5, game.cellSelected(5)); //player 2 a jogar na linha 5
        assertEquals(4, game.cellSelected(6));
        assertEquals(4, game.cellSelected(5));
        assertEquals(3, game.cellSelected(6));
        assertEquals(3, game.cellSelected(5));
        assertEquals(2, game.cellSelected(6));//player 1

        assertTrue(game.isWinPosition(2,6)); //verificar se na ultima jogada o Player1 ganhou por coluna


    }

    @Test
    public void test07(){ //testar se o player 2 ganha na diagonar da celula (5,3)->(2,0)

        for (int j = 0; j < 5; j++) {

            assertEquals(5, game.cellSelected(j));//        1,2,1,2
            assertEquals(4, game.cellSelected(j));//        2,1,2,1
            assertEquals(3, game.cellSelected(j));//        1,2,1,2
            //  0,1,2,1

        }

        for (int j = 0; j < 3; j++) {
            assertEquals(2, game.cellSelected(j));
        }


        assertTrue(game.isWinPosition(5,3)); //é possivel ganhar nessa posição
        assertFalse(game.hasWinningLinePlayer2()); //mas que não é possival ganhar por linha
        assertFalse(game.hasWinningColumnPlayer2()); //nem por coluna, por isso só pode ser possível ganhar pela diagonal
        assertTrue(game.hasWinnningAntiDiagonalPlayer2()); //o player2 ganha na anti diagonal
        assertFalse(game.hasWinningMainDiagonalPlayer2()); //nao pode ganhar pela main diagonal


    }

    @Test
    public void test08(){ //testar se pode ganhar em ambas as diagonais na célula (2,3)

        for (int j = 0; j < 7; j++) {

            assertEquals(5, game.cellSelected(j));
            assertEquals(4, game.cellSelected(j));
            assertEquals(3, game.cellSelected(j));

        }

        for (int j = 0; j < 4; j++) { //termina na coluna 4 pois depois disso o jogo tem de retornar vitoria
            assertEquals(2, game.cellSelected(j));
        }

        assertTrue(game.isWinPosition(2,3)); //nesta posição pode se ganhar pelas duas diagonais pelo Player1
        assertTrue(game.hasWinningMainDiagonalPlayer1());
        assertTrue(game.hasWinnningAntiDiagonalPlayer1());

    }

    @Test
    public void test09(){ //testar se existiu empate

        for (int i = 5; i >= 0; i--) { //preenche a primeira coluna
            assertEquals(i, game.cellSelected(0));
        }
        for (int i = 5; i >= 0; i--) { //preenche a segunda coluna
            assertEquals(i, game.cellSelected(1));
        }
        for (int i = 5; i >= 0; i--) { //preenche a terceira coluna
            assertEquals(i, game.cellSelected(2));
        }

        assertEquals(5, game.cellSelected(4)); //preenche a ultima celula da coluna 4 para nao haver condiçao de vitoria
        assertEquals(5, game.cellSelected(3)); //preenche a ultima celula da coluna 3 para nao haver condiçao de vitoria

        for (int i = 4; i >= 0; i--) { //preenche o resto da coluna 3
            assertEquals(i, game.cellSelected(3));
        }

        for (int i = 4; i >= 0; i--) { //preenche o resto da coluna 4
            assertEquals(i, game.cellSelected(4));
        }

        for (int i = 5; i >= 0; i--) { //preenche a coluna 5
            assertEquals(i, game.cellSelected(5));
        }
        for (int i = 5; i >= 0; i--) { //preenche a coluna 6
            assertEquals(i, game.cellSelected(6));
        }

        assertTrue(game.isDrawPosition()); //verificar se existe condição de empate
        assertFalse(game.isWinPosition(0,6)); //verificar se numa casa houve vitoria, onde tem de dar false


    }







}