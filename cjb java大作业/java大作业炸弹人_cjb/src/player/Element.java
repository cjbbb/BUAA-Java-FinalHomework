package player;

public abstract class Element {
    private int x;
    private int y;
    private int h;
    private int w;
    private int layer;
    private int variety;
    private boolean across;//能否穿过
    private int burst;//0 不能，1能会阻挡炸弹，2能且不阻挡炸弹
    private boolean visable;

    public Element(int x,int y,int w,int h){
        this.x=x;
        this.y=y;
        this.w=w;
        this.h=h;
        this.layer = y/50;
        visable = true;
        across = true;
        burst = 2;
        variety = -1;
    }

    public void updata(){
        move();
        destroy();
    }
    public abstract void move();
    public abstract void destroy();
}
