package com.example.backend.Consumer.Utilities;

public class Game {
    private Integer rows, cols, inner_walls_count;
    private int[][] g;

    public Game(Integer rows, Integer cols, Integer inner_walls_count){
        this.rows = rows;
        this.cols = cols;
        this.inner_walls_count = inner_walls_count;
        g = new int[rows][cols];
    }

    
}
