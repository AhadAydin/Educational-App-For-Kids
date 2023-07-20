package com.example.gameproject;

import androidx.annotation.Nullable;

public class ObjBool {

    public Object obj;
    public Boolean selected;
    public ObjBool(Object o){
        obj=o; selected=false;
    }

    @Override
    public boolean equals(@Nullable Object object) {
        if(obj instanceof ObjBool){
            return this.obj == ((ObjBool) object).obj ;
        }else{
            return super.equals(obj);
        }
    }

    @Override
    public int hashCode() {
        return obj.hashCode();
    }
}
