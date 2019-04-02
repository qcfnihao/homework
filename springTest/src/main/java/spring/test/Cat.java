package spring.test;
public class Cat {
    private  int inr;
    public Cat(int inr){
        this.inr = inr;
    }
    public static void print(Cat cat){
        System.out.println(cat.inr);
    }
    public void setInr(int inr){
        this.inr = inr;
    }
    public static void main(String[] args) {
        Cat A = new Cat(100);
        Cat B = new Cat(101);
        System.out.println(A);
        System.out.println(B);
        swap(A,B);
        Cat.print(A);
        Cat.print(B);
    }
    public static void swap(Cat A,Cat B){
        Cat temp = A;
        A = B;
        B = temp;
   //     temp = new Cat(400);
    //    B.setInr(400);
    }
}
