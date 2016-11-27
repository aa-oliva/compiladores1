/**
 * Created by aaom on 29/10/16.
 */
import java.io.*;
import java.lang.*;
import javax.swing.JFileChooser;
import java.io.File;
public class Main {
    public static void main(String[] args){

        FileReader fr = null;
        BufferedReader br = null;
        // File ruta = null;
        File ruta = new File("C:\\Users\\aa-ol\\OneDrive\\Documentos\\GitHub\\compiladores1\\src\\text.txt");
        //Cadena de texto donde se guardara el contenido del archivo
        String contenido = "";
        try
        {

//            JFileChooser fileChooser = new JFileChooser();
//            int result = fileChooser.showOpenDialog(null);
//            if (result == JFileChooser.APPROVE_OPTION) {
//                //ruta puede ser de tipo String o tipo File
//                //si usamos un File debemos hacer un import de esta clase
//
//                ruta =  fileChooser.getSelectedFile();
//            }

            //System.out.println(ruta);
            //String ruta = "text.txt";
            fr = new FileReader( ruta );
            br = new BufferedReader( fr );

            String linea;
            //Obtenemos el contenido del archivo linea por linea
            while( ( linea = br.readLine() ) != null )
            {
                contenido += linea + "\n";
            }

        }catch( Exception e ){
            System.out.println("Ocurrio un error en la lectura del archivo");
        }
        //finally se utiliza para que si todo ocurre correctamente o si ocurre
        //algun error se cierre el archivo que anteriormente abrimos
        finally
        {
            try{
                br.close();
            }catch( Exception ex ){}
        }

        //Se imprime el contenido
        System.out.println( contenido );

       Lexer lexer = new Lexer(contenido);
    }
}
