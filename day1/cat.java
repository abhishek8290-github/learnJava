public class cat {
    String type;
    public static void main(String[] args) {
        cat _cat = new cat( "Siamese");
        _cat.sayMeow();
        
        _cat.print();
    }
    public cat(String type){
        this.type = type;
    }

    
    public void print(){
        System.out.println("Type of Cat is " + this.type);
    }

    public void sayMeow(){
        System.out.println("meow");
    }

}







