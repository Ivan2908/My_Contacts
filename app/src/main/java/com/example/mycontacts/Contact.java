package com.example.mycontacts;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.RadioGroup;

public class Contact implements Parcelable {

        private String telefono;
        private String nombres;
        private String grupo;

    public Contact(String telefono, String nombres, String grupo) {
        this.telefono = telefono;
        this.nombres = nombres;
        this.grupo = grupo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.telefono);
        dest.writeString(this.nombres);
        dest.writeString(this.grupo);
    }

    protected Contact(Parcel in) {
        this.telefono = in.readString();
        this.nombres = in.readString();
        this.grupo = in.readString();
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel source) {
            return new Contact(source);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };
}
