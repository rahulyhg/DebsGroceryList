package com.example.fayejako.debsgrocerylist;

public class Items {
    private String name;
    private int status;

    //constructor
    public Items(String name, int status){
        this.name = name;
        this.status = status;
    }

    //getters
    public String getItemName() {return name; }
    public boolean getItemStatus() {
        if(status == 1){
            return true;
        }
        else{
            return false;
        }
    }

}
