import com.sun.deploy.util.ArrayUtil;

import java.lang.reflect.Array;
/**
 * Created by aa-ol on 11/26/2016.
 */
public class SimuladorIdent {
    static String [] caracteresArray = {"a","b","c","d","e","f","g","h","i","j","k",
            "l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","A","B",
            "C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S",
            "T","U","V","W","X","Y","Z","0","1","2","3","4","5","6","7","8","9"};

    public static boolean verificarIdent(String cadena){
//        if(contains=false){
//            break;
//        }
        System.out.println("Entra en el verificador de ident");


        boolean isIdent=true;
        boolean contains=false;
        //Largo de toda la cadena
        for(int i=0; i<cadena.length(); i++){
            String c = cadena.substring(i, i+1); //caracter actual
            

            //Revisa cada caracter contra todos los caracteres
            for(int ii=0; ii<caracteresArray.length; ii++){
                //Reinicia el valor de la variable para que con cada letra
                // tenga que volver a comparar si esta en el arreglo o no.
                contains = false;
                if(c.equals(caracteresArray[ii])){
                    contains=true;
                    break;
                }

            }

            /*Se asigna el valor del alcual 'contains' para que en el caso de
            * que no contenga algun caracter, el contains sea falso, y el valor
            * del isIdent tambien sea falso.
            *
            * Cuando NO se encuetra algun caracter se sale del ciclo.*/
            isIdent=contains;
            if(contains==false){
                System.out.println("No contiene el caracter: " + c);
                break;
            }
            else{
                System.out.println("Conetiene el caracter: " + c);

            }


        }


        return isIdent;
    }
}