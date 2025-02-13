class sound{
    public void bark(){
        System.out.println("dog start bark");
    }
    public void meaw(){
        System.out.println("cat start meaw");
    }
}
public class ClassObject2 {
    public static void main(String[] args) {
        sound dog = new sound();
        sound cat = new sound();
        dog.bark();
        cat.meaw();
    }
}
