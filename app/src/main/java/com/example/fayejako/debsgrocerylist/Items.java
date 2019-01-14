package com.example.fayejako.debsgrocerylist;

public class Items {
    private int id;
    private String name;
    private int status;

    //constructor
    public Items(int id,String name, int status){
        this.id = id;
        this.name = name;
        this.status = status;
    }

    //getters
    public int getItemId() {return id; }
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
