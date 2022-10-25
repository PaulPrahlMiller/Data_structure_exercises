package com.oop;

public class Contact {

    private final String name;
    private final String address;

    public Contact(String n, String a){
        name = n;
        address = a;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString(){
        return String.format("""
                Name: %s
                Address: %s
                """, getName(), getAddress());
    }
}
