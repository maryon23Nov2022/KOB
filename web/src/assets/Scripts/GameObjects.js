const grr = [];

export class gameobject{
    constructor(){
        gameobject.push(this);
        this.gap = 0;
        this.has_called = false;
    }

    start(){    //execute only once

    }

    update(){   //execute every frame except the first frame
        
    }

    on_destroy(){   //execute before function "destroy"
        
    }

    destroy(){
        this.on_destroy();

        for(let i in grr){
            const obj = grr[i];
            if(obj == this){
                grr.splice(i);
                break;
            }
        }
    }

}

let last_ts;    //time of last execution

const step = ts =>{
    for(let obj of grr){
        if(!obj.has_called){
            obj.has_called = true;
            obj.start();
        }
        else{
            obj.gap = ts - last_ts;
            obj.update();
        }
    }
    last_ts = ts;
    requestAnimationFrame(step)
}

requestAnimationFrame(step)