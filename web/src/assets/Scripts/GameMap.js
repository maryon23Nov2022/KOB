import { gameobject } from "./GameObjects";

export class gamemap extends gameobject{
    constructor(ctx, parent){
        super();

        this.ctx = ctx;
        this.parent = parent;
        this.L = 0;
    }

    start(){

    }

    update(){
        this.render();
    }

    render(){

    }
}