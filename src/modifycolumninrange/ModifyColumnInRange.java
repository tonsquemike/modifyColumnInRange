/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modifycolumninrange;

import Funciones.Archivos;
import Funciones.MyListArgs;
import Funciones.MySintaxis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author UAEM
 */
public class ModifyColumnInRange {

    /**
     * @param args the command line arguments
     */
    String IN = "";
    String OUT = "";
    int p = 0;

    int RANGE = 0;
    ModifyColumnInRange(String[]args)
    {
        String        ConfigFile = "";
        MyListArgs Param          ;

        Param      = new MyListArgs(args)                  ;
        ConfigFile = Param.ValueArgsAsString("-CONFIG", "");

        if (!ConfigFile.equals(""))
        {
            Param.AddArgsFromFile(ConfigFile);
        }//fin if

        String Sintaxis      = "-IN:str -OUT:str [-P:str] [-RANGE:int]";
        MySintaxis Review    = new MySintaxis(Sintaxis, Param);

        IN     = Param.ValueArgsAsString ("-IN"  , "");
        OUT    = Param.ValueArgsAsString ("-OUT" , "");
        p      = Param.ValueArgsAsInteger("-P"   ,30);
        RANGE  = Param.ValueArgsAsInteger("-RANGE", 4);

        BufferedWriter bw = Archivos.newBuffer(OUT);

        System.out.println("progarma");
        int rand;
        String line;
        String []tmp;

        int []ranges = new int[RANGE*2+1];
        for (int i = 0, x = (RANGE*-1); i < ranges.length; i++, x++) {
            ranges[i] = x;
        }
        //leer archivo liena por linea

        String tmpvalue = "";
        int actValue = 0;
        String tmpLine = "";
        try (BufferedReader br = new BufferedReader(new FileReader(IN))){
            while ((line = br.readLine()) != null) {
                tmp = line.split(",");//dividir la linea actual por comas para copiar la lista de caracterisitcas
                rand = ThreadLocalRandom.current().nextInt(0,100);
                System.out.println("rand = "+rand);
                if(rand<p)
                {
                    tmpvalue = tmp[0];

                    try {
                        actValue = Integer.parseInt(tmpvalue.substring(1, tmpvalue.length()));
                    }catch (Exception e){
                        System.out.println("tmpValue"+tmpvalue);
                    }
                    System.out.println("act value = "+actValue);
                    tmp[0] = "c"+String.valueOf(newValue(actValue, RANGE, ranges));
                    System.out.println("tmp[0] = "+tmp[0]);
                    for (int i = 0; i < tmp.length; i++) {
                        tmpLine += tmp[i];
                        if(i<tmp.length-1)
                            tmpLine+=",";
                    }
                    //escribir vector actual como una casola cadaena
                    Archivos.addLine(bw, tmpLine);
                    tmpLine = "";
                }
                else
                {
                    //escribir linea original en el archvio de saida
                    Archivos.addLine(bw, line);
                }


            }
        }catch (Exception e){
            System.out.println("error loading csv IN");
        }

        Archivos.saveFile(bw);
    }

    public int newValue(int actual, int range, int ranges[])
    {

        int rand = ThreadLocalRandom.current().nextInt(0,ranges.length-1);
        int newValue  = actual+ranges[rand];

        return newValue;
    }
}
