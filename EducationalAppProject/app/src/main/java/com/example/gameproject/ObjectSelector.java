package com.example.gameproject;

import android.util.Log;

import java.util.HashSet;
import java.util.Random;

public class ObjectSelector {

    public ObjectSelector(){
        set=new HashSet<>();
        selectedCount=0;
    }
    void putObj(Object o){
        ObjBool newObj = new ObjBool(o);
        set.add(newObj);
    }
    void removeObj(Object o){
        ObjBool tempObj = new ObjBool(o);
        set.remove(tempObj);
    }
    boolean containsStr(Object o){
        ObjBool tempObj = new ObjBool(o);
        return set.contains(tempObj);
    }
    Object randUnselectedObj(){
        if(selectedCount>=set.size()){
            return null;
        }else{
            boolean found=false;
            ObjBool current = null;

            while(!found){
                int randomIndex = new Random().nextInt(set.size());
                current = giveAt(randomIndex);
                if(current.selected == false){
                    this.selectedCount++;
                    current.selected=true;
                    found=true;
                }
            }
            return current.obj;
        }
    }
    Object randObj(){
        if(selectedCount>=set.size()){
            return null;
        }else{
            boolean found=false;
            ObjBool current = null;

            while(!found){
                int randomIndex = new Random().nextInt(set.size());
                current = giveAt(randomIndex);
                if(current.selected == false){
                    this.selectedCount++;
                    found=true;
                }
            }
            return current.obj;
        }
    }
    ObjBool giveAt(int index){
        if(index>=set.size()){
            return null;
        }else{
            ObjBool[] tempArr = set.toArray(new ObjBool[set.size()]);
            return tempArr[index];
        }
    }
    void resetSet(){
        ObjBool[] tempArr = set.toArray(new ObjBool[set.size()]);
        for(int i = 0;i<tempArr.length;i++){
            tempArr[i].selected=false;
        }
    }
    int length(){
        return set.size();
    }
    int getSelectedCount(){
        return selectedCount;
    }
    HashSet getSet(){
        return set;
    }
    private
    int selectedCount;
    HashSet<Object> set;
}
