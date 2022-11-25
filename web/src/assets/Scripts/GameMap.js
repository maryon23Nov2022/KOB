import { gameobject } from "./GameObjects";
import { wall } from "./Wall";

export class gamemap extends gameobject{
    constructor(ctx, parent){
        super();

        this.ctx = ctx;
        this.parent = parent;
        this.l = 0;
        this.rows = 13;
        this.cols = 13;

        this.walls = [];
        this.walls_cnt = 10;
    }

    creat_walls(){
        // new wall(0, 0, this);
        const g = [];
        for(let r = 0; r < this.rows; ++ r){
            g[r] = [];
            for(let c = 0; c < this.cols; ++ c){
                g[r][c] = false;
            }
        }

        for(let r = 0; r < this.rows; ++ r){
            g[r][0] = g[r][this.cols - 1] = true;
        }
        for(let c = 1; c < this.cols; ++ c){
            g[0][c] = g[this.rows - 1][c] = true;
        }

        for(let i = 0; i < this.walls_cnt; ++ i){
            for(let j = 0; j < 1000; ++ j){
                let r = parseInt(Math.random() * this.rows);
                let c = parseInt(Math.random() * this.cols);
                if(g[r][c] || g[c][r]) continue;
                if(r == this.rows - 2 && c == 1 || c == this.cols - 2 && r == 1) continue;
                g[r][c] = g[c][r] = true;
                break;
            }
        }

        for(let r = 0; r < this.rows; ++ r){
            for(let c = 0; c < this.cols; ++ c){
                if(g[r][c]) this.walls.push(new wall(r, c, this));
            }
        }
    }

    start(){
        this.creat_walls();
    }

    update_size(){
        this.l = parseInt(Math.min(this.parent.clientWidth / this.cols, this.parent.clientHeight / this.rows));
        //"div's" size can get from clientWidth and clientHeight
        this.ctx.canvas.width = this.l * this.cols;
        this.ctx.canvas.height = this.l * this.rows;
    }

    update(){
        this.update_size();
        this.render();
    }

    render(){
        const color_even = "#AAD751", color_odd = "#A2D149";
        for(let i = 0; i < this.rows; ++ i){
            for(let j = 0; j < this.cols; ++ j){
                if(i + j & 1) this.ctx.fillStyle = color_odd;
                else this.ctx.fillStyle = color_even;
                this.ctx.fillRect(j * this.l, i * this.l, this.l, this.l);
            }
        }
    }
}