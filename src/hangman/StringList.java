package hangman;

import java.util.Arrays;

public class StringList {

    private String[] array = new String[0];

    public void add(String str){
        array = Arrays.copyOf(array, array.length+1);
        array[array.length-1] = str;
    }

    public void add(int index, String str){
        array = Arrays.copyOf(array, array.length+1);
        String temp1 = "";
        String temp2 = "";
        for(int i = index; i < array.length; i++){
            if(i == index){
                temp1 = array[i];
                array[i] = str;
            }else if(i == array.length-1){
                array[i] = temp1;
            }else{
                temp2 = temp1;
                temp1 = array[i];
                array[i] = temp2;
            }
        }
    }

    public String get(int index) {
        return array[index];
    }

    public void remove(int index){
        remove(array[index]);
    }

    public void remove(String str){
        for(int i = 0; i < array.length; i++){
            if(array[i] == null || array[i].equals(str)){
                if(array[i] != null && array[i].equals(str)){
                    array[i] = null;
                }
                if(i == array.length-1){
                    array = Arrays.copyOf(array, array.length-1);
                    break;
                }
                array[i] = array[i+1];
                array[i+1] = null;
            }
        }
    }

    public void replace(int index, String str) {
        array[index] = str;
     }

     public void replace(String oldStr, String newStr){
        for(int i = 0; i < array.length; i++){
            if(array[i].equals(oldStr)){
                array[i] = newStr;
                break;
            }
        }
     }

     public String[] toArray(){
        return array;
     }

}
