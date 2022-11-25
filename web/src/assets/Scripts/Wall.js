import { gameobject } from "./GameObjects";

export class wall extends gameobject{
    constructor(r, c, gamemap){
        super();

        this.r = r, this.c = c;
        this.gamemap = gamemap;
        this.color = "#B37226";
    }

    update(){
        this.render();
    }

    render(){
        const l = this.gamemap.l;
        const ctx = this.gamemap.ctx;

        ctx.fillStyle = this.color;
        ctx.fillRect(this.c * l, this.r * l, l, l);
    }
}