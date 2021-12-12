import Garph_GUI.GUI;

public class main {
    public static void main(String[] args) {
        if(args.length>0){
            GUI g = new GUI();
            String ans =args[0];
            g.load_from_json(ans);
        }
    }
}
