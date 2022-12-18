import { gameobject } from "./GameObjects";
import { cell } from "./Cell";

export class snake extends gameobject{
    constructor(info, gamemap){
        super();

        this.id = info.id;
        this.color = info.color;
        this.gamemap = gamemap;

        this.cells = [new cell(info.r, info.c)];    //body of snake
        this.next_cell = null;      //next destination
        this.dr = [-1, 0, 1, 0], this.dc = [0, 1, 0, -1];

        this.speed = 5;
        this.direct = -1;    //'-1' means no direct, 0, 1, 2, 3 means up, right, down, left respectively
        this.status = "idle";   //idle, move, died

        this.step = 0;
        this.eye_direction = 0;
        if(this.id === 1) this.eye_direction = 2;
        this.eye_dx = [
            [-1, 1],
            [1,  1],
            [1, -1],
            [-1, -1],
        ];
        this.eye_dy = [
            [-1, -1],
            [-1, 1],
            [1, 1],
            [1, -1],
        ];

        this.eps = 1e-2;    //max error could accepted
    }

    start(){

    }

    set_direct(d){
        this.direct = d;
    }

    check_tail_increasing(){    //detect if the length of snake had been increased
        if(this.step <= 10) return true;
        else if(this.step % 3 === 1) return true;
        return false;
    }

    next_step(){
        const d = this.direct;
        this.next_cell = new cell(this.cells[0].r + this.dr[d], this.cells[0].c + this.dc[d]);
        this.eye_direction = d;
        this.direct = -1;
        this.status = "move";
        ++ this.step;

        const k = this.cells.length;
        for(let i = k; i > 0; -- i){
            this.cells[i] = JSON.parse(JSON.stringify(this.cells[i - 1]));
        }
    }

    update_move(){
        const dx = this.next_cell.x - this.cells[0].x;
        const dy = this.next_cell.y - this.cells[0].y;
        const dist = Math.sqrt(dx * dx + dy * dy);

        if(dist < this.eps){
            this.cells[0] = this.next_cell;     //add a new snake head
            this.next_cell = null;
            this.status = "idle";   //movement completed
            if(!this.check_tail_increasing()) this.cells.pop();
        }
        else{
            const move_dist = this.speed * this.gap / 1000;
            this.cells[0].x += move_dist * dx / dist;
            this.cells[0].y += move_dist * dy / dist;

            if(!this.check_tail_increasing()){
                const k = this.cells.length;
                const tail = this.cells[k - 1], tail_target = this.cells[k - 2];
                const tail_dx = tail_target.x - tail.x;
                const tail_dy = tail_target.y - tail.y;
                tail.x += move_dist * tail_dx / dist;
                tail.y += move_dist * tail_dy / dist;
            }
        }
    }

    update(){       //execute every frame
        if(this.status === "move") this.update_move();
        this.render();
    }

    render(){
        const l = this.gamemap.l;
        const ctx = this.gamemap.ctx;

        ctx.fillStyle = this.color;
        if(this.status === "died"){
            ctx.fillStyle = "white";
        }
        ctx.beginPath();
        for(const cell of this.cells){
            ctx.beginPath();
            ctx.arc(cell.x * l, cell.y * l, l / 2 * 0.8, 0, Math.PI * 2);
            ctx.fill();
        }
        for(let i = 1; i < this.cells.length; ++ i){
            const a = this.cells[i - 1], b = this.cells[i];
            // if(Math.abs(a.x - b.x) < this.eps && Math.abs(a.y - b.y) < this.eps) continue;
            if(Math.abs(a.x - b.x) < this.eps){
                ctx.fillRect((a.x - 0.4) * l, Math.min(a.y, b.y) * l, l * 0.8, Math.abs(a.y - b.y) * l);
            }
            if(Math.abs(a.y - b.y) < this.eps){
                ctx.fillRect(Math.min(a.x, b.x) * l, (a.y - 0.4) * l, Math.abs(a.x - b.x) * l, l * 0.8);
            }
        }

        ctx.fillStyle = "black";
        for(let i = 0; i < 2; ++ i){
            ctx.beginPath();
            const eye_x = (this.cells[0].x + this.eye_dx[this.eye_direction][i] * 0.15) * l;
            const eye_y = (this.cells[0].y + this.eye_dy[this.eye_direction][i] * 0.15) * l;
            ctx.arc(eye_x, eye_y, l * 0.05, 0, Math.PI * 2);
            ctx.fill();
        }
    }
}