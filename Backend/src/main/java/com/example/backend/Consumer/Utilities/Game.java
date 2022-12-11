package com.example.backend.Consumer.Utilities;

import java.util.Random;

public class Game {
    final private Integer rows, cols, inner_walls_count;
    final private int[][] g;
    final private static int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};

    public Game(Integer rows, Integer cols, Integer inner_walls_count){
        this.rows = rows;
        this.cols = cols;
        this.inner_walls_count = inner_walls_count;
        g = new int[rows][cols];
    }

    public int[][] getG() {
        return g;
    }

    private void draw(){
        for(int i = 0; i < this.rows; ++ i){
            for(int j = 0; j < this.cols; ++ j){
                g[i][j] = 0;
            }
        }

        for(int r = 0; r < this.rows; ++ r){
            g[r][0] = g[r][this.cols - 1] = 1;
        }

        for(int c = 0; c < this.cols; ++ c){
            g[0][c] = g[this.rows - 1][c] = 1;
        }

        Random random = new Random();
        for(int i = 0; i < this.inner_walls_count / 2; ++ i){
            for(int j = 0; j < 1000; ++ j){
                int r = random.nextInt(this.rows);
                int c = random.nextInt(this.cols);

                if(g[r][c] == 1 || g[this.rows - 1 - r][this.cols - 1 - c] == 1)
                    continue;
                if(r == this.rows - 2 && c == 1 || r == 1 && c == this.cols - 2)
                    continue;

                g[r][c] = g[this.rows - 1 - r][this.cols - 1 - c] = 1;
                break;
            }
        }
    }

    public void createMap(){
        draw();
    }
}
