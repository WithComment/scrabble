package test.entity;

import entity.Board;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardTest {
    @Test
    void testInit() {
        Board board = new Board();
        boolean[][] checked = new boolean[15][15];
        assertEquals(15, board.getHeight());
        assertEquals(15, board.getWidth());
        int[][] tiles = new int[][]{
                {0, 0, 3, 0},
                {0, 7, 3, 0},
                {0, 14, 3, 0},
                {7, 0, 3, 0},
                {7, 14, 3, 0},
                {14, 0, 3, 0},
                {14, 7, 3, 0},
                {14, 14, 3, 0},

                {1, 1, 2, 0},
                {2, 2, 2, 0},
                {3, 3, 2, 0},
                {4, 4, 2, 0},
                {1, 13, 2, 0},
                {2, 12, 2, 0},
                {3, 11, 2, 0},
                {4, 10, 2, 0},
                {13, 1, 2, 0},
                {12, 2, 2, 0},
                {11, 3, 2, 0},
                {10, 4, 2, 0},
                {13, 13, 2, 0},
                {12, 12, 2, 0},
                {11, 11, 2, 0},
                {10, 10, 2, 0},

                {0, 3, 0, 2},
                {2, 6, 0, 2},
                {3, 7, 0, 2},
                {6, 6, 0, 2},
                {0, 11, 0, 2},
                {2, 8, 0, 2},
                {6, 8, 0, 2},
                {14, 3, 0, 2},
                {12, 6, 0, 2},
                {11, 7, 0, 2},
                {8, 6, 0, 2},
                {14, 11, 0, 2},
                {12, 8, 0, 2},
                {11, 7, 0, 2},
                {8, 8, 0, 2},
                {3, 0, 0, 2},
                {6, 2, 0, 2},
                {7, 3, 0, 2},
                {3, 14, 0, 2},
                {6, 12, 0, 2},
                {7, 11, 0, 2},
                {11, 0, 0, 2},
                {8, 2, 0, 2},
                {11, 14, 0, 2},
                {8, 12, 0, 2},

                {2, 5, 0, 3},
                {5, 5, 0, 3},
                {2, 9, 0, 3},
                {5, 9, 0, 3},
                {12, 5, 0, 3},
                {9, 5, 0, 3},
                {12, 9, 0, 3},
                {9, 9, 0, 3},
                {5, 2, 0, 3},
                {5, 12, 0, 3},
                {9, 2, 0, 3},
                {9, 12, 0, 3}
        };
        for (int[] tile : tiles) {
            assertEquals(tile[2], board.getCell(tile[0], tile[1]).getWordMult());
            assertEquals(tile[3], board.getCell(tile[0], tile[1]).getLetterMult());
            checked[tile[0]][tile[1]] = true;
        }
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (!checked[i][j]) {
                    assertEquals(0, board.getCell(i, j).getWordMult());
                    assertEquals(0, board.getCell(i, j).getLetterMult());
                }
            }
        }
    }
}